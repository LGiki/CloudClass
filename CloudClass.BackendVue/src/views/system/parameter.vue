<template>
  <div class="app-container">
    <div class="operation-button">
      <el-button type="danger" @click="initParameterDetail();isAdd=true;dialogFormVisible=true">
        <i class="el-icon-plus"/>&nbsp;新增系统参数
      </el-button>
    </div>
    <el-table
      v-loading="systemConfigListLoading"
      :data="systemConfigList"
      border
      style="width: 100%;"
    >
      <el-table-column
        prop="paId"
        header-align="center"
        align="center"
        width="80"
        label="ID"
      />
      <el-table-column
        prop="paName"
        header-align="center"
        align="center"
        label="参数名称"
      />
      <el-table-column
        prop="description"
        header-align="center"
        align="center"
        label="关键字"
      />
      <el-table-column
        prop="value"
        header-align="center"
        align="center"
        label="值"
      />
<!--      <el-table-column-->
<!--        prop="status"-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        label="状态"-->
<!--      >-->
<!--        <template slot-scope="scope">-->
<!--          <el-tag v-if="scope.row.status === 1" size="small">正常</el-tag>-->
<!--          <el-tag v-else size="small" type="info">禁用</el-tag>-->
<!--        </template>-->
<!--      </el-table-column>-->
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
    <el-dialog :title="`${isAdd ? '创建' : '编辑'}系统参数`" :visible.sync="dialogFormVisible" @closed="dialogClosed" width="70%">
      <el-form ref="parameterDetail" :model="parameterDetail" :rules="checkRules">
        <el-form-item v-if="!isAdd" label="参数ID" prop="paId">
          <el-input v-model="parameterDetail.paId" placeholder="请输入参数ID" disabled/>
        </el-form-item>
        <el-form-item label="参数名称" prop="paName">
          <el-input v-model="parameterDetail.paName" placeholder="请输入参数名称"/>
        </el-form-item>
        <el-form-item label="关键字" prop="description">
          <el-input v-model="parameterDetail.description" placeholder="请输入关键字" />
        </el-form-item>
        <el-form-item label="参数值" prop="value">
          <el-input v-model="parameterDetail.value" placeholder="请输入参数值" type="number"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onEditSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getSystemParameter, addSystemParameter, updateSystemParameter, deleteSystemParameter, isDescriptionExist} from '@/api/system'
import {validatePhone} from "@/utils/validate";

export default {
  data() {
    return {
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      systemConfig: {
        expPerSign: 0
      },
      systemConfigListLoading: false,
      systemConfigList: [],
      parameterDetail: {},
      isAdd: true,
      dialogFormVisible: false,
      checkRules: {
        paName: [
          {
            required: true,
            message: '参数名称不能为空',
            trigger: 'blur'
          }
        ],
        description: [
          {
            required: true,
            trigger: 'blur',
            validator: (rule, value, callback) => {
              if (!value || value.length === 0) {
                callback(new Error('关键字不能为空'))
                return
              }
              // TODO: 如果未修改则不判断是否重复
              isDescriptionExist(value).then(res => {
                if (res.data.code !== '200') {
                  callback(new Error(res.data.msg))
                }
              })
            }
          }
        ],
        value: [
          {
            required: true,
            message: '值不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted() {
    this.getSystemParameterList()
  },
  methods: {
    dialogClosed() {
      this.$refs.parameterDetail.resetFields()
    },
    onEditSubmit() {
      if (this.$refs.parameterDetail.validate()) {
        if (this.isAdd) {
          addSystemParameter(this.parameterDetail.paName, this.parameterDetail.description, this.parameterDetail.value, 1).then(res => {
            if (res.data.code === '200') {
              this.getSystemParameterList()
              this.dialogFormVisible = false
              this.$message.success('添加成功')
            } else {
              this.$message.error(res.data.msg)
            }
          })
        } else {
          updateSystemParameter(this.parameterDetail.paId, this.parameterDetail.paName, this.parameterDetail.description, this.parameterDetail.value, this.parameterDetail.status).then(res => {
            if (res.data.code === '200') {
              this.getSystemParameterList()
              this.dialogFormVisible = false
              this.$message.success('更新成功')
            } else {
              this.$message.error(res.data.msg)
            }
          })
        }
      }
    },
    initParameterDetail() {
      this.parameterDetail = {
        pdName: '',
        description: '',
        value: '',
        status: ''
      }
    },
    sizeChangeHandle(newSize) {
      this.pageSize = newSize
      this.getSystemParameterList()
    },
    currentChangeHandle(newCurrent) {
      this.pageIndex = newCurrent
      this.getSystemParameterList()
    },
    handleUpdate(row) {
      this.parameterDetail = {
        paId: row.paId,
        paName: row.paName,
        description: row.description,
        value: row.value,
        status: row.status
      }
      this.isAdd = false
      this.dialogFormVisible = true
    },
    handleDelete(row, index) {
      this.$alert('您确定要删除该系统参数吗？', '删除确认', {
        confirmButtonText: '确定',
        callback: action => {
          if (action === 'confirm') {
            deleteSystemParameter(row.paId).then(res => {
              if (res.data.code === '200') {
                this.getSystemParameterList()
                this.$message.success('删除成功')
              } else {
                this.$message.error(res.data.msg)
              }
            })
          }
        }
      })
    },
    getSystemParameterList() {
      getSystemParameter(this.pageIndex, this.pageSize).then(res => {
        if (res.data.code === '200') {
          this.systemConfigList = res.data.data
          this.totalPage = res.data.total
        } else {
          this.$message.error(res.data.msg)
        }
      })
    }
  }
}
</script>

<style>
.operation-button {
  text-align: right;
  padding-bottom: 10px;
}

.pagination-center {
  margin-top: 1em;
  display: flex;
  width: 100%;
  justify-content: center;
}
</style>
