<template>
  <div
    style="height: 100%; background-color: rgba(0, 0, 0, 0.02)"
    class="d-flex align-center"
  >
    <v-container>
      <h1
        style="font-size: 2.5em; color: #1976d2"
        class="d-flex justify-center ma-5"
      >
        到云APP - 登录
      </h1>
      <v-card>
        <v-tabs v-model="loginType" grow class="mb-5">
          <v-tab href="#password-login"> 账号密码登录</v-tab>
          <v-tab href="#sms-login"> 短信登录 </v-tab>
        </v-tabs>
        <div class="pa-4">
          <v-tabs-items v-model="loginType">
            <v-tab-item value="password-login" class="ma-1">
              <v-text-field
                prepend-icon="mdi-account"
                v-model="username"
                label="用户名"
                :rules="[rules.required]"
                outlined
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
              ></v-text-field>
            </v-tab-item>
            <v-tab-item value="sms-login" class="ma-1">
              <v-text-field
                prepend-icon="mdi-account"
                v-model="phone"
                :rules="[rules.required, rules.phone]"
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
                <v-btn
                  class="ma-2"
                  color="primary"
                  large
                  :disabled="disabled"
                  @click="getVerifyCode"
                  >{{ getVerifyCodeText }}
                </v-btn>
              </div>
            </v-tab-item>
          </v-tabs-items>
        </div>
      </v-card>
      <div class="d-flex justify-space-between flex-nowrap mt-2">
        <div class="d-flex justify-start flex-nowrap">
          <v-btn
            class="ma-2 float-left"
            elevation="0"
            small
            href="https://github.com/login/oauth/authorize?client_id=1bcc057d1fa210ff880a&redirect_uri=http://localhost:8080/github-redirect"
          >
            <v-icon color="darken-2">mdi-github</v-icon>
            Github登录
          </v-btn>
        </div>
        <div class="d-flex flex-nowrap flex-end">
          <v-btn class="ma-2" elevation="0" small @click="registerByPhone"
            >注册
          </v-btn>
          <v-btn class="ma-2" elevation="0" small>忘记密码？ </v-btn>
        </div>
      </div>
      <v-btn class="mt-2" color="primary" block @click="login">登录</v-btn>
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
  </div>
</template>

<script>
import { loginByUsername, sendSMS, loginByMessage } from "../api/user";
//import axios from "axios";

export default {
  data() {
    return {
      tab: null,
      loginType: "password-login",
      username: "",
      password: "",
      verifyCode: "",
      phone: "",
      showPassword: false,
      isSuccess: false,
      alertMessage: "用户名或密码有误",
      getVerifyCodeText: "获取验证码",
      snackbar: false,
      disabled: false,
      time: 60,
      rules: {
        required: (value) => !!value || "必填.",
        phone: (v) =>
          /^[1][3,4,5,7,8,9][0-9]{9}$/.test(v) || "手机号码格式不正确",
      },
    };
  },
  methods: {
    async login() {
      if (this.loginType === "sms-login") {
        if (this.phone === "" || this.verifyCode === "") {
          this.alertMessage = "请输入手机号及验证码";
          this.snackbar = true;
          return;
        }
        let result = await loginByMessage(this.phone, this.verifyCode);
        switch (result.data.code) {
          case "200":
            this.alertMessage = "登录成功";
            this.snackbar = true;
            this.$store.commit("token/SET_TOKEN", result.data.token);
            this.$router.push("/class");
            break;
          default:
            this.alertMessage = result.data.msg;
            this.snackbar = true;
        }
      } else {
        if (this.username === "" || this.password === "") {
          this.alertMessage = "请输入用户名和密码";
          this.snackbar = true;
          return;
        }

        let result = await loginByUsername(this.username.trim(), this.password);

        switch (result.data.code) {
          case "200":
            this.alertMessage = "登录成功";
            this.snackbar = true;
            this.$store.commit("token/SET_TOKEN", result.data.token);
            //设置是教师还是学生
            this.$store.commit("userInfo/SET_TEA_TYPE", "1");
            this.$router.push("/class");
            break;
          default:
            this.alertMessage = result.data.msg;
            this.snackbar = true;
        }
      }
    },

    async getVerifyCode() {
      if (this.phone === "") {
        this.alertMessage = "请填写正确手机号";
        this.snackbar = true;
        return;
      }

      let result = await sendSMS(this.phone);

      if (result.data.code === null) {
        this.alertMessage = "网络连接失败";
        this.snackbar = true;
        return;
      }
      switch (parseInt(result.data.code)) {
        case 200:
          this.alertMessage = "已发送验证码";
          this.snackbar = true;
          this.disabled = true;
          this.timer();
          break;
        default:
          this.alertMessage = result.data.msg;
          this.snackbar = true;
      }
    },
    registerByPhone() {
      this.$router.push({
        path: "/register",
        query: {
          type: "phone",
        },
      });
    },
    registerByName() {
      this.$router.push({
        path: "/register",
        query: {
          type: "name",
        },
      });
    },
    timer() {
      if (this.time > 0) {
        this.disabled = true;
        this.time--;
        this.getVerifyCodeText = this.time + "秒";
        setTimeout(this.timer, 1000);
      } else {
        this.time = 60;
        this.getVerifyCodeText = "获取验证码";
        this.disabled = false;
      }
    },
  },
};
</script>
