<template>
  <div class="app-container">
    <div class="operation-button">
      <el-input
        v-model="keywords"
        prefix-icon="el-icon-search"
        placeholder="请输入搜索关键字"
        style="width: 200px;margin-right: 10px"
        class="filter-item"
      />
      <el-button type="primary" @click="onSearch">
        <svg-icon icon-class="search" />&nbsp;搜索
      </el-button>
      <el-button v-if="inSearch" type="primary" @click="onCancelSearch">
        <i class="el-icon-close" />&nbsp;取消搜索
      </el-button>
      <el-button type="danger" @click="dialogVisible = true">
        <i class="el-icon-plus" />&nbsp;新建用户
      </el-button>
    </div>
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="用户ID" prop="id" align="center" width="80px">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="昵称" min-width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.nickname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属角色" width="100px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.role }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.sex }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" width="180px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="注册日期" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.registerData }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-center">
      <pagination :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    </div>
    <el-dialog
      title="新建用户"
      :visible.sync="dialogVisible"
      width="60%"
    >
      <el-form ref="userDetail" :model="userDetail" :rules="checkRules" label-width="120px">
        <!--        <el-form-item label="用户ID">-->
        <!--          <el-input v-model="userDetail.id" disabled />-->
        <!--        </el-form-item>-->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userDetail.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="userDetail.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="userDetail.phone" type="number" placeholder="请输入手机号" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onNewUser">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  components: { Pagination },
  data() {
    return {
      userDetail: {},
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
      },
      dialogVisible: false,
      keywords: '',
      inSearch: false,
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: false,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      }
    }
  },
  mounted() {
    this.list = []
    this.total = 10
    for (let i = 0; i < 10; i++) {
      this.list.push({
        id: i,
        username: 'Admin',
        nickname: '张三',
        role: '管理员',
        sex: '男',
        phone: '13800138000',
        email: 'example@example.org',
        registerData: '2020年1月1日'
      })
    }
  },
  methods: {
    onNewUser() {

    },
    onSearch() {
    },
    onCancelSearch() {
    },
    getSortClass(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
    handleUpdate(row) {
      this.$router.push('/user/edit/' + row.id)
    },
    handleDelete(row, index) {
      this.$alert('您确定要删除该用户吗？', '删除确认', {
        confirmButtonText: '确定',
        callback: action => {
          // this.$message({
          //   type: 'success',
          //   message: '删除成功'
          // })
        }
      })
    },
    getList() {
    }
  }
}
</script>

<style scoped>
.operation-button {
  float: right;
  padding-bottom: 10px;
}

.pagination-center {
  display: flex;
  width: 100%;
  justify-content: center;
}
</style>
