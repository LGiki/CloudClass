<template>
  <v-container>
    <v-card class="mb-5">
      <div class="d-flex flex-no-wrap justify-space-between">
        <div>
          <v-card-title class="headline">林声睿</v-card-title>

          <v-card-subtitle class="mt-0">
            学号: {{ 200327063 }}
            <br />
            班级: {{ 123 }}
            <br />
            专业: {{ 123 }}
            <br />
            经验值： 100
          </v-card-subtitle>
        </div>
        <div style="display: flex; align-items: center">
          <v-avatar class="ma-3" size="125" tile>
            <img src="../assets/images/pikaqiu1.jpg" />
          </v-avatar>
        </div>
      </div>
    </v-card>
    <v-list>
      <template v-for="(item, index) in items">
        <v-subheader
          class="font-weight-bold"
          v-if="item.header"
          :key="item.header"
        >
        </v-subheader>
        <v-divider v-else-if="item.divider" :key="index"></v-divider>
        <v-list-item v-else :key="item.title" @click="goEdit(index)">
          <v-list-item-content>
            <v-list-item-title v-html="item.title"></v-list-item-title>
          </v-list-item-content>
          <v-list-item-icon>
            <v-icon left>mdi-chevron-right</v-icon>
          </v-list-item-icon>
        </v-list-item>
      </template>
    </v-list>
    <v-dialog v-model="changePasswordDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="text-h5">修改密码</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-form ref="changePasswordForm">
              <v-text-field
                v-model="oldPassword"
                :rules="[rules.required]"
                hint="请输入旧密码"
                label="旧密码"
                prepend-icon="mdi-form-textbox-password"
              >
              </v-text-field>
              <v-text-field
                v-model="newPassword"
                :rules="[rules.required]"
                hint="请输入新密码"
                label="新密码"
                prepend-icon="mdi-form-textbox-password"
              >
              </v-text-field>
              <v-text-field
                v-model="reNewPassword"
                :rules="[rules.required]"
                hint="请再次输入新密码"
                label="再次输入新密码"
                prepend-icon="mdi-form-textbox-password"
              >
              </v-text-field>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            text
            @click="changePasswordDialog = false"
          >
            取消
          </v-btn>
          <v-btn color="blue darken-1" text @click="handleChangePassword">
            确定
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
export default {
  name: "Personal",
  data() {
    return {
      changePasswordDialog: false,
      oldPassword: "",
      newPassword: "",
      reNewPassword: "",
      rules: {
        required: (value) => !!value || "必填",
      },
      items: [
        { divider: true, inset: true },
        {
          title: "个人信息修改",
          subtitle: ``,
        },
        { divider: true, inset: true },
        {
          title: "签到记录",
          subtitle: ``,
        },
        { divider: true, inset: true },
        {
          title: "修改密码",
          subtitle: "",
        },
        { divider: true, inset: true },
        {
          title: "退出登录",
          subtitle: "",
        },
        { divider: true, inset: true },
      ],
    };
  },
  methods: {
    logout() {
      this.$store.commit("token/REMOVE_TOKEN");
      this.$router.replace({ path: "/" });
    },
    handleChangePassword() {
      if (this.$refs.changePasswordForm.validate()) {
        //TODO: 完成修改密码
      }
    },
    goEdit(index) {
      console.log(index);
      if (index === 1) {
        this.$router.push("/personalEdit");
      } else if (index === 3) {
        this.$router.push("/signRecord");
      } else if (index === 5) {
        // this.$router.push({
        //   path: "enterClass",
        //   query: { type: "changePass" }
        // });
        this.changePasswordDialog = true;
      } else if (index === 7) {
        this.logout();
      }
    },
  },
};
</script>

<style scoped></style>
