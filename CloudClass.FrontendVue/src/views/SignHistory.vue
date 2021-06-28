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
    <v-list class="overflow-x-hidden">
      <v-subheader>签到历史</v-subheader>
      <v-list-item-group
        color="primary"
      >
        <template v-for="(item, i) in items">
          <v-divider
            :key="i"
            v-if="item.divider"
            :inset="item.inset" />
          <v-list-item
            :key="i"
            v-else
          >
            <template>
              <v-list-item-icon>
                <v-icon>mdi-calendar</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title v-text="item.title"></v-list-item-title>
              </v-list-item-content>
              <v-btn
                class="primary"
                @click="
                  () =>
                    $router.push({
                      name: 'SignDetail',
                      query: { siId: item.siId },
                    })
                "
              >
                详情
              </v-btn>
            </template>
          </v-list-item>
        </template>
      </v-list-item-group>
    </v-list>
    <v-snackbar v-model="snackbar" timeout="3000">
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">
          关闭
        </v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
import { getFinishedSignUp } from "../api/sign";
import { getClassData } from "../api/class";
import QRCode from "qrcodejs2";

export default {
  name: "SignHistory",
  data() {
    return {
      classes: {
        title: "",
        teacher: "",
        semester: "",
        classNumber: ""
      },
      items: [],
      snackbar: false,
      snackbarText: ""
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
        correctLevel: QRCode.CorrectLevel.H
      });
    },
    async setClassData() {
      let result = await getClassData(this.$route.query.id);
      this.classes.teacher = result.data.data.teacher;
      this.classes.semester = result.data.data.term;
      this.classes.title = result.data.data.ccName;
    },
    getSignHistory() {
      getFinishedSignUp(this.$route.query.cId).then(res => {
        if (res.data.code === "200") {
          this.items = [];
          let divider = { divider: true };
          for (const item of res.data.data) {
            this.items.push({
              title: item.date.replaceAll("T", " "),
              siId: item.siId
            });
            this.items.push(divider);
          }
          if (this.items.length > 0) {
            this.items.splice(this.items.length - 1, 1);
          }
        } else {
          this.snackbarText = "获取签到详情失败，请稍后再试";
          this.snackbar = true;
        }
      });
    }
  },
  mounted() {
    this.createQrCode();
    this.setClassData();
    this.getSignHistory();
  }
};
</script>
