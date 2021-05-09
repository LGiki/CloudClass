<template>
  <v-container>
    <div class="text-h4 text-center mb-16 mt-16">班级：工程实践</div>
    <div class="d-flex justify-center align-self-center pt-16 pb-16">
      <v-btn x-large class="primary mb-16 mt-16" @click="sign(1)">
        <v-icon left>mdi-clipboard-account</v-icon>
        签到
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
  }),
  methods: {
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

      setTimeout(function () {
        alert(me.msg);
      }, 1000);

      //this.snackbar = true;
    },
  },
};
</script>
