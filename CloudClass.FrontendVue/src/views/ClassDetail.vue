<template>
  <v-container>
    <v-card class="mb-5">
      <div class="d-flex flex-no-wrap justify-space-between">
        <div>
          <v-card-title class="headline" v-text="classes.title"></v-card-title>
          <v-card-subtitle class="mt-0 font-weight-bold">
            班级号: {{ this.$route.query.id }}
            <br />
            任课教师: {{ classes.teacher }}
            <br />
            学期: {{ classes.semester }}
          </v-card-subtitle>
        </div>
        <div style="display: flex; align-items: center">
          <v-avatar class="ma-3" size="125" tile>
            <div ref="qrCodeUrl" class="qrcode"></div>
          </v-avatar>
        </div>
      </div>
    </v-card>
    <v-list two-line class="overflow-x-hidden">
      <v-row>
        <v-col>
          <v-subheader>成员列表</v-subheader>
        </v-col>
        <v-col>
          <div style="height: 100%;display: flex; justify-content: flex-end; align-items: center;margin-right: 16px">
            <span>经验值排序：</span>
            <v-btn
              icon
              @click="handleOrderByExp"
            >
              <v-icon>{{ expOrder === 1 ? "mdi-sort-ascending" : "mdi-sort-descending" }}</v-icon>
            </v-btn>
          </div>
        </v-col>
      </v-row>
      <template v-for="(item, index) in items">
        <v-subheader
          class="font-weight-bold"
          v-if="item.header"
          :key="item.header"
          v-text="item.header"
        >
        </v-subheader>

        <v-divider
          v-else-if="item.divider"
          :key="index"
          :inset="item.inset"
        ></v-divider>

        <v-list-item v-else :key="item.title">
          <v-list-item-avatar>
            <v-img :src="item.avatar"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-html="item.title"></v-list-item-title>
            <!--
            <v-list-item-subtitle
              v-html="item.subtitle"
            ></v-list-item-subtitle>
            -->
          </v-list-item-content>
          <v-list-item-content class="ml-16 font-weight-bold red--text">
            {{ item.grade }}经验值
          </v-list-item-content
          >
        </v-list-item>
      </template>
    </v-list>
    <v-snackbar v-model="snackbar" timeout="3000">
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
import { getClassData } from "@/api/class";
import QRCode from "qrcodejs2";
import { queryClassMembers } from "./api/class";

export default {
  name: "ClassDetail",
  data() {
    return {
      snackbar: false,
      text: "",
      classes: {
        title: "工程实践",
        teacher: "池芝标",
        semester: "2021-1",
        classNumber: 10004
      },
      items: [],
      expOrder: 1
    };
  },
  methods: {
    handleOrderByExp() {
      this.expOrder = this.expOrder === 1 ? -1 : 1;
      this.setClassMembersData();
    },
    createQrCode() {
      new QRCode(this.$refs.qrCodeUrl, {
        text: this.$route.query.id,
        width: 100,
        height: 100,
        colorDark: "#00aa00",
        colorLight: "#ffffff",
        correctLevel: QRCode.CorrectLevel.H
      });
    },
    judgeShowSuccess() {
      if (this.$route.query.showSuccess) {
        this.snackbar = true;
        this.text = "创建成功";
      }
    },
    async setClassData() {
      let result = await getClassData(this.$route.query.id);
      this.classes.teacher = result.data.data.teacher;
      this.classes.semester = result.data.data.term;
      this.classes.title = result.data.data.ccName;
    },
    async setClassMembersData() {
      let result = await queryClassMembers(this.$route.query.cId, this.expOrder);
      if (result.data != null) {
        this.items = [];
        let divider = { divider: true };
        for (let i = 1; i <= result.data.data.length; i++) {
          let item = {
            title: result.data.data[i - 1].peName,
            grade: result.data.data[i - 1].exp,
            avatar: require("../assets/images/pikaqiu1.jpg")
          };
          this.items.push(item);
          this.items.push(divider);
        }
        if (this.items.length > 0) {
          this.items.splice(this.items.length - 1, 1);
        }
      }
    }
  },
  async mounted() {
    this.createQrCode();
    this.judgeShowSuccess();
    await this.setClassData();
    await this.setClassMembersData();
  }
};
</script>

<style scoped>
/*.class-info {*/
/*  position: sticky;*/
/*  top: 68px;*/
/*  z-index: 2;*/
/*}*/
</style>
