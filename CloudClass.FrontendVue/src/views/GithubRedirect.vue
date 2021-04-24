<template>
  <div></div>
</template>

<script>
export default {
  name: "GithubRedirect",
  computed: {
    code: this.$route.query.code || "",
  },
  mounted() {
    console.log(this.code);
    // TODO: I get github code here, then I need to request backend API to request token
  }
}
</script>

<style scoped>

</style>
