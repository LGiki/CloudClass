<template>
  <div class="app-container">
    <div class="operation-button">
      <el-button type="danger" @click="handleNewClassButton">
        <i class="el-icon-plus"/>&nbsp;新建班课
      </el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="ID" prop="id" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.cId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="课程名称" align="center">
        <template slot-scope="{row}">
          <span>{{ row.ccName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="班级名称" align="center">
        <template slot-scope="{row}">
          <span>{{ row.cName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="班课号" align="center">
        <template slot-scope="{row}">
          <span>{{ row.cNumber }}</span>
        </template>
      </el-table-column>
      <el-table-column label="教师" align="center">
        <template slot-scope="{row}">
          <span>{{ row.peName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="学期" align="center">
        <template slot-scope="{row}">
          <span>{{ row.term }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
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
      :title="`${isAdd ? '新增' : '编辑'}班课`"
      :visible.sync="dialogVisible"
      width="50%"
      @closed="dialogClosed"
    >
      <el-form ref="classDetail" :model="classDetail" :rules="checkRules" label-width="120px">
        <el-form-item label="课程名称" prop="ccName">
          <el-input v-model="classDetail.ccName" placeholder="请输入课程名称"/>
        </el-form-item>
        <el-form-item label="班级名称" prop="cName">
          <el-input v-model="classDetail.cName" placeholder="请输入班级名称"/>
        </el-form-item>
        <el-form-item label="学期" prop="term">
          <el-input v-model="classDetail.term" placeholder="请输入学期"/>
        </el-form-item>
        <el-form-item label="学时" prop="date">
          <el-input v-model="classDetail.date" type="number" placeholder="请输入学时"/>
        </el-form-item>
        <el-form-item label="院校" prop="college">
          <el-cascader
            v-model="value"
            :options="options"
            :props="{ expandTrigger: 'hover' }"
            @change="handleChange"></el-cascader>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onNewClass">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import {validatePhone} from "@/utils/validate"; // secondary package based on el-pagination
import {addClass, getClassList, queryAllCollege, updateClass} from "@/api/class";

export default {
  components: {Pagination},
  data() {
    return {
      value: [],
      options: [],
      isAdd: false,
      classDetail: {},
      checkRules: {
        ccName: [
          {
            required: true,
            message: '课程名称不能为空',
            trigger: 'blur'
          }
        ],
        cName: [
          {
            required: true,
            message: '班级名称不能为空',
            trigger: 'blur'
          }
        ],
        term: [
          {
            required: true,
            trigger: 'blur',
            message: '学期不能为空'
          }
        ],
        date: [
          {
            required: true,
            message: '学时不能为空',
            trigger: 'blur'
          }
        ],
        college: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (this.classDetail.universityId === '' || this.classDetail.collegeId === '') {
                callback(new Error('请选择院校'))
              } else {
                callback()
              }
            }
          }
        ]
      },
      dialogVisible: false,
      list: null,
      total: 0,
      listLoading: false,
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0
    }
  },
  mounted() {
    this.getClassList()
  },
  methods: {
    dialogClosed() {
      this.$refs.classDetail.resetFields()
    },
    handleChange(value) {
      this.classDetail.universityId = value[0]
      this.classDetail.collegeId = value[1]
    },
    getCollegeOptions(callback) {
      queryAllCollege().then(res => {
        if (res.data.code === '200') {
          for (const item of res.data.data) {
            const school = {
              value: item['s_id'],
              label: item.title
            }
            if (item.depart !== undefined && item.depart.length !== 0) {
              school.children = []
              for (const item2 of item.depart) {
                school.children.push({
                  value: item2.sid,
                  label: item2.sname
                })
              }
            }
            this.options.push(school)
          }
          if (callback) {
            callback()
          }
        } else {
          this.$message.error('获取院校列表失败')
        }
      })
    },
    getTerm() {
      const date = new Date()
      const year = date.getFullYear()
      const month = date.getMonth()
      if (month >= 8) {
        return `${year}-${year + 1}-1`
      } else {
        return `${year - 1}-${year}-2`
      }
    },
    getClassList() {
      getClassList(this.pageIndex, this.pageSize).then(res => {
        if (res.data.code === '200') {
          this.list = res.data.data
          this.totalPage = res.data.total
        } else {
          this.$message.error(res.data.msg)
        }
      })
    },
    initClassDetail() {
      this.classDetail = {
        ccName: '',
        cName: '',
        term: this.getTerm(),
        date: '',
        universityId: '',
        collegeId: ''
      }
    },
    async onNewClass() {
      this.$refs.classDetail.validate().then(isValidate => {
        if (isValidate) {
          if (this.isAdd) {
            addClass(this.classDetail.cName, this.classDetail.ccName, this.classDetail.term, this.classDetail.date, this.classDetail.universityId, this.classDetail.collegeId).then(res => {
              if (res.data.code === '200') {
                this.dialogVisible = false
                this.getClassList()
                this.$message.success('创建成功')
              } else {
                this.$message.error(res.data.msg)
              }
            })
          } else {
            updateClass(this.classDetail.cId, this.classDetail.ccId, this.classDetail.cName, this.classDetail.ccName, this.classDetail.term, this.classDetail.date, this.classDetail.universityId, this.classDetail.collegeId).then(res => {
              if (res.data.code === '200') {
                this.dialogVisible = false
                this.getClassList()
                this.$message.success('更新成功')
              } else {
                this.$message.error(res.data.msg)
              }
            })
          }
        }
      }).catch(e => {
      })
    },
    handleNewClassButton() {
      this.options = []
      this.value = []
      this.getCollegeOptions()
      this.initClassDetail()
      this.isAdd = true
      this.dialogVisible = true
    },
    handleUpdate(row) {
      this.classDetail = {
        cId: row.cId,
        cName: row.cName,
        ccName: row.ccName,
        term: row.term,
        date: row.date,
        universityId: row.universityId,
        collegeId: row.collegeId,
        ccId: row.ccId
      }
      this.options = []
      this.getCollegeOptions(() => {
        this.value = [row.universityId, row.collegeId]
      })
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
      this.getClassList()
    },
    currentChangeHandle(newCurrent) {
      this.pageIndex = newCurrent
      this.getClassList()
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
