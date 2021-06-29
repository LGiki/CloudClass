<template>
  <v-container>
    <v-text-field
        prepend-icon="mdi-pen"
        v-model="personDetail.name"
        label="姓名"
        :disabled="isTeacher === '1'"
        :rules="[rules.required]"
    ></v-text-field>



    <v-text-field
        prepend-icon="mdi-android-studio"
        v-model="personDetail.className"
        label="班级"
        :disabled="isTeacher === '1'"
        :rules="[rules.required]"
    ></v-text-field>

    <v-text-field
        prepend-icon="mdi-google-classroom"
        v-model="personDetail.grade"
        label="年级"
        :disabled="isTeacher === '1'"
        :rules="[rules.required]"
    ></v-text-field>

    <v-text-field
        prepend-icon="mdi-google-classroom"
        v-model="personDetail.exp"
        label="经验值"
        v-if="isTeacher === '1'"
        :rules="[rules.required]"
    ></v-text-field>
    <v-icon>mdi-account-box</v-icon>


    <div style="display: grid; grid-template-columns: repeat(2, 1fr)">
      <v-btn class="mr-2" @click="$router.back()">取消</v-btn>
      <v-btn class="ml-2" color="primary" @click="updateExp">保存</v-btn>
    </div>

    <div class="text-center">
      <v-snackbar v-model="snackbar" timeout="3000">
        {{ alertMessage }}

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
import {getStudentInfo} from "../api/sign";
import {editExp} from "../api/person";

export default {
name: "ExpEdit",
  data(){
  return{
    isTeacher: localStorage.getItem("isTeacher"),
    alertMessage: "",
    snackbar: false,
    personDetail: {
      name: "",
      schoolName: "",
      className: "",
      major: "",
      exp: "",
    },
    rules: {
      required: (value) => !!value || "必填.",
      phone: (v) =>
          /^[1][3,4,5,7,8,9][0-9]{9}$/.test(v) || "手机号码格式不正确",
    },
  };

},
  async updateExp(){
    let result = await editExp(this.$route.query.peId,this.$route.query.cId,this.personDetail.exp);
    switch (result.data.code) {
      case "200":
        this.alertMessage = "修改成功";
        this.snackbar = true;
        this.$router.back();
        break;
      default:
        this.alertMessage = result.data.msg;
        this.snackbar = true;
    }
  },
  async mounted() {
    let result = await getStudentInfo(this.$route.query.peId);
    this.personDetail.exp = this.$route.query.exp;
    if (result.data.data != null) {

      if (result.data.data.peName != null && result.data.data.peName !== "")
        this.personDetail.name = result.data.data.peName;
      if (result.data.data.classes != null && result.data.data.classes !== "")
        this.personDetail.className = result.data.data.classes;
      if (result.data.data.grade != null && result.data.data.grade !== "")
        this.personDetail.grade = result.data.data.grade;
    } else {
      this.snackbar = true;
      this.alertMessage = "数据初始化失败";
    }
  },};
</script>
import {getStudentInfo} from "../api/sign";

<style scoped>

</style>