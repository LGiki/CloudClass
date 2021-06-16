<template>
  <v-container>
    <v-card class="mb-5">
      <div class="d-flex flex-no-wrap justify-space-between">
        <div>
          <v-card-title class="headline">{{
            classInfo.className
          }}</v-card-title>

          <v-card-subtitle class="mt-0">
            班课号: {{ classInfo.classNumber }}
            <br />
            学期: {{ classInfo.semester }}
            <br />
            学校: {{ classInfo.school }}
            <br />
            院系: {{ classInfo.depart }}
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
        { divider: true, inset: true },
        {
          title: "班课状态设置",
          subtitle: ``,
        },
      ],
      classInfo: {
        className: "工程实践",
        semester: "2021-2022-1",
        classNumber: 27715,
        school: "福州大学",
        depart: "数学科学与计算机学院",
      },
    };
  },
  methods: {
    goEdit(index) {
      console.log(index);
      if (index === 2) {
        this.$router.push({
          path: "/classSetupDetail",
          query: {},
        });
      }
    },
  },
};
</script>

<style scoped></style>
