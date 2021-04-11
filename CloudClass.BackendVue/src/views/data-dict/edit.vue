<template>
  <div class="app-container">
    <el-form ref="dictDetail" :model="dictDetail" :rules="checkRules" label-width="120px">
      <el-form-item label="数据字典ID">
        <el-input v-model="dictDetail.id" disabled />
      </el-form-item>
      <el-form-item label="中文标识" prop="chineseLabel">
        <el-input v-model="dictDetail.chineseLabel" placeholder="请输入中文标识" />
      </el-form-item>
      <el-form-item label="英文标识" prop="englishLabel">
        <el-input v-model="dictDetail.englishLabel" placeholder="请输入英文标识" />
      </el-form-item>
      <el-form-item label="备注" prop="phone">
        <el-input v-model="dictDetail.comment" type="number" placeholder="请输入备注" />
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
  mounted() {
    console.log(this.$route.params)
  },
  data() {
    return {
      dictDetail: {
        chineseLabel: '',
        englishLabel: '',
        comment: ''
      },
      passwordType: 'password',
      password: '',
      checkRules: {
        chineseLabel: [
          {
            required: true,
            message: '中文标识不能为空',
            trigger: 'blur'
          }
        ],
        englishLabel: [
          {
            required: true,
            message: '英文标识不能为空',
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
