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
        v-if="!(startSign > 0 && selectType === 'one')"
      >
        <v-icon color="white" medium>mdi-clock-check-outline</v-icon>
      </v-btn>
    </div>

    <div class="justify-space-around d-flex mt-3">
      <div style="font-size: 14px" v-if="!(startSign && selectType === 'time')">
        一键签到
      </div>
      <div style="font-size: 14px" v-if="!(startSign && selectType === 'one')">
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
        v-if="isSuccess === false && isTeacher === '0'"
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
        v-if="isSuccess === true && isTeacher === '0'"
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
export default {
  data: () => ({
    snackbar: false,
    text: `签到成功！`,
    msg: "位置信息获取失败",
    isSuccess: false,
    isTeacher: localStorage.getItem("isTeacher"),
    selectType: "one",
    color1: "purple",
    color2: "white",
    minute: 0,
    second: 0,
    startSign: false,
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
    sign(val) {
      var me = this;
      //获取精度，纬度
      var onSuccess = function (position) {
        me.msg =
          "纬度:\t" +
          position.coords.latitude +
          "\n" +
          "精度:\t" +
          position.coords.longitude +
          "\n";
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
        me.msg =
          "code: " + error.code + "\n" + "message: " + error.message + "\n";
      };

      navigator.geolocation.getCurrentPosition(onSuccess, error, {
        maximumAge: 30000,
        timeout: 5000,
        enableHighAccuracy: val,
      });

      this.isSuccess = true;
      console.log(this.isSuccess);

      setTimeout(function () {
        alert(me.msg);
      }, 1000);
    },
    startSignUp() {
      this.startSign = true;
      this.isSuccess = true;
      this.startCount();
    },
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
    endSignUp() {
      this.startSign = false;
      this.isSuccess = false;
      this.minute = 0;
      this.second = 0;
    },
  },
};
</script>
