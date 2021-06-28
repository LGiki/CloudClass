<template>
  <v-container style="height: 100%">
    <template v-if="items.length !== 0">
      <v-list class="overflow-x-hidden">
        <v-subheader>未签到学生 ({{ items.length }})</v-subheader>
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
                  <v-icon>mdi-account</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title v-text="item.peName"></v-list-item-title>
                </v-list-item-content>
                <v-btn
                  class="primary"
                  @click="sign($route.query.siId, item.peId)"
                >
                  补签
                </v-btn>
              </template>
            </v-list-item>
          </template>
        </v-list-item-group>
      </v-list>
    </template>
    <template v-else>
      <div
        style="
          display: flex;
          justify-content: center;
          align-items: center;
          height: 100%;
        "
      >
        <h1>所有学生均已签到</h1>
      </div>
    </template>
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
import { getUnSignedStudents, reSignUp } from "./api/sign";

export default {
  name: "SignDetail",
  data() {
    return {
      items: [],
      snackbar: false,
      snackbarText: ""
    };
  },
  methods: {
    sign(seId, peId) {
      reSignUp(seId, peId).then((res) => {
        if (res.data.code === "200") {
          this.getUnSignedList();
        } else {
          this.snackbarText = "补签失败，请稍后再试";
          this.snackbar = true;
        }
      });
    },
    getUnSignedList() {
      getUnSignedStudents(this.$route.query.siId).then((res) => {
        if (res.data.code === "200") {
          this.items = [];
          let divider = { divider: true };
          for (const item of res.data.data) {
            this.items.push({
              peName: item.peName,
              username: item.username,
              peId: item["pe_id"]
            });
            this.items.push(divider);
          }
          if (this.items.length > 0) {
            this.items.splice(this.items.length - 1, 1);
          }
        } else {
          this.snackbarText = "获取未签到学生列表失败，请稍后再试";
          this.snackbar = true;
        }
      });
    }
  },
  mounted() {
    this.getUnSignedList();
  }
};
</script>
