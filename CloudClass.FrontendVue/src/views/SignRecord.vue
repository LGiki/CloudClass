<template>
  <v-container>
    <v-card class="mb-5">
      <div class="d-flex flex-no-wrap justify-space-between">
        <div>
          <v-card-title class="headline">林声睿</v-card-title>

          <v-card-subtitle class="mt-0">
            学号: {{ 200327063 }}
            <br />
            班级: {{ 123 }}
            <br />
            专业: {{ 123 }}
            <br />
            经验值： 100
          </v-card-subtitle>
        </div>
        <div style="display: flex; align-items: center">
          <v-avatar class="ma-3" size="125" tile>
            <img src="../assets/images/pikaqiu1.jpg" />
          </v-avatar>
        </div>
      </div>
    </v-card>

    <v-list>
      <template v-for="(item, index) in items">
        <v-subheader
          class="font-weight-bold"
          v-if="item.header"
          :key="item.header"
          style="font-size: large"
          >{{ item.header }}
        </v-subheader>

        <v-divider v-else-if="item.divider" :key="index"></v-divider>

        <v-list-item v-else :key="item.title">
          <v-list-item-content> {{ item.title }}</v-list-item-content>
          <v-list-item-content>{{ item.time }}</v-list-item-content>
          <v-list-item-content> {{ item.change }}</v-list-item-content>
          <v-list-item-content> {{ item.exp }}</v-list-item-content>
        </v-list-item>
      </template>
    </v-list>
  </v-container>
</template>

<script>
export default {
  name: "SignRecord",
  data() {
    return {
      items: [
        { header: "签到记录" },
        {
          title: "日期",
          time: "时间",
          change: "经验变化",
          exp: "经验总数",
        },
        { divider: true, inset: true },
        {
          title: "5-1",
          time: "10:43",
          change: "+2",
          exp: "25",
        },
        { divider: true, inset: true },
        {
          title: "5-2",
          time: "18:64",
          change: "-2",
          exp: "23",
        },
        { divider: true, inset: true },
      ],
    };
  },
};
</script>

<style scoped></style>
