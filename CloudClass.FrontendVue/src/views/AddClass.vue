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
      v-model="classNumber"
      :rules="[rules.required]"
      label="班级号"
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
export default {
  data() {
    return {
      name: "AddClass",
      className: "",
      teacherName: "",
      classNumber: 10001,
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
        this.classNumber < 1 ||
        this.semester == ""
      ) {
        this.snackbar = true;
        this.text = "请填写班课信息";
      } else {
        this.snackbar = true;
        this.text = "添加班课成功";

        this.$router.push({
          path: "/class",
          query: {
            className: this.className,
            teacherName: this.teacherName,
            classNumber: this.classNumber,
            semester: this.semester,
          },
        });
      }

      //逻辑判断班级号是否重复,返回是否添加成功
    },
  },
};
</script>

<style scoped></style>
