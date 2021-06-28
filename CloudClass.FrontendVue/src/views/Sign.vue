<template>
  <v-container>
    <div class="font-weight-bold mb-6 mt-2 text-h6 ml-2">班级：{{ classRealName }}</div>
    <div class="justify-space-around d-flex mt-8">
      <v-btn
        medium
        fab
        color="red"
        @click="onSelect(1)"
        v-if="!(startSign > 0 && selectType === 'time')"
      >
        <v-icon color="white" medium>mdi-check</v-icon>
      </v-btn>
      <v-btn
        medium
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

    <div class="justify-space-around d-flex mt-1" v-if="!startSign">
      <v-icon :color="color1" large>mdi-pan-up</v-icon>

      <v-icon :color="color2" large>mdi-pan-up</v-icon>
    </div>

    <template v-if="startSign < 1 && selectType === 'time' && isTeacher === '1'">
      <div class="text-center d-flex justify-center align-content-center">
        <div class="mt-2">限时：</div>
        <v-menu offset-y>
          <template v-slot:activator="{ on, attrs }">
            <v-btn v-bind="attrs" v-on="on">
              {{ selectTime }}
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(time, index) in times"
              :key="index"
              @click="changeSelectTime(index)"
            >
              <v-list-item-title>{{ time.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
    </template>

    <div
      class="font-weight-bold mt-5"
      style="color: red; text-align: center"
      v-if="startSign > 0 && this.selectType === 'once' && this.minute >= 0"
    >
      已发起签到：{{ minute }}分{{ second }}秒
    </div>
    <div
      class="font-weight-bold mt-5"
      style="color: red; text-align: center"
      v-if="startSign > 0 && this.selectType === 'time'"
    >
      签到倒计时：{{ minute2 }}分{{ second2 }}秒
    </div>
    <v-card
      v-if="isSuccess === true && isTeacher === '1'"
      class="mt-4 mr-4 ml-4"
    >
      <div class="d-flex justify-end">
        <v-card-text class="font-weight-bold">未签到学生</v-card-text>
        <v-card-text class="ml-10"
        >{{ studentNum - items.length }}/{{ studentNum }}
        </v-card-text
        >
      </div>
      <virtual-list
        style="height: 170px; overflow-y: auto"
        scrollable
        :data-key="'pe_id'"
        :data-sources="items"
        :data-component="itemComponent"
      />
      <!--
      <v-list max-height="230px" height="230px">
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
            <v-list-item-content>
              <v-list-item-title
                v-html="item.title"
                style="font-size: 13px"
              ></v-list-item-title>
              <v-list-item-subtitle
                v-html="item.subtitle"
              ></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
      -->
    </v-card>
    <div class="d-flex justify-center align-self-center pt-8 pb-16">
      <v-btn
        x-large
        class="primary mb-16 mt-16"
        @click="sign(1)"
        v-if="stuIsSuccess === false && isTeacher === '0'"
      >
        签到
      </v-btn>
      <div style="display: flex; flex-direction: column; width: 60%">
        <v-btn
          x-large
          block
          class="primary mt-16"
          @click="startSignUp"
          v-if="isSuccess === false && isTeacher === '1'"
        >
          发起签到
        </v-btn>

        <v-btn
          x-large
          block
          class="success mt-16"
          @click="() => $router.push({name: 'SignHistory', query: $route.query})"
          v-if="isSuccess === false && isTeacher === '1'"
        >
          签到记录
        </v-btn>
      </div>
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
        class="white--text"
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
  startSignUp
} from "@/api/sign";
import Item from "./item-component";
import VirtualList from "vue-virtual-scroll-list";
import { getSignUpRemainTime, getUnSignedStudents } from "./api/sign";
import { getClassData } from "../api/class";

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
    minute2: 0,
    second: 0,
    second2: 60,
    studentNum: 1,
    startSign: false, //控制倒计时
    longtitude: "",
    latitude: "",
    siId: "",
    selectTime: "30秒",
    classRealName: "工程实践",
    itemComponent: Item,
    items: [{ pe_id: "1", peName: "魏璐炜", username: "2003270xx" }],
    times: [{ title: "10秒" }, { title: "30秒" }, { title: "60秒" }]
    /*
    items: [
      {
        title: "魏璐炜",
        subtitle: `2003270xx`,
      },
      { divider: true, inset: true },
      {
        title: "林家琪",
        subtitle: `2003270xx`,
      },
      { divider: true, inset: true },
      {
        title: "林声睿",
        subtitle: "2003270xx",
      },
      { divider: true, inset: true },
      {
        title: "戴锦坤",
        subtitle: "2003270xx",
      },
    ],
    */
  }),
  components: { "virtual-list": VirtualList },
  methods: {
    //根据选择的签到类型改变UI
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
    //获取位置
    getPositon(val) {
      var me = this;
      //获取精度，纬度
      var onSuccess = async function(position) {
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
      var error = function(error) {
        console.log(error.message);
        me.msg =
          "code: " + error.code + "\n" + "message: " + error.message + "\n";
        alert(error.message);
      };

      navigator.geolocation.getCurrentPosition(onSuccess, error, {
        maximumAge: 30000,
        timeout: 3000,
        enableHighAccuracy: val
      });
    },
    //学生进行签到
    async sign(val) {
      await this.getPositon(val);
      var me = this;
      setTimeout(async function() {
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
    //发起签到
    async startSignUp() {
      let me = this;
      await this.getPositon(1);

      setTimeout(async function() {
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

          me.siId = result.data.siId;
          me.studentNum = result.data.StudentNum;

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
            me.selectTime.substring(0, 2)
          );

          me.siId = result.data.siId;
          me.studentNum = result.data.StudentNum;

          if (result.data === null) {
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
              me.second2 = parseInt(me.selectTime.substring(0, 2));
              me.startReverseCount();
              break;
            default:
              me.text = result.data.msg;
              me.snackbar = true;
          }
        }
      }, 1000);
    },
    //正计时
    async startCount() {
      if (this.startSign === false) return;

      let result = await getUnSignedStudents(this.siId);
      if (result.data != null) this.items = result.data.data;

      this.second++;
      if (this.second === 60) {
        this.second = 0;
        this.minute++;
      }
      setTimeout(this.startCount, 1000);
    },
    //倒计时
    startReverseCount() {
      if (this.startSign === false) return;

      this.second2--;
      if (this.second2 === 0) {
        this.startSign = false;
        this.isSuccess = false;
        this.minute2 = 0;

        this.text = "签到已结束";
        this.snackbar = true;
        return;
      }
      setTimeout(this.startReverseCount, 1000);
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
    changeSelectTime(index) {
      if (index === 0) {
        this.selectTime = "10秒";
      } else if (index === 1) {
        this.selectTime = "30秒";
      } else {
        this.selectTime = "60秒";
      }
    },
    async setClassData() {
      let result = await getClassData(this.$route.query.id);
      this.classRealName = result.data.data.ccName;
    }
  },
  async mounted() {

    this.setClassData();
    //若签到还未结束，改变UI
    let activeSignUpResult = await getActiveSignUp(this.$route.query.cId);
    this.studentNum = activeSignUpResult.data.StudentNum;
    let remainTime = await getSignUpRemainTime(
      activeSignUpResult.data.data.siId
    );

    if (activeSignUpResult.data.data.siId != null) {
      this.siId = activeSignUpResult.data.data.siId;

      //更改UI
      this.isSuccess = true;
      this.startSign = true;
      if (activeSignUpResult.data.data.type === 1) {
        this.selectType = "once";
      } else if (activeSignUpResult.data.data.type === 2) {
        this.selectType = "time";
      }
    }
    //更新剩余时间
    if (remainTime.data != null && remainTime.data.data >= 0) {
      this.second2 = remainTime.data.data;
      this.startReverseCount();
    } else {
      //让已签到时间的UI不再限时，适用于一键签到情况
      this.minute = -1;
    }

  }
};
</script>
