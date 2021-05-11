<template>
  <v-container>
    <v-text-field
      v-if="type === 'classCode'"
      v-model="classNumber"
      :rules="[rules.required]"
      class="mt-5"
      hint="请输入班级号"
      label="班级号"
      outlined
      prepend-icon="mdi-format-color-highlight"
    >
    </v-text-field>
    <v-text-field
      v-if="type === 'school'"
      v-model="classNumber"
      :rules="[rules.required]"
      class="mt-5"
      hint="请输入学校名称"
      label="学校"
      outlined
      prepend-icon="mdi-format-color-highlight"
    >
    </v-text-field>
    <v-text-field
      v-if="type === 'grade'"
      v-model="classNumber"
      :rules="[rules.required]"
      class="mt-5"
      hint="请输入年级"
      label="年级"
      outlined
      prepend-icon="mdi-format-color-highlight"
    >
    </v-text-field>
    <v-text-field
      v-if="type === 'major'"
      v-model="classNumber"
      :rules="[rules.required]"
      class="mt-5"
      hint="请输入专业班级"
      label="专业班级"
      outlined
      prepend-icon="mdi-format-color-highlight"
    >
    </v-text-field>
    <v-text-field
      v-if="type == 'exp'"
      v-model="classNumber"
      :rules="[rules.required]"
      class="mt-5"
      hint="请输入修改后的经验值"
      label="经验值"
      outlined
      prepend-icon="mdi-format-color-highlight"
    >
    </v-text-field>

    <!--
      <v-alert
          type="warning"
          v-show="isComplete"
          dismissible
          transition="scale-transition"

      >请填写班课信息</v-alert>
-->
    <div class="text-center d-flex justify-space-around">
      <v-btn color="primary" dark @click="judgeEnterClass"> 确定 </v-btn>
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
import axios from "axios";

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
    };
  },
  methods: {
    judgeEnterClass() {
      //判断是否都已填写
      if (this.classNumber < 1) {
        this.snackbar = true;
        this.text = "请填写正确的班级号";
      } else {
        axios
          .post("", {
            username: this.GLOBAL.username,
            classNumber: this.classNumber,
          })
          .then(function (res) {
            this.isSuccess = res.data.isSuccess;
          })
          .catch(function (error) {
            console.log(error);
          });
        if (this.isSuccess) {
          this.snackbar = true;
          this.text = "加入班课成功";
        } else {
          this.snackbar = true;
          this.text = "加入班课失败";
        }

        this.$router.push({
          path: "/class",
        });
      }
    },

    back() {
      this.$router.back();
    },
  },
  mounted: function () {
    if (this.$route.query.type !== "") {
      this.type = this.$route.query.type;
    }
    console.log(this.type);
  },
};
</script>

<style scoped></style>
