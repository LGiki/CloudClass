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
import { loginByUsername } from "../api/user";

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
        this.loginByMessage();
      } else {
        if (this.username === "" || this.password === "") {
          this.alertMessage = "请输入用户名和密码";
          this.snackbar = true;
          return;
        }

        this.$router.push("/class");
        let result = await loginByUsername(this.username.trim(), this.password);
        console.log(result);

        // .then(function (res) {
        //   this.isSuccess = res.data.isSuccess;
        //   //判断登录成功，设置用户名
        //   if (this.isSucceed) {
        //     this.GLOBAL.username = this.username;
        //     this.$router.push("/class");
        //   } else {
        //     this.alertMessage = "用户名或密码有误";
        //     this.snackbar = true;
        //   }
        // })
      }
    },

    async loginByMessage() {
      if (this.phone === "") {
        this.alertMessage = "请填写正确的手机号";
        this.snackbar = true;
        return;
      } else if (this.verifyCode === "") {
        this.alertMessage = "请先获取验证码";
        this.snackbar = true;
        return;
      }
      // let result = await loginByUsername(this.phone.trim(), this.verifyCode);

      // service
      //   .post("", {
      //     username: this.username.trim(),
      //     verifyCode: this.verifyCode,
      //   })
      //   .then(function (res) {
      //     this.isSuccess = res.data.isSuccess;
      //     //判断登录成功，设置用户名
      //     if (this.isSucceed) {
      //       this.GLOBAL.username = this.username;
      //       this.$router.push("/class");
      //     } else {
      //       this.snackbar = true;
      //     }
      //   })
      //   .catch(function (error) {
      //     console.log(error);
      //   });
    },

    getVerifyCode() {},
  },
};
</script>
