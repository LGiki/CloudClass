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

      <v-list three-line>
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
              <v-list-item-subtitle
                v-html="item.subtitle"
              ></v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-content class="ml-16 font-weight-bold red--text">
              {{ item.grade }}积分</v-list-item-content
            >
          </v-list-item>
        </template>
      </v-list>
    </v-card>

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
        classNumber: 10004,
      },

      items: [
        { header: "成员列表" },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
          title: "魏璐炜",
          subtitle: `2003270xx`,
          grade: 27,
        },
        { divider: true, inset: true },
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
          title: "林家琪",
          subtitle: `2003270xx`,
          grade: 28,
        },
        { divider: true, inset: true },
        {
          avatar: require("../assets/images/pikaqiu1.jpg"),
          title: "林声睿",
          subtitle: "2003270xx",
          grade: 26,
        },
        { divider: true, inset: true },
        {
          avatar: require("../assets/images/pikaqiu2.jpg"),
          title: "戴锦坤",
          subtitle: "2003270xx",
          grade: 29,
        },
      ],
    };
  },
  methods: {
    createQrCode() {
      new QRCode(this.$refs.qrCodeUrl, {
        text: this.$route.query.id,
        width: 100,
        height: 100,
        colorDark: "#00aa00",
        colorLight: "#ffffff",
        correctLevel: QRCode.CorrectLevel.H,
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
  },
  async mounted() {
    this.createQrCode();
    this.judgeShowSuccess();
    this.setClassData();
  },
};
</script>

<style scoped></style>
