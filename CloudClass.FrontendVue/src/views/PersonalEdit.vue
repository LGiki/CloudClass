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
          this.$router.push({
            path: "/enterClass",
            query: { type: "school" },
          });
          break;
        case 2:
          this.$router.push({
            path: "/enterClass",
            query: { type: "grade" },
          });
          break;
        case 4:
          this.$router.push({
            path: "/enterClass",
            query: { type: "major" },
          });
          break;
      }
    },
  },
};
</script>

<style scoped></style>
