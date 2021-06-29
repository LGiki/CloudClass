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
          <div
            style="
              height: 100%;
              display: flex;
              justify-content: flex-end;
              align-items: center;
              margin-right: 16px;
            "
          >
            <span>经验值排序：</span>
            <v-btn icon @click="handleOrderByExp">
              <v-icon>{{
                expOrder === 1 ? "mdi-sort-ascending" : "mdi-sort-descending"
              }}</v-icon>
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

        <v-list-item
          v-else
          :key="item.title"
          class="font-weight-bold"
          @click="editExp(index)"
        >
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
          </v-list-item-content>

          >
        </v-list-item>
      </template>
    </v-list>
    <v-dialog v-model="updateExpDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="text-h5">修改经验值</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-form ref="updateExpForm">
              <v-text-field
                v-model="exp"
                :rules="[rules.required]"
                hint="请输入新经验值"
                label="经验值"
                prepend-icon="mdi-star"
              >
              </v-text-field>
            </v-form>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="updateExpDialog = false">
            取消
          </v-btn>
          <v-btn color="blue darken-1" text @click="handleUpdateExp">
            确定
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
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
import { editExp } from "@/api/person";

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
        cId: "",
      },
      items: [],
      expOrder: 1,
      updateExpDialog: false,
      peId: 0,
      cId: 0,
      exp: 0,
      rules: {
        required: (value) => !!value || "必填",
      },
    };
  },
  methods: {
    handleUpdateExp() {
      editExp(this.peId, this.cId, this.exp).then((res) => {
        if (res.data.code === "200") {
          this.setClassMembersData();
          this.text = "经验值修改成功";
          this.snackbar = true;
          this.updateExpDialog = false;
        } else {
          this.text = "经验值修改失败，请稍后再试";
          this.snackbar = true;
        }
      });
    },
    editExp(index) {
      this.updateExpDialog = true;
      this.peId = this.items[index].peId;
      this.cId = this.classes.cId;
      this.exp = this.items[index].grade;
    },
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
      this.classes.cId = result.data.data.cId + "";
    },
    async setClassMembersData() {
      var result = await queryClassMembers(
        this.$route.query.cId,
        this.expOrder
      );
      if (result.data != null) {
        this.items = [];
        let divider = { divider: true };
        for (let i = 1; i <= result.data.data.length; i++) {
          let item = {
            title: result.data.data[i - 1].peName,
            grade: result.data.data[i - 1].exp,
            avatar: require("../assets/images/pikaqiu1.jpg"),
            peId: result.data.data[i - 1].peId,
          };
          this.items.push(item);
          this.items.push(divider);
        }
        if (this.items.length > 0) {
          this.items.splice(this.items.length - 1, 1);
        }
      }
    },
  },
  async mounted() {
    this.createQrCode();
    this.judgeShowSuccess();
    await this.setClassData();
    await this.setClassMembersData();
  },
};
</script>

<style scoped>
/*.class-info {*/
/*  position: sticky;*/
/*  top: 68px;*/
/*  z-index: 2;*/
/*}*/
</style>
