<template>
  <v-container>
    <div class="font-weight-bold mb-16 mt-2 text-h6 ml-2">班级：工程实践</div>
    <div class="justify-space-around d-flex">
      <v-btn
        large
        fab
        color="red"
        @click="onSelect(1)"
        v-if="!(startSign > 0 && selectType === 'time')"
      >
        <v-icon color="white" medium>mdi-check</v-icon>
      </v-btn>
      <v-btn
        large
        fab
        color="orange"
        @click="onSelect(2)"
        v-if="!(startSign > 0 && selectType === 'once')"
      >
        <v-icon color="white" medium>mdi-clock-check-outline</v-icon>
      </v-btn>
    </div>

    <div class="justify-space-around d-flex mt-3">
      <div style="font-size: 14px" v-if="!(startSign && selectType === 'time')">
        一键签到
      </div>
      <div style="font-size: 14px" v-if="!(startSign && selectType === 'once')">
        限时签到
      </div>
    </div>

    <div class="justify-space-around d-flex mt-3" v-if="!startSign">
      <v-icon :color="color1" large>mdi-pan-up</v-icon>

      <v-icon :color="color2" large>mdi-pan-up</v-icon>
    </div>

    <div
      class="font-weight-bold mt-5"
      style="color: red; text-align: center"
      v-if="startSign > 0"
    >
      已发起签到：{{ minute }}分{{ second }}秒
    </div>

    <div class="d-flex justify-center align-self-center pt-16 pb-16">
      <v-btn
        x-large
        class="primary mb-16 mt-16"
        @click="sign(1)"
        v-if="stuIsSuccess === false && isTeacher === '0'"
      >
        签到
      </v-btn>
      <v-btn
        x-large
        class="primary mb-16 mt-16"
        @click="startSignUp"
        v-if="isSuccess === false && isTeacher === '1'"
      >
        发起签到
      </v-btn>
      <v-btn
        v-if="stuIsSuccess === true && isTeacher === '0'"
        x-large
        class="mb-16 mt-16 white--text"
        elevation="4"
        color="green lighten"
        shaped
      >
        <v-icon left>mdi-check</v-icon>
        签到成功
      </v-btn>

      <v-btn
        v-if="isSuccess === true && isTeacher === '1'"
        x-large
        class="mb-16 mt-16 white--text"
        elevation="4"
        color="red darken"
        shaped
        @click="endSignUp"
      >
        <v-icon left>mdi-alert-circle-outline</v-icon>
        结束签到
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
import {
  endSignUp,
  getActiveSignUp,
  signUpBySid,
  startSignUp,
} from "@/api/sign";

export default {
  data: () => ({
    snackbar: false,
    text: `签到成功！`,
    msg: "位置信息获取失败",
    isSuccess: false, //控制签到和不在签到情况下的UI,是否签到成功
    stuIsSuccess: false,
    isTeacher: localStorage.getItem("isTeacher"),
    selectType: "once",
    color1: "purple",
    color2: "white",
    minute: 0,
    second: 0,
    startSign: false, //控制倒计时
    longtitude: "",
    latitude: "",
    siId: "",
  }),
  methods: {
    onSelect(color) {
      if (color === 1) {
        this.selectType = "once";
        this.color1 = "purple";
        this.color2 = "white";
      } else if (color === 2) {
        this.selectType = "time";
        this.color2 = "purple";
        this.color1 = "white";
      }
    },
    getPositon(val) {
      var me = this;
      //获取精度，纬度
      var onSuccess = async function (position) {
        me.msg =
          "纬度:\t" +
          position.coords.latitude +
          "\n" +
          "精度:\t" +
          position.coords.longitude +
          "\n";

        me.longtitude = position.coords.longitude + "";
        me.latitude = position.coords.latitude + "";
        console.log(me.longtitude);
        console.log(me.latitude);

        /*
          "Altitude:\t" +
          position.coords.altitude +
          "\n" +
          "Accuracy:\t" +
          position.coords.accuracy +
          "\n" +
          "Altitude Accuracy:\t" +
          position.coords.altitudeAccuracy +
          "\n" +
          "Heading:\t" +
          position.coords.heading +
          "\n" +
          "Speed:\t" +
          position.coords.speed +
          "\n" +
          "Timestamp:\t" +
          position.timestamp +
          "\n";
          */
      };
      var error = function (error) {
        console.log(error.message);
        me.msg =
          "code: " + error.code + "\n" + "message: " + error.message + "\n";
        alert(error.message);
      };

      navigator.geolocation.getCurrentPosition(onSuccess, error, {
        maximumAge: 30000,
        timeout: 3000,
        enableHighAccuracy: val,
      });
    },
    async sign(val) {
      await this.getPositon(val);
      var me = this;
      setTimeout(async function () {
        let activeSignUpResult = await getActiveSignUp(me.$route.query.cId);
        if (
          activeSignUpResult.data.data.siId === null ||
          activeSignUpResult.data.code !== "200"
        ) {
          me.text = "目前没有进行中的签到";
          me.snackbar = true;
          return;
        }

        if (me.latitude === "" || me.longtitude === "") {
          me.text = "位置信息获取失败，请重试";
          me.snackbar = true;
          return;
        }

        let result = await signUpBySid(
          activeSignUpResult.data.data.siId + "",
          me.latitude,
          me.longtitude
        );

        if (result.data == null) {
          me.text = "参数错误";
          me.snackbar = true;
          return;
        }

        switch (result.data.code) {
          case "200":
            me.stuIsSuccess = true;
            break;
          default:
            me.text = result.data.msg;
            me.snackbar = true;
        }
      }, 1000);

      // setTimeout(function () {
      //   alert(me.msg);
      // }, 1000);
    },
    async startSignUp() {
      let me = this;
      await this.getPositon(1);

      setTimeout(async function () {
        if (me.latitude === "" || me.longtitude === "") {
          me.text = "位置信息获取失败，请重试";
          me.snackbar = true;
          return;
        }

        //一键签到
        if (me.selectType === "once") {
          let result = await startSignUp(
            "1",
            me.$route.query.cId,
            me.longtitude,
            me.latitude,
            ""
          );

          this.siId = result.data.siId;

          if (result.data == null) {
            me.text = "参数错误";
            me.snackbar = true;
            return;
          }

          switch (result.data.code) {
            case "200":
              me.text = "发起签到成功";
              me.snackbar = true;

              me.startSign = true;
              me.isSuccess = true;
              me.startCount();
              break;
            default:
              me.text = result.data.msg;
              me.snackbar = true;
          }
        } //限时签到
        else if (me.selectType === "time") {
          let result = await startSignUp(
            "2",
            me.$route.query.cId,
            me.longtitude,
            me.latitude,
            "60"
          );

          this.siId = result.data.siId;

          if (result.data == null) {
            me.text = "参数错误";
            me.snackbar = true;
            return;
          }

          switch (result.data.code) {
            case "200":
              me.text = "发起签到成功";
              me.snackbar = true;

              me.startSign = true;
              me.isSuccess = true;
              me.startCount();
              break;
            default:
              me.text = result.data.msg;
              me.snackbar = true;
          }
        }
      }, 1000);
    },
    //倒计时
    startCount() {
      if (this.startSign === false) return;

      console.log(this.second);
      this.second++;
      if (this.second === 60) {
        this.second = 0;
        this.minute++;
      }
      setTimeout(this.startCount, 1000);
    },
    //结束签到
    async endSignUp() {
      let result = await endSignUp(this.siId + "");

      if (result.data === null) {
        this.text = "参数错误";
        this.snackbar = true;
      }
      switch (result.data.code) {
        case "200":
          this.text = "签到已结束";
          this.snackbar = true;

          this.startSign = false;
          this.isSuccess = false;
          this.minute = 0;
          this.second = 0;
          break;
        default:
          this.text = result.data.msg;
          this.snackbar = true;
      }
    },
  },
  async mounted() {
    let activeSignUpResult = await getActiveSignUp(this.$route.query.cId);

    if (activeSignUpResult.data.data.siId != null) {
      this.siId = activeSignUpResult.data.data.siId;

      this.isSuccess = true;
    }
  },
};
</script>
