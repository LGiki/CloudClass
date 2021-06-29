<template>
  <div>
    <h1>{{ code }}</h1>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "GithubRedirect",
  data: function () {
    return {
      code: "",
    };
  },
  mounted() {
    this.code = this.$route.query.code;

    console.log(this.code);
    axios
      .get("http://8.140.0.237:8080/api/oauth/github", {
        params: { code: this.code },
      })
      .then((response) => {
        if (response.data.code === "200") {
          this.$store.commit("token/SET_TOKEN", response.data.token);
          this.$router.push("/class");
        } else {
          console.log(response);
          alert("Github登录失败");
        }
      })
      .catch((err) => {
        console.log(err);
      });
    // TODO: I get github code here, then I need to request backend API to request token
  },
};
</script>

<style scoped></style>
