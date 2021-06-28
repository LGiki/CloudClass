<template>
  <div class="app-container">
    <div class="operation-button">
      <el-button type="danger" @click="initUserDetail(); isAdd = true; dialogVisible = true">
        <i class="el-icon-plus"/>&nbsp;新建用户
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
      <el-table-column label="用户ID" prop="id" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.peId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名" align="center">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" align="center">
        <template slot-scope="{row}">
          <span>{{ row.peName }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="性别" width="100" align="center">-->
<!--        <template slot-scope="{row}">-->
<!--          <span>{{ row.gender }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="专业" width="100" align="center">-->
<!--        <template slot-scope="{row}">-->
<!--          <span>{{ row.major }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="手机号" align="center">
        <template slot-scope="{row}">
          <span>{{ row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="身份" width="100" align="center">
        <template slot-scope="{row}">
          <el-tag v-if="row.isTeacher === -1" size="small" type="success">管理员</el-tag>
          <el-tag v-else-if="row.isTeacher === 0" size="small" type="info">学生</el-tag>
          <el-tag v-else-if="row.isTeacher === 1" size="small" type="warning">老师</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <!--          <el-button size="mini" type="danger" @click="handleDelete(row,$index)">-->
          <!--            删除-->
          <!--          </el-button>-->
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-center">
      <el-pagination
        :current-page="pageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
      />
    </div>
    <el-dialog
      :title="`${isAdd ? '新增' : '编辑'}用户`"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <el-form ref="userDetail" :model="userDetail" :rules="checkRules" label-width="120px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userDetail.username" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item label="姓名" prop="peName">
          <el-input v-model="userDetail.peName" placeholder="请输入姓名"/>
        </el-form-item>
        <el-form-item label="用户身份" prop="isTeacher">
          <el-radio-group v-model="userDetail.isTeacher" size="medium">
            <el-radio-button label="-1">管理员</el-radio-button>
            <el-radio-button label="1">老师</el-radio-button>
            <el-radio-button label="0">学生</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="userDetail.phone" type="number" placeholder="请输入手机号"/>
        </el-form-item>
        <el-form-item label="用户默认密码为" v-if="isAdd">
          <u><b>12345678</b></u>
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
import Pagination from '@/components/Pagination'
import {getUserList, addUser, updateUser} from '@/api/user'
import {validatePhone} from "@/utils/validate"; // secondary package based on el-pagination

export default {
  components: {Pagination},
  data() {
    return {
      isAdd: false,
      userDetail: {},
      checkRules: {
        username: [
          {
            required: true,
            message: '用户名不能为空',
            trigger: 'blur'
          }
        ],
        peName: [
          {
            required: true,
            message: '姓名不能为空',
            trigger: 'blur'
          }
        ],
        phone: [
          {
            required: true,
            trigger: 'blur',
            validator: validatePhone
          }
        ],
        isTeacher: [
          {
            required: true,
            message: '用户身份不能为空',
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
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0
    }
  },
  mounted() {
    this.getUserList()
  },
  methods: {
    getUserList() {
      getUserList(this.pageIndex, this.pageSize).then(res => {
        if (res.data.code === '200') {
          this.list = res.data.data
          this.totalPage = res.data.total
        } else {
          this.$message.error(res.data.msg)
        }
      })
    },
    initUserDetail() {
      this.userDetail = {
        username: '',
        peName: '',
        isTeacher: -1,
        phone: ''
      }
    },
    async onNewUser() {
      this.$refs.userDetail.validate().then(isValidate => {
        if (isValidate) {
          if (this.isAdd) {
            addUser(this.userDetail.peName, this.userDetail.isTeacher, this.userDetail.username, this.userDetail.phone).then(res => {
              if (res.data.code === '200') {
                this.dialogVisible = false
                this.getUserList()
                this.$message.success('创建成功')
              } else {
                this.$message.error(res.data.msg)
              }
            })
          } else {
            updateUser(this.userDetail.peId, this.userDetail.peName, this.userDetail.isTeacher, this.userDetail.username, this.userDetail.phone, this.userDetail.password).then(res => {
              if (res.data.code === '200') {
                this.dialogVisible = false
                this.getUserList()
                this.$message.success('更新成功')
              } else {
                this.$message.error(res.data.msg)
              }
            })
          }
        }
      }).catch(e => {})
    },
    handleUpdate(row) {
      this.userDetail = {
        peId: row.peId,
        username: row.username,
        peName: row.peName,
        isTeacher: row.isTeacher,
        phone: row.phone
      }
      this.isAdd = false
      this.dialogVisible = true
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
    sizeChangeHandle(newSize) {
      this.pageSize = newSize
      this.getUserList()
    },
    currentChangeHandle(newCurrent) {
      this.pageIndex = newCurrent
      this.getUserList()
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
