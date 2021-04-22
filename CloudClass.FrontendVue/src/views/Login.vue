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
        v-model="phone"
        :rules="[rules.required, rules.phone]"
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
          >获取验证码
        </v-btn>
      </div>

      <div class="d-flex justify-end">
        <v-btn
          class="ma-2 float-left"
          elevation="0"
          color="primary"
          small
          v-if="messageLogin === false"
          @click="messageLogin = true"
          >切换短信登录
        </v-btn>
        <v-btn
          class="ma-2 float-left"
          elevation="0"
          color="primary"
          small
          @click="messageLogin = false"
          v-if="messageLogin === true"
          >切换账号密码登录
        </v-btn>
        <v-btn class="ma-2" elevation="0" small v-if="messageLogin === false"
          >忘记密码？
        </v-btn>
        <v-btn
          class="ma-2"
          elevation="0"
          small
          @click="$router.push('/register')"
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
  </div>
</template>

<script>
import { loginByUsername, sendSMS } from "../api/user";

export default {
  data() {
    return {
      username: "",
      password: "",
      verifyCode: "",
      phone: "",
      showPassword: false,
      isSuccess: false,
      messageLogin: false,
      alertMessage: "用户名或密码有误",
      snackbar: false,
      rules: {
        required: (value) => !!value || "必填.",
        phone: (v) =>
          /^[1][3,4,5,7,8,9][0-9]{9}$/.test(v) || "手机号码格式不正确",
      },
    };
  },
  methods: {
    async login() {
      if (this.messageLogin) {
        if (this.username === "" || this.verifyCode === "") {
          this.alertMessage = "请输入手机号及验证码";
          this.snackbar = true;
          return;
        }
        let result = await this.loginByMessage(this.username, this.verifyCode);
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
  },
};
</script>
