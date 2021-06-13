<template>
  <v-container>
    <v-text-field
      prepend-icon="mdi-card-text"
      :rules="[rules.required]"
      v-model="className"
      label="班课名称"
      outlined
    ></v-text-field>
    <v-text-field
      prepend-icon="mdi-account-circle"
      v-model="teacherName"
      :rules="[rules.required]"
      name="input-10-1"
      label="任课教师"
      outlined
    ></v-text-field>
    <v-text-field
      prepend-icon="mdi-format-color-highlight"
      v-model="classHour"
      :rules="[rules.required]"
      label="课时"
      outlined
    >
    </v-text-field>
    <v-text-field
      prepend-icon="mdi-numeric-1-circle-outline"
      v-model="semester"
      :rules="[rules.required]"
      label="学期"
      outlined
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
    <div class="text-center">
      <v-btn dark @click="judgeAddClass" color="primary" block> 添加 </v-btn>
      <v-snackbar v-model="snackbar" timeout="3000">
        {{ text }}

        <template v-slot:action="{ attrs }">
          <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">
            关闭
          </v-btn>
        </template>
      </v-snackbar>
    </div>
  </v-container>
</template>

<script>
import { addClass } from "../api/class";
export default {
  data() {
    return {
      name: "AddClass",
      className: "",
      teacherName: "",
      classHour: 24,
      semester: "2021-1",
      rules: {
        required: (value) => !!value || "必填",
      },

      snackbar: false,
      text: `添加班课成功`,
    };
  },
  methods: {
    judgeAddClass() {
      //判断是否都已填写
      if (
        this.className == "" ||
        this.teacherName == "" ||
        this.classHour < 1 ||
        this.semester == ""
      ) {
        this.snackbar = true;
        this.text = "请填写班课信息";
      } else {
        let result = addClass(
          localStorage.getItem("teacherId"),
          this.className,
          "nope",
          this.semester,
          this.classHour + ""
        );
        switch (result.data.code) {
          case "200":
            this.text = "创建成功";
            this.snackbar = true;
            this.$router.push("/class");
            break;
          default:
            this.alertMessage = result.data.msg;
            this.snackbar = true;
        }
      }

      //逻辑判断班级号是否重复,返回是否添加成功
    },
  },
};
</script>

<style scoped></style>
