<template>
  <v-container>
    <v-card class="mb-5">
      <div class="d-flex flex-no-wrap justify-space-between">
        <div>
          <v-card-title class="headline">{{
            classInfo.className
          }}</v-card-title>

          <v-card-subtitle class="mt-0">
            班课号: {{ classInfo.classNumber }}
            <br />
            学期: {{ classInfo.semester }}
            <br />
            学校: {{ classInfo.school }}
            <br />
            院系: {{ classInfo.depart }}
          </v-card-subtitle>
        </div>
        <div style="display: flex; align-items: center">
          <v-avatar class="ma-3" size="125" tile>
            <img src="../assets/images/pikaqiu1.jpg" />
          </v-avatar>
        </div>
      </div>
      <!-- 确认退出班课对话框 -->
      <template>
        <div class="text-center">
          <v-dialog v-model="dialog" width="500">
            <v-card>
              <v-card-title class="text-h5 grey lighten-2"> 提示 </v-card-title>

              <v-card-text class="mt-2"> 确认退出班课? </v-card-text>

              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" text @click="exit"> 确认 </v-btn>
                <v-btn color="primary" text @click="dialog = false">
                  取消
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </div>
      </template>

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
              <v-list-item-title v-html="item.title"> </v-list-item-title>
            </v-list-item-content>
            <v-list-item-icon>
              <v-icon left>mdi-chevron-right</v-icon>
            </v-list-item-icon>
          </v-list-item>
        </template>
      </v-list>
    </v-card>

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
import { getClassData } from "@/api/class";
import { exitClass } from "./api/sign";

export default {
  name: "Personal",
  data() {
    return {
      dialog: false,
      alertMessage: "",
      snackbar: false,
      items: [
        { divider: true, inset: true },
        {
          title: "班课状态设置",
          subtitle: ``,
        },
        { divider: true, inset: true },
        {
          title: "退出班课",
          subtitle: ``,
        },
      ],
      classInfo: {
        className: "",
        semester: "",
        classNumber: 10001,
        school: "",
        depart: "",
        cId: "1",
      },
    };
  },
  methods: {
    async goEdit(index) {
      if (index === this.items.length - 3) {
        this.$router.push({
          path: "/classSetupDetail",
          query: {
            cId: this.classInfo.cId,
            cNumber: this.$route.query.cNumber,
          },
        });
      } else if (index === this.items.length - 1) {
        this.dialog = true;
      }
    },
    async exit() {
      this.dialog = false;

      let result = await exitClass(this.classInfo.cId + "");
      switch (result.data.code) {
        case "200":
          this.alertMessage = "退出班课成功";
          this.snackbar = true;

          this.$router.push("class");
          break;
        default:
          this.alertMessage = result.data.msg;
          this.snackbar = true;
      }
    },
    async initClassData() {
      let result = await getClassData(this.$route.query.cNumber);
      this.classInfo.semester = result.data.data.term;
      this.classInfo.className = result.data.data.ccName;
      this.classInfo.classNumber = this.$route.query.cNumber;
      this.classInfo.school = result.data.data.university;
      this.classInfo.depart = result.data.data.college;
      this.classInfo.cId = result.data.data.cId;
    },
  },
  async mounted() {
    this.initClassData();
    if (localStorage.getItem("isTeacher") !== "1") {
      this.items.splice(0, 2);
    }
  },
};
</script>

<style scoped></style>
