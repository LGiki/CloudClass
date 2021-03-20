<template>
  <v-container>
    <v-text-field
      prepend-icon="mdi-account"
      v-model="username"
      label="用户名"
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
    <div class="d-flex justify-end">
      <v-btn class="ma-2" elevation="0" small>忘记密码？</v-btn>
      <v-btn class="ma-2" elevation="0" small @click="$router.push('/register')"
        >注册</v-btn
      >
    </div>
    <v-btn class="ma-2" color="primary" block>登录</v-btn>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      username: "",
      password: "",
      showPassword: false,
      rules: {
        required: (value) => !!value || "必填.",
        emailMatch: () => `The email and password you entered don't match`,
      },
    };
  },
};
</script>
