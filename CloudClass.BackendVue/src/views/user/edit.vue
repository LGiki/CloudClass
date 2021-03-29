<template>
  <div class="app-container">
    <el-form ref="userDetail" :model="userDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="用户ID">
        <el-input v-model="userDetail.id" disabled />
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="userDetail.username" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="userDetail.nickname" placeholder="请输入昵称" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="userDetail.phone" type="number" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="$router.back(-1)">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userDetail: {},
      passwordType: 'password',
      password: '',
      checkRules: {
        username: [
          {
            required: true,
            message: '用户名不能为空',
            trigger: 'blur'
          }
        ],
        nickname: [
          {
            required: true,
            message: '昵称不能为空',
            trigger: 'blur'
          }
        ],
        phone: [
          {
            required: true,
            message: '手机号不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    onSubmit() {
      // TODO: Complete on Submit
    }
  }
}
</script>
