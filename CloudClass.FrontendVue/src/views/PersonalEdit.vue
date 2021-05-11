<template>
  <v-container>
    <v-card class="mb-5">
      <div class="d-flex flex-no-wrap justify-space-between">
        <div>
          <v-card-title class="headline">林声睿</v-card-title>

          <v-card-subtitle class="mt-0">
            学号: {{ 200327063 }}
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
  name: "PersonalEdit",
  data() {
    return {
      isTeacher: localStorage.getItem("isTeacher"),
      items: [
        {
          title: "学校",
        },
        { divider: true, inset: true },
        {
          title: "年级",
        },
        { divider: true, inset: true },
        {
          title: "专业班级",
        },
        { divider: true, inset: true },
      ],
    };
  },
  methods: {
    goEdit(index) {
      console.log(index);
      switch (index) {
        case 0:
          if (this.isTeacher === "0") {
            this.$router.push({
              path: "/enterClass",
              query: { type: "school" },
            });
            this.$route.meta.stuTitle = "修改学校";
          } else if (this.isTeacher === "1") {
            this.$router.push({
              path: "/enterClass",
              query: { type: "exp" },
            });
            this.$route.meta.title = "修改经验值";
          }
          break;
        case 2:
          this.$router.push({
            path: "/enterClass",
            query: { type: "grade" },
          });
          this.$route.meta.stuTitle = "修改年级";
          break;
        case 4:
          this.$router.push({
            path: "/enterClass",
            query: { type: "major" },
          });
          this.$route.meta.stuTitle = "修改专业";
          break;
      }
    },
  },
  mounted() {
    if (this.isTeacher === "1") {
      this.items.splice(0, this.items.length);
      this.items.push(
        {
          title: "经验值",
        },
        { divider: true, inset: true }
      );
    }
  },
};
</script>

<style scoped></style>
