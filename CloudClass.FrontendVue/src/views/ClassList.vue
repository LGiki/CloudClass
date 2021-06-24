<template>
  <v-container>
    <v-card
      class="mb-5"
      v-for="(classInfo, index) in classes"
      v-bind:key="index"
    >
      <div class="d-flex flex-no-wrap justify-space-between">
        <div>
          <v-card-title
            class="headline"
            v-text="classInfo.ccName"
          ></v-card-title>
          <v-card-subtitle
            v-text="classInfo.peName + classInfo.term"
          ></v-card-subtitle>
          <v-card-actions>
            <div style="display: flex; flex-direction: row; flex-wrap: wrap">
              <v-btn
                class="ml-1 mt-1 mb-1 elevation-0"
                small
                @click="goClassDetail(index)"
              >
                <v-icon left dark color="red darken-2"
                  >mdi-account-circle
                </v-icon>
                成员
              </v-btn>
              <v-btn class="ml-1 mt-1 mb-1 elevation-0" small>
                <v-icon left dark color="orange darken-2"
                  >mdi-clipboard-text-outline
                </v-icon>
                统计
              </v-btn>
              <v-btn
                class="ml-1 mt-1 mb-1 elevation-0"
                small
                @click="goClassSignUp(index)"
              >
                <v-icon left dark color="green darken-2"
                  >mdi-check-circle
                </v-icon>
                签到
              </v-btn>
              <v-btn
                class="ml-1 mt-1 mb-1 elevation-0"
                small
                @click="goClassSetup(index)"
              >
                <v-icon left dark color="blue darken-2">mdi-cog </v-icon>
                设置
              </v-btn>
            </div>
          </v-card-actions>
        </div>
        <div style="display: flex; align-items: center">
          <v-avatar class="ma-3" size="125" tile>
            <v-img
              class="class-image"
              max-width="120px"
              max-height="120px"
              :src="
                classInfo.imgUrl && classInfo.imgUrl !== ''
                  ? classInfo.imgUrl
                  : classDefaultAvatar
              "
            ></v-img>
          </v-avatar>
        </div>
      </div>
    </v-card>
    <div style="width: 100%; display: flex; justify-content: center">
      <v-btn
        elevation="6"
        fab
        color="primary"
        bottom
        right
        fixed
        style="bottom: 75px"
        @click="showList"
      >
        <v-icon dark>mdi-plus</v-icon>
      </v-btn>
    </div>
    <v-overlay :absolute="absolute" :value="overlay" opacity="0.7">
      <div class="d-flex flex-no-wrap flex-column">
        <v-btn
          v-if="isTeacher === '0'"
          elevation="2"
          x-large
          class="mb-10"
          color="success"
          @click="enterByClassCode"
        >
          <v-icon dark left>mdi-form-textbox-password</v-icon>
          通过班课号加入
        </v-btn>
        <v-btn
          v-if="isTeacher === '0'"
          x-large
          class="mb-10"
          elevation="2"
          color="success"
          @click="enterByQrCode"
        >
          <v-icon dark left>mdi-qrcode</v-icon>
          通过二维码加入
        </v-btn>
        <v-btn
          v-if="isTeacher === '1'"
          x-large
          class="mb-10"
          elevation="2"
          color="success"
          @click="addClass"
        >
          创建班课
        </v-btn>
        <v-btn x-large elevation="2" color="grey" @click="overlay = false">
          取消
        </v-btn>
      </div>
    </v-overlay>
  </v-container>
</template>

<script>
import { initClassList } from "@/api/class";

import { enterClass } from "@/api/class";

export default {
  name: "lookup_classes",
  data() {
    return {
      window: window,
      absolute: true,
      overlay: false,
      pageNo: 1,
      pageSize: 20,
      classDefaultAvatar: require("../assets/images/class_default_avatar.png"),
      isTeacher: localStorage.getItem("isTeacher"),
      classes: [
        {
          ccName: "工程实践",
          peName: "A老师",
          term: "2021-2022-1",
          cId: 1,
          cNumber: 10001,
        },
      ],
    };
  },
  methods: {
    //创建班课
    showList() {
      this.overlay = !this.overlay;
    },
    addClass() {
      // if (this.GLOBAL.user == "student") {
      this.$router.push("addClass");
      // } else {
      //   this.$router.push("/addClass");
      // }
    },
    enterByClassCode() {
      this.$router.push({
        path: "/enterClass",
        query: {
          type: "classCode",
        },
      });
      this.$route.meta.stuTitle = "加入班课";
    },
    enterByQrCode() {
      this.window.cordova.plugins.barcodeScanner.scan(
        async function (result) {
          let response = await enterClass(result.text);
          alert(response.data.code);
          alert(response.data.msg);
          switch (response.data.code) {
            case "200":
              this.text = "加入成功";
              this.snackbar = true;
              this.enterSuccess = true;
              alert("ing");
              this.$router.push({
                path: "/enterClass",
                query: { type: "", classNumber: result.text },
              });

              break;
            default:
              this.text = response.data.msg;
              this.snackbar = true;

              alert(response.data.msg);
          }
          /*
          alert(
            "We got a barcode\n" +
              "Result: " +
              result.text +
              "\n" +
              "Format: " +
              result.format +
              "\n" +
              "Cancelled: " +
              result.cancelled
          );
          */
        },
        function (error) {
          alert("扫码失败: " + error);
        },
        {
          preferFrontCamera: false, // iOS and Android
          showFlipCameraButton: true, // iOS and Android
          showTorchButton: true, // iOS and Android
          torchOn: false, // Android, launch with the torch switched on (if available)
          saveHistory: true, // Android, save scan history (default false)
          prompt: "请将二维码放置于扫描区域内", // Android
          resultDisplayDuration: 500, // Android, display scanned text for X ms. 0 suppresses it entirely, default 1500
          formats: "QR_CODE", // default: all but PDF_417 and RSS_EXPANDED
          orientation: "portrait", // Android only (portrait|landscape), default unset so it rotates with the device
          disableAnimations: true, // iOS
          disableSuccessBeep: true, // iOS and Android
        }
      );
    },
    goClassDetail(index) {
      this.$router.push({
        path: "/classDetail/",
        query: {
          id: this.classes[index].cNumber,
        },
      });
    },

    goClassSetup(index) {
      this.$router.push({
        path: "/classSetup",
        query: {
          cNumber: this.classes[index].cNumber,
        },
      });
    },
    goClassSignUp(index) {
      this.$router.push({
        path: "/sign",
        query: {
          cId: this.classes[index].cId + "",
        },
      });
    },
  },
  async mounted() {
    let result = await initClassList(1, 3);
    this.classes = result.data.data;

    // initClassList(this.pageNo,this.pageSize,localStorage.getItem("teacherId"));
    // console.log(this.$route.query.className);
    /*
    if (this.$route.query.className != null) {
      this.classes.push({
        title: this.$route.query.className,
        imgUrl: "",
        teacher: this.$route.query.teacherName,
        time: this.$route.query.semester,
        classNumber: this.$route.query.classNumber,
      });
    }
*/
    // console.log(this.classes);
  },
};
</script>

<style scoped></style>
