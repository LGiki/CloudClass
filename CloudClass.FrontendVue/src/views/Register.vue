<template>
  <v-container>
    <v-text-field
      prepend-icon="mdi-account"
      :rules="[rules.required]"
      v-model="username"
      label="用户名"
      outlined
    ></v-text-field>
    <v-text-field
      prepend-icon="mdi-lock"
      v-model="password"
      :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
      :rules="[rules.required, rules.min]"
      :type="showPassword ? 'text' : 'password'"
      name="input-10-1"
      label="密码"
      hint="至少8个字符"
      counter
      outlined
      @click:append="showPassword = !showPassword"
    ></v-text-field>
    <v-text-field
      prepend-icon="mdi-lock"
      v-model="rePassword"
      :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
      :rules="[rules.required, rules.min]"
      :type="showPassword ? 'text' : 'password'"
      name="input-10-1"
      label="重复密码"
      hint="至少8个字符"
      counter
      outlined
      @click:append="showPassword = !showPassword"
    ></v-text-field>
    <v-text-field
      :rules="[rules.required]"
      prepend-icon="mdi-cellphone"
      v-model="phone"
      label="手机号"
      outlined
    ></v-text-field>
    <div class="d-flex">
      <v-text-field
        :rules="[rules.required]"
        prepend-icon="mdi-android-messages"
        v-model="verifyCode"
        label="验证码"
        outlined
      ></v-text-field>
      <v-btn class="ma-2" color="primary" large>获取验证码</v-btn>
    </div>

    <div class="d-flex">
      <span> 注册身份： </span>
      <v-checkbox
        v-model="selected"
        label="学生"
        input-value="!selectedType"
        value="student"
      ></v-checkbox>
      <v-checkbox
        v-model="selected"
        label="教师"
        value="teacher"
        input-value="selectedType"
      ></v-checkbox>
    </div>

    <v-btn color="primary" block @click="register">注册</v-btn>

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
import service from "@/utils/request";

export default {
  data() {
    return {
      username: "",
      password: "",
      rePassword: "",
      phone: "",
      verifyCode: "",
      showPassword: false,
      selected: "student",
      selectedType: false,
      snackbar: false,
      alertMessage: "",
      isRegisterSuccess: false,
      rules: {
        required: (value) => !!value || "必填",
        min: (v) => v.length >= 8 || "至少8个字符",
      },
    };
  },
  methods: {
    register() {
      if (
        this.username === "" ||
        this.password === "" ||
        this.rePassword === "" ||
        this.phone === ""
      ) {
        this.alertMessage = "请补充完整信息";
        this.snackbar = true;
        return;
      } else if (this.password != this.rePassword) {
        this.alertMessage = "两次输入的密码不一致";
        this.snackbar = true;
        return;
      } else if (this.verifyCode === "") {
        this.alertMessage = "请获取验证码";
        this.snackbar = true;
        return;
      }

      service
        .post("", {
          username: this.username,
          password: this.password,
          phone: this.phone,
          verifyCode: this.verifyCode,
        })
        .then(function (res) {
          this.isRegisterSuccess = res.data.isRegisterSuccess;
          //判断注册成功，设置用户名
          if (this.isSucceed) {
            this.$router.push("/class");
          } else {
            this.alertMessage = "用户注册失败";
            this.snackbar = true;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>
