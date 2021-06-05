<template>
  <div class="app-container">
    <el-form
      ref="systemConfig"
      :model="systemConfig"
      :rules="systemConfigRules"
      class="system-setting-form"
      auto-complete="on"
      label-position="left"
    >
      <el-form-item label="每次签到获得的经验值" prop="expPerSign">
        <el-input v-model="systemConfig.expPerSign" type="number" />
      </el-form-item>
      <el-form-item style="text-align: center">
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
      systemConfig: {
        expPerSign: 0
      },
      systemConfigRules: {
        expPerSign: [
          {
            required: true, trigger: 'blur', validator: (rule, value, callback) => {
              console.log(value)
              if (!value || value.len === 0) {
                callback(new Error('不能为空'))
              } else if (parseInt(value) <= 0) {
                callback(new Error('每次签到的经验值必须大于0'))
              } else {
                callback()
              }
            }
          }
        ]
      }
    }
  },
  methods: {
    onSubmit() {
    }
  }
}
</script>
