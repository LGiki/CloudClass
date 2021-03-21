<template>
  <v-container>
    <div class="text-h4 text-center mb-16 mt-16">班级：工程实践</div>
    <div class="d-flex justify-center align-self-center pt-16 pb-16">
      <v-btn x-large class="primary mb-16 mt-16" @click="sign">
        <v-icon left>mdi-clipboard-account</v-icon>
        签到
      </v-btn>
    </div>
    <v-snackbar v-model="snackbar">
      {{ text }}
      <template v-slot:action="{ attrs }">
        <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">
          关闭
        </v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    snackbar: false,
    text: `签到成功！`,
  }),
  methods: {
    sign() {
      this.snackbar = true;
      // this.$router.back();
    },
  },
};
</script>
