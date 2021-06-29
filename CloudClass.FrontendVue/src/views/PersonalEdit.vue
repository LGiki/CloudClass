<template>
  <v-container>
    <!--      <div class="d-flex flex-no-wrap justify-space-between">-->
    <!--        <div>-->
    <!--          <v-card-title class="headline">林声睿</v-card-title>-->

    <!--          <v-card-subtitle class="mt-0">-->
    <!--            学号: {{ 200327063 }}-->
    <!--            <br />-->
    <!--            班级: {{ 123 }}-->
    <!--            <br />-->
    <!--            专业: {{ 123 }}-->
    <!--            <br />-->
    <!--            经验值： 100-->
    <!--          </v-card-subtitle>-->
    <!--        </div>-->
    <!--      <div style="display: flex; align-items: center">-->
    <!--          <v-avatar class="ma-3" size="125" tile>-->
    <!--            <img src="../assets/images/pikaqiu1.jpg" />-->
    <!--          </v-avatar>-->
    <!--        </div>-->
    <!--      </div>-->
    <v-text-field
      prepend-icon="mdi-pen"
      v-model="personDetail.name"
      label="姓名"
      :rules="[rules.required]"
    ></v-text-field>

    <v-text-field
      prepend-icon="mdi-android-studio"
      v-model="personDetail.className"
      label="班级"
      :rules="[rules.required]"
    ></v-text-field>

    <v-text-field
      prepend-icon="mdi-google-classroom"
      v-model="personDetail.grade"
      label="年级"
      :rules="[rules.required]"
    ></v-text-field>
    <v-icon>mdi-account-box</v-icon>
    <span
      :class="`ml-2 v-label ${
        $vuetify.theme.dark ? 'theme--dark' : 'theme--light'
      }`"
      >选择身份</span
    >
    <v-radio-group v-model="personDetail.isTeacher" mandatory class="pl-6">
      <v-radio label="我是学生" :value="0"></v-radio>
      <v-radio label="我是老师" :value="1"></v-radio>
    </v-radio-group>

    <div style="display: grid; grid-template-columns: repeat(2, 1fr)">
      <v-btn class="mr-2" @click="$router.back()">取消</v-btn>
      <v-btn class="ml-2" color="primary" @click="updatePersonalInfo"
        >保存</v-btn
      >
    </div>
    <!--      <v-list>-->
    <!--        <template v-for="(item, index) in items">-->
    <!--          <v-subheader-->
    <!--            class="font-weight-bold"-->
    <!--            v-if="item.header"-->
    <!--            :key="item.header"-->
    <!--          >-->
    <!--          </v-subheader>-->

    <!--          <v-divider v-else-if="item.divider" :key="index"></v-divider>-->

    <!--          <v-list-item v-else :key="item.title" @click="goEdit(index)">-->
    <!--            <v-list-item-content>-->
    <!--              <v-list-item-title v-html="item.title"> </v-list-item-title>-->
    <!--            </v-list-item-content>-->
    <!--            <v-list-item-icon>-->
    <!--              <v-icon left>mdi-chevron-right</v-icon>-->
    <!--            </v-list-item-icon>-->
    <!--          </v-list-item>-->
    <!--        </template>-->
    <!--      </v-list>-->

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
import { getPersonInfo, setPersonInfo } from "./api/person";

export default {
  name: "PersonalEdit",
  data() {
    return {
      isTeacher: localStorage.getItem("isTeacher"),
      alertMessage: "",
      snackbar: false,
      personDetail: {
        name: "",
        schoolName: "",
        className: "",
        major: "",
        isTeacher: 0,
      },
      items: [
        {
          title: "姓名",
        },
        { divider: true, inset: true },
        {
          title: "班级",
        },
        { divider: true, inset: true },
        {
          title: "年级",
        },
        { divider: true, inset: true },
      ],
      rules: {
        required: (value) => !!value || "必填.",
        phone: (v) =>
          /^[1][3,4,5,7,8,9][0-9]{9}$/.test(v) || "手机号码格式不正确",
      },
    };
  },
  methods: {
    goEdit(index) {
      console.log(index);
      switch (index) {
        case 0:
          if (this.isTeacher === "0") {
            this.$router.push({
              path: "/enterClass",
              query: { type: "school" },
            });
            this.$route.meta.stuTitle = "修改学校";
          } else if (this.isTeacher === "1") {
            this.$router.push({
              path: "/enterClass",
              query: { type: "exp" },
            });
            this.$route.meta.title = "修改经验值";
          }
          break;
        case 2:
          this.$router.push({
            path: "/enterClass",
            query: { type: "grade" },
          });
          this.$route.meta.stuTitle = "修改年级";
          break;
        case 4:
          this.$router.push({
            path: "/enterClass",
            query: { type: "major" },
          });
          this.$route.meta.stuTitle = "修改专业";
          break;
      }
    },
    async updatePersonalInfo() {
      console.log(this.personDetail);
      let result = await setPersonInfo(
        this.personDetail.name,
        this.personDetail.className,
        this.personDetail.grade,
        this.personDetail.isTeacher
      );
      switch (result.data.code) {
        case "200":
          if (this.personDetail.isTeacher != localStorage.getItem("isTeacher")) {
            localStorage.setItem("isTeacher", this.personDetail.isTeacher);
          }
          this.alertMessage = "保存成功";
          this.snackbar = true;
          this.$router.back()
          break;
        default:
          this.alertMessage = result.data.msg;
          this.snackbar = true;
      }
      console.log(result);
    },
  },
  async mounted() {
    let result = await getPersonInfo();
    if (result.data.data != null) {
      if (result.data.data.peName != null && result.data.data.peName !== "")
        this.personDetail.name = result.data.data.peName;
      if (result.data.data.classes != null && result.data.data.classes !== "")
        this.personDetail.className = result.data.data.classes;
      if (result.data.data.grade != null && result.data.data.grade !== "")
        this.personDetail.grade = result.data.data.grade;
      if (result.data.data.isTeacher != null)
        this.personDetail.isTeacher = result.data.data.isTeacher;
    } else {
      this.snackbar = true;
      this.alertMessage = "数据初始化失败";
    }
  },
};
</script>

<style scoped></style>
