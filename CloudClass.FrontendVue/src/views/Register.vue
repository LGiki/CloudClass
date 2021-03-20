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
    <v-btn class="ma-2" color="primary" block>注册</v-btn>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      username: "",
      password: "",
      rePassword: "",
      phone: "",
      verifyCode: "",
      showPassword: false,
      rules: {
        required: (value) => !!value || "必填",
        min: (v) => v.length >= 8 || "至少8个字符",
      },
    };
  },
};
</script>
