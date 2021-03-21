<template>
  <v-container>
    <v-card
      class="mb-5"
      v-for="(classInfo, index) in classes"
      v-bind:key="index"
    >
      <div class="d-flex flex-no-wrap justify-space-between">
        <div>
          <v-card-title
            class="headline"
            v-text="classInfo.title"
          ></v-card-title>
          <v-card-subtitle
            v-text="classInfo.teacher + classInfo.time"
          ></v-card-subtitle>
          <v-card-actions>
            <v-btn class="ma-2 elevation-0" small>
              <v-icon left dark color="red darken-2">mdi-account-circle</v-icon>
              成员
            </v-btn>
            <v-btn class="ma-2 elevation-0" small>
              <v-icon left dark color="orange darken-2"
                >mdi-clipboard-text-outline</v-icon
              >
              统计
            </v-btn>
            <v-btn class="ma-2 elevation-0" small @click="sign">
              <v-icon left dark color="green darken-2">mdi-check-circle</v-icon>
              签到
            </v-btn>
          </v-card-actions>
        </div>
        <v-avatar class="ma-3" size="125" tile>
          <v-img
            :src="
              classInfo.imgUrl && classInfo.imgUrl !== ''
                ? classInfo.imgUrl
                : classDefaultAvatar
            "
          ></v-img>
        </v-avatar>
      </div>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "lookup_classes",
  data() {
    return {
      classDefaultAvatar: require("../assets/images/class_default_avatar.png"),
      classes: [
        {
          title: "工程实践",
          imgUrl: "",
          teacher: "池芝标",
          time: "2021-1",
        },
        {
          title: "工程实践",
          imgUrl: "",
          teacher: "池芝标",
          time: "2021-1",
        },
        {
          title: "工程实践",
          imgUrl: "",
          teacher: "池芝标",
          time: "2021-1",
        },
      ],
    };
  },
  methods: {
    sign() {
      this.$router.push("/sign");
    },
  },
};
</script>

<style scoped></style>
