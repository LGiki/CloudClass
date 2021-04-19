<template>
  <v-container>
    <v-text-field
      prepend-icon="mdi-account"
      v-model="username"
      label="用户名"
      outlined
      v-if="messageLogin === false"
    ></v-text-field>
    <v-text-field
      prepend-icon="mdi-lock"
      v-model="password"
      :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
      :rules="[rules.required]"
      :type="showPassword ? 'text' : 'password'"
      label="密码"
      outlined
      @click:append="showPassword = !showPassword"
      v-if="messageLogin === false"
    ></v-text-field>

    <v-text-field
      prepend-icon="mdi-account"
      v-model="username"
      label="手机号"
      outlined
      v-if="messageLogin === true"
    ></v-text-field>
    <div class="d-flex">
      <v-text-field
        :rules="[rules.required]"
        prepend-icon="mdi-android-messages"
        v-model="verifyCode"
        label="验证码"
        outlined
        v-if="messageLogin === true"
      ></v-text-field>
      <v-btn
        class="ma-2"
        color="primary"
        large
        v-if="messageLogin === true"
        @click="getVerifyCode"
        >获取验证码</v-btn
      >
    </div>

    <div class="d-flex justify-end">
      <v-btn
        class="ma-2 float-left"
        elevation="0"
        color="primary"
        small
        v-if="messageLogin === false"
        @click="messageLogin = true"
        >切换短信登录</v-btn
      >
      <v-btn
        class="ma-2 float-left"
        elevation="0"
        color="primary"
        small
        @click="messageLogin = false"
        v-if="messageLogin === true"
        >切换账号密码登录</v-btn
      >
      <v-btn class="ma-2" elevation="0" small v-if="messageLogin === false"
        >忘记密码？</v-btn
      >
      <v-btn class="ma-2" elevation="0" small @click="$router.push('/register')"
        >注册
      </v-btn>
    </div>
    <v-btn class="ma-2" color="primary" block @click="login">登录</v-btn>

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
// import { login } from "@/api/user";
import service from "@/utils/request";

export default {
  data() {
    return {
      username: "",
      password: "",
      verifyCode: "",
      showPassword: false,
      isSuccess: false,
      messageLogin: false,
      alertMessage: "用户名或密码有误",
      snackbar: false,
      rules: {
        required: (value) => !!value || "必填.",
      },
    };
  },
  methods: {
    async login() {
      if (this.messageLogin) {
        this.loginByMessage();
      } else {
        service
          .post("", {
            username: this.username,
            password: this.password,
          })
          .then(function (res) {
            this.isSuccess = res.data.isSuccess;
            //判断登录成功，设置用户名
            if (this.isSucceed) {
              this.GLOBAL.username = this.username;
              this.$router.push("/class");
            } else {
              this.alertMessage = "用户名或密码有误";
              this.snackbar = true;
            }
          })
          .catch(function (error) {
            console.log(error);
          });

        // let loginResponse = await login(this.username.trim(), this.password);
        // if (loginResponse.status === 200 && loginResponse.data.code === '200') {
        //   this.$router.push("/class");
        // } else {
        //   console.log("登录失败");
        // }
      }
    },

    async loginByMessage() {
      if (this.verifyCode === "") {
        this.alertMessage = "请先获取验证码";
        this.snackbar = true;
        return;
      }
      service
        .post("", {
          username: this.username,
          verifyCode: this.verifyCode,
        })
        .then(function (res) {
          this.isSuccess = res.data.isSuccess;
          //判断登录成功，设置用户名
          if (this.isSucceed) {
            this.GLOBAL.username = this.username;
            this.$router.push("/class");
          } else {
            this.snackbar = true;
          }
        })
        .catch(function (error) {
          console.log(error);
        });

      // let loginResponse = await login(this.username.trim(), this.password);
      // if (loginResponse.status === 200 && loginResponse.data.code === '200') {
      //   this.$router.push("/class");
      // } else {
      //   console.log("登录失败");
      // }
    },

    getVerifyCode() {},
  },
};
</script>
