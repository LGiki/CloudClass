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
        到云APP - 注册
      </h1>
      <v-text-field
        prepend-icon="mdi-account"
        :rules="[rules.required]"
        v-model="username"
        label="用户名"
        outlined
        v-if="registerType === 'name'"
      ></v-text-field>
      <v-text-field
        prepend-icon="mdi-lock"
        v-model="password"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, rules.min]"
        :type="showPassword ? 'text' : 'password'"
        label="密码"
        hint="至少8个字符"
        counter
        outlined
        v-if="registerType === 'name'"
        @click:append="showPassword = !showPassword"
      ></v-text-field>
      <v-text-field
        prepend-icon="mdi-lock"
        v-model="rePassword"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, rules.min]"
        :type="showPassword ? 'text' : 'password'"
        label="重复密码"
        hint="至少8个字符"
        counter
        outlined
        v-if="registerType === 'name'"
        @click:append="showPassword = !showPassword"
      ></v-text-field>
      <v-text-field
        :rules="[rules.required, rules.phone]"
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
          type="number"
        ></v-text-field>
        <v-btn
          class="ma-2"
          color="primary"
          large
          @click="getVerifyCode"
          :disabled="disabled"
          >{{ getVerifyCodeText }}</v-btn
        >
      </div>

      <div class="d-flex">
        <div class="d-flex align-center">
          <v-icon style="margin-right: 9px">mdi-card-account-details </v-icon>
          <span
            :style="`color:${
              $vuetify.theme.dark
                ? 'rgba(255, 255, 255, 0.7)'
                : 'rgba(0, 0, 0, .6)'
            };`"
            >注册身份：</span
          >
        </div>
        <v-radio-group v-model="role" row>
          <v-radio label="学生" value="student"></v-radio>
          <v-radio label="教师" value="teacher"></v-radio>
        </v-radio-group>
      </div>

      <v-btn color="primary" block @click="registerIn">注册</v-btn>

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
import { registerByName } from "../api/user";
import { registerByPhone } from "../api/user";
import { sendSMS } from "../api/user";

export default {
  data() {
    return {
      username: "",
      password: "",
      rePassword: "",
      phone: "",
      verifyCode: "",
      registerType: "",
      getVerifyCodeText: "获取验证码",
      showPassword: false,
      role: "student",
      snackbar: false,
      alertMessage: "",
      isTeacher: "0",
      isRegisterSuccess: false,
      disabled: false,
      time: 60,
      rules: {
        required: (value) => !!value || "必填",
        min: (v) => v.length >= 8 || "至少8个字符",
        phone: (v) =>
          /^[1][3,4,5,7,8,9][0-9]{9}$/.test(v) || "手机号码格式不正确",
      },
    };
  },
  methods: {
    async registerIn() {
      if (
        this.registerType === "name" &&
        (this.username === "" ||
          this.password === "" ||
          this.rePassword === "" ||
          this.phone === "")
      ) {
        this.alertMessage = "请补充完整信息";
        this.snackbar = true;
        return;
      } else if (
        this.registerType === "name" &&
        this.password !== this.rePassword
      ) {
        this.alertMessage = "两次输入的密码不一致";
        this.snackbar = true;
        return;
      } else if (this.verifyCode === "") {
        this.alertMessage = "请获取验证码";
        this.snackbar = true;
        return;
      }
      if (this.role === "teacher") {
        this.isTeacher = "1";
      } else {
        this.isTeacher = "0";
      }

      let result;
      if (this.registerType === "name") {
        result = await registerByName(
          this.phone,
          this.username.trim(),
          this.password,
          this.verifyCode,
          this.isTeacher
        );
      } else if (this.registerType === "phone") {
        result = await registerByPhone(
          this.phone,
          this.verifyCode,
          this.isTeacher
        );
      }

      console.log(result.data);

      switch (parseInt(result.data.code)) {
        case 200:
          this.$router.push("/login");
          this.alertMessage = "注册成功";
          this.snackbar = true;
          break;
        default:
          this.alertMessage = result.data.msg;
          this.snackbar = true;
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
    //倒计时60s
    timer() {
      if (this.time > 0) {
        this.disabled = true;
        this.time--;
        this.getVerifyCodeText = this.time + "秒";
        setTimeout(this.timer, 1000);
      } else {
        this.time = 0;
        this.getVerifyCodeText = "获取验证码";
        this.disabled = false;
      }
    },
  },
  mounted() {
    this.registerType = this.$route.query.type;
    console.log(this.registerType);
  },
};
</script>
