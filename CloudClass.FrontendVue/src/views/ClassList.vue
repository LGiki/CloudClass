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
            v-text="classInfo.title"
          ></v-card-title>
          <v-card-subtitle
            v-text="classInfo.teacher + classInfo.time"
          ></v-card-subtitle>
          <v-card-actions>
            <div style="display: flex; flex-direction: row; flex-wrap: wrap">
              <v-btn
                class="ml-1 mt-1 mb-1 elevation-0"
                small
                @click="goClassDetail(classInfo.classNumber)"
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
              <v-btn class="ml-1 mt-1 mb-1 elevation-0" small @click="sign">
                <v-icon left dark color="green darken-2"
                  >mdi-check-circle
                </v-icon>
                签到
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
        medium
        bottom
        fixed
        class="mr-4"
        @click="addClass"
      >
        <v-icon dark>mdi-plus</v-icon>
      </v-btn>
    </div>
    <v-overlay :absolute="absolute" :value="overlay" opacity="0.7">
      <div class="d-flex flex-no-wrap flex-column">
        <v-btn
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
          x-large
          class="mb-10"
          elevation="2"
          color="success"
          @click="enterByQrCode"
        >
          <v-icon dark left>mdi-qrcode</v-icon>
          通过二维码加入
        </v-btn>
        <v-btn x-large elevation="2" color="grey" @click="overlay = false">
          取消
        </v-btn>
      </div>
    </v-overlay>
  </v-container>
</template>

<script>
export default {
  name: "lookup_classes",
  data() {
    return {
      window: window,
      absolute: true,
      overlay: false,
      classDefaultAvatar: require("../assets/images/class_default_avatar.png"),
      classes: [
        {
          title: "工程实践",
          imgUrl: "",
          teacher: "池芝标",
          time: "2021-1",
          classNumber: 10004,
        },
        {
          title: "工程实践",
          imgUrl: "",
          teacher: "池芝标",
          time: "2021-1",
          classNumber: 10003,
        },
        {
          title: "工程实践",
          imgUrl: "",
          teacher: "池芝标",
          time: "2021-1",
          classNumber: 10002,
        },
      ],
    };
  },
  methods: {
    sign() {
      this.$router.push("/sign");
    },
    //创建班课
    addClass() {
      // if (this.GLOBAL.user == "student") {
      this.overlay = !this.overlay;
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
    },
    enterByQrCode() {
      this.window.cordova.plugins.barcodeScanner.scan(
        function (result) {
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
        },
        function (error) {
          alert("Scanning failed: " + error);
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
    goClassDetail(classId) {
      this.$router.push({
        path: "/classDetail/",
        query: {
          id: classId,
        },
      });
    },
  },
  mounted: function () {
    // console.log(this.$route.query.className);
    if (this.$route.query.className != null) {
      this.classes.push({
        title: this.$route.query.className,
        imgUrl: "",
        teacher: this.$route.query.teacherName,
        time: this.$route.query.semester,
        classNumber: this.$route.query.classNumber,
      });
    }

    // console.log(this.classes);
  },
};
</script>

<style scoped></style>
