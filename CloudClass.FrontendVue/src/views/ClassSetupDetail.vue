<template>
  <v-container>
    <v-list>
      <template v-for="(item, index) in items">
        <v-subheader
          class="font-weight-bold"
          v-if="item.header"
          :key="item.header"
        >
          {{ item.header }}
        </v-subheader>

        <v-divider v-else-if="item.divider" :key="index"></v-divider>

        <v-list-item v-else :key="item.title" @click="goEdit(index)">
          <v-list-item-content>
            <v-list-item-title v-html="item.title"> </v-list-item-title>
          </v-list-item-content>

          <template>
            <div class="text-center">
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn v-bind="attrs" v-on="on">
                    {{ initContent[index - 1] }}
                  </v-btn>
                </template>
                <v-list v-if="index === 1">
                  <v-list-item
                    v-for="(item, index1) in menuContent"
                    :key="index1"
                    @click="setClassCondition(index1)"
                  >
                    <v-list-item-title>{{ item.select1 }}</v-list-item-title>
                  </v-list-item>
                </v-list>

                <v-list v-if="index === 3">
                  <v-list-item
                    v-for="(item, index2) in menuContent"
                    :key="index2"
                    @click="setPermitAdd(index2)"
                  >
                    <v-list-item-title>{{ item.select2 }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </template>
        </v-list-item>
      </template>
    </v-list>
    <v-btn block color="primary" @click="updateSetup">保存</v-btn>

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
import { updateClassFinished, updateClassPermitAdd } from "@/api/class";

export default {
  name: "ClassSetupDetail",
  data() {
    return {
      text: "",
      snackbar: false,
      condition: [],
      permitMember: [],
      classCondtion: "1",
      permitAdding: "1",
      items: [
        { header: "基本信息设置" },
        {
          title: "班课状态",
          subtitle: ``,
        },
        { divider: true, inset: true },
        {
          title: "是否允许成员加入",
          subtitle: ``,
        },
        { divider: true, inset: true },
      ],
      initContent: ["正常", "", "允许加入"],
      menuContent: [
        {
          select1: "正常",
          select2: "允许加入",
        },
        {
          select1: "结束",
          select2: "不允许加入",
        },
      ],
    };
  },
  methods: {
    setClassCondition(index) {
      if (index === 0) {
        this.classCondtion = "0";
        this.initContent[0] = "正常";
      } else if (index === 1) {
        this.classCondtion = "1";
        this.initContent[0] = "结束";
      }
    },
    setPermitAdd(index) {
      if (index === 0) {
        this.permitAdding = "1";
        this.initContent[2] = "允许加入";
      } else if (index === 1) {
        this.permitAdding = "0";
        this.initContent[2] = "不允许加入";
      }
    },
    updateSetup() {
      updateClassFinished(
        // this.$route.query.cid,
        "20",
        this.classCondtion
      );
      updateClassPermitAdd(
        //   this.$route.query.cid,
        "20",
        this.permitAdding
      );
      this.snackbar = true;
      this.text = "保存成功";
      /*
      if (result1.data.code === "200" && result2.data.code === "200") {
        this.snackbar = true;
        this.text = "保存成功";
      } else {
        this.snackbar = true;
        this.text = "保存失败";
      }
      */
    },
  },
};
</script>

<style scoped></style>
