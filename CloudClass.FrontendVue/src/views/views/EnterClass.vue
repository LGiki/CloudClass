<template>
  <v-container>
    <v-text-field
      v-if="type != ''"
      v-model="classNumber"
      :rules="[rules.required]"
      class="mt-5"
      :hint="hint"
      :label="label"
      outlined
      prepend-icon="mdi-format-color-highlight"
    >
    </v-text-field>

    <v-text-field
      v-if="type === 'changePass'"
      v-model="classNumber"
      :rules="[rules.required]"
      hint="请输入新密码"
      label="新密码"
      outlined
      prepend-icon="mdi-format-color-highlight"
    >
    </v-text-field>
    <v-text-field
      v-if="type === 'changePass'"
      v-model="classNumber"
      :rules="[rules.required]"
      hint="请输入再次输入新密码"
      label="再次输入新密码"
      outlined
      prepend-icon="mdi-format-color-highlight"
    >
    </v-text-field>

    <div class="d-flex flex-no-wrap justify-space-between" v-if="type === ''">
      <div>
        <v-card-title class="headline" v-text="classes.title"></v-card-title>

        <v-card-subtitle class="mt-0 font-weight-bold">
          班级号: {{ this.classNumber }}
          <br />
          任课教师: {{ classes.teacher }}
          <br />
          学期: {{ classes.semester }}
        </v-card-subtitle>
      </div>
      <div style="display: flex; align-items: center">
        <v-avatar class="ma-3" size="125" tile>
          <div ref="qrCodeUrl" class="qrcode"></div>
        </v-avatar>
      </div>
    </div>
    <!--
      <v-alert
          type="warning"
          v-show="isComplete"
          dismissible
          transition="scale-transition"

      >请填写班课信息</v-alert>
-->
    <div class="text-center d-flex justify-space-around" v-if="type != ''">
      <v-btn color="primary" dark @click="judgeEnterClass"> 确定 </v-btn>
      <v-btn color="green lighten" dark @click="back"> 返回 </v-btn>
    </div>

    <div class="text-center d-flex justify-space-around" v-if="type == ''">
      <v-btn
        color="primary"
        dark
        @click="confirmEnterClass"
        v-if="!enterSuccess"
      >
        加入
      </v-btn>
      <v-btn color="green lighten" dark @click="back"> 返回 </v-btn>
    </div>

    <v-snackbar v-model="snackbar" timeout="3000">
      {{ text }}

      <template v-slot:action="{ attrs }">
        <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">
          关闭
        </v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
import { enterClass, getClassData } from "@/api/class";

export default {
  data() {
    return {
      name: "AddClass",
      classNumber: 0,
      rules: {
        required: (value) => !!value || "必填",
      },
      snackbar: false,
      type: "classCode",
      text: `加入班课成功`,
      isSuccess: false,
      enterSuccess: false,
      hint: "",
      label: "",
      classes: {
        title: "工程实践",
        teacher: "池芝标",
        semester: "2021-1",
        classNumber: 10004,
      },
    };
  },
  methods: {
    async judgeEnterClass() {
      //判断是否都已填写
      if (this.type == "classCode" && this.classNumber < 1) {
        this.snackbar = true;
        this.text = "请填写正确的班级号";
      } else {
        let result2 = await getClassData(this.classNumber + "");

        switch (result2.data.code) {
          case "200":
            this.type = "";
            this.classes.teacher = result2.data.data.teacher;
            this.classes.semester = result2.data.data.term;
            this.classes.title = result2.data.data.ccName;

            break;
          default:
            this.text = result2.data.msg;
            this.snackbar = true;
        }
      }
    },
    async confirmEnterClass() {
      let result = await enterClass(this.classNumber + "");

      switch (result.data.code) {
        case "200":
          this.text = "加入成功";
          this.snackbar = true;
          this.enterSuccess = true;

          //      this.$router.push({
          //         path: "/class",
          //      });

          break;
        default:
          this.text = result.data.msg;
          this.snackbar = true;
      }
    },
    back() {
      this.$router.back();
    },
  },
  async mounted() {
    if (this.$route.query.type !== null) {
      this.type = this.$route.query.type;
      if (this.type == "classCode") {
        this.hint = "请输入班课号";
        this.label = "班课号";
      } else if (this.type == "school") {
        this.hint = "请输入学校名称";
        this.label = "学校";
      } else if (this.type == "grade") {
        this.hint = "请输入年级";
        this.label = "年级";
      } else if (this.type == "major") {
        this.hint = "请输入专业班级";
        this.label = "专业班级";
      } else if (this.type == "exp") {
        this.hint = "请输入修改后的经验值";
        this.label = "经验值";
      } else if (this.type == "changePass") {
        this.hint = "请输入原密码";
        this.label = "原密码";
      } else if (this.type == "") {
        let result = await getClassData(this.$route.query.classNumber);
        this.classes.teacher = result.data.data.teacher;
        this.classes.semester = result.data.data.term;
        this.classes.title = result.data.data.ccName;
      }
    }
    console.log(this.type);
  },
};
</script>

<style scoped></style>
