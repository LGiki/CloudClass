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

      <v-list>
        <template v-for="(item, index) in items">
          <v-subheader
            class="font-weight-bold"
            v-if="item.header"
            :key="item.header"
          >
          </v-subheader>

          <v-divider v-else-if="item.divider" :key="index"></v-divider>

          <v-list-item v-else :key="item.title" @click="goEdit(index)">
            <v-list-item-content>
              <v-list-item-title v-html="item.title"> </v-list-item-title>
            </v-list-item-content>
            <v-list-item-icon>
              <v-icon left>mdi-chevron-right</v-icon>
            </v-list-item-icon>
          </v-list-item>
        </template>
      </v-list>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "Personal",
  data() {
    return {
      items: [
        { header: " " },
        {
          title: "个人信息修改",
          subtitle: ``,
        },
        { divider: true, inset: true },
        {
          title: "签到记录",
          subtitle: ``,
        },
        { divider: true, inset: true },
        {
          title: "修改密码",
          subtitle: "",
        },
        { divider: true, inset: true },
      ],
    };
  },
  methods: {
    goEdit(index) {
      console.log(index);
      if (index === 3) {
        this.$router.push("/signRecord");
      } else if (index === 1) {
        this.$router.push("/personalEdit");
      } else if (index === 5) {
        this.$router.push({
          path: "enterClass",
          query: { type: "changePass" },
        });
      }
    },
  },
};
</script>

<style scoped></style>
