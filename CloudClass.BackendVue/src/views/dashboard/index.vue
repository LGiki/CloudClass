<template>
  <div class="dashboard-container">
    <div class="dashboard-text">欢迎使用到云APP 后台管理系统</div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {

  &-container {
    margin: 30px;
    display: flex;
    justify-content: center;
  }
  &-text {
    font-size: 2.5rem;
    line-height: 50px;
    font-weight: bold;
  }
}
</style>
