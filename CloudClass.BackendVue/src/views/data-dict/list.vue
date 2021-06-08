<template>
  <div class="app-container">
    <div class="operation-button">
      <el-button type="danger" @click="onNewDataDict">
        <i class="el-icon-plus"/>&nbsp;新建数据字典
      </el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="dataDictList"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column prop="d_id" label="数据字典ID" align="center"/>
      <el-table-column prop="ChineseName" label="名称" align="center"/>
      <el-table-column prop="EnglishName" label="关键字" align="center"/>
      <el-table-column prop="comment" label="字典明细项" align="center">
        <template slot-scope="{row}">
          <div style="display: flex; flex-wrap: wrap;justify-content: center">
            <span v-for="item of row['items']" :key="item.ddId" :class="item.isDefault == '1' ? 'dict-value dict-value-default' : 'dict-value'">
              {{ item.ddName }}
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleDataDictUpdate(row)">
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
    <el-dialog @closed="onNewDataDictDialogClosed" :title="`${isAdd ? '新增' : '编辑'}数据字典`" :visible.sync="dialogFormVisible" width="80%">
      <el-form ref="dataDictDialogForm" :model="dataDictDetail" :rules="newDataDictCheckRules" label-width="120px">
        <el-form-item v-if="!isAdd" label="数据字典ID">
          <el-input v-model="dataDictDetail['d_id']" disabled/>
        </el-form-item>
        <el-form-item label="名称" prop="ChineseName">
          <el-input v-model="dataDictDetail.ChineseName" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="关键字" prop="EnglishName">
          <el-input v-model="dataDictDetail.EnglishName" placeholder="请输入关键字"/>
        </el-form-item>
      </el-form>
      <div class="operation-button">
        <el-button
          type="danger"
          size="small"
          @click="initDataDictItem(); isDataDictItemAdd = true; dataDictItemEditDialogFormVisible = true"
        >
          <i class="el-icon-plus"/>&nbsp;新建数据字典明细项
        </el-button>
      </div>
      <el-table
        v-loading="dataDictItemTableLoading"
        :data="dataDictItemList"
        :key="dataDictItemTableKey"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column v-if="!isAdd" prop="ddId" label="数据字典明细项ID" align="center"/>
        <el-table-column prop="ddName" label="名称" align="center"/>
        <el-table-column prop="isDefault" label="默认值" align="center">
          <template slot-scope="{row,$index}">
            <el-tag v-if="row.isDefault === 1" size="small">是</el-tag>
            <el-tag v-else size="small" type="info">否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="{row,$index}">
            <div style="display: flex;justify-content: center">
              <el-button type="primary" size="mini" @click="handleDataDictItemUpdate(row,$index)">
                编辑
              </el-button>
              <el-button size="mini" type="danger" @click="onDeleteDataDictItem(row,$index)">
                删除
              </el-button>
              <div style="display: flex; flex-direction: column; font-size: .1rem; margin-left: 10px">
                <button class="change-order-button" @click="onDataDictItemUp(row,$index)" :disabled="$index === 0">
                  ↑
                </button>
                <button class="change-order-button" @click="onDataDictItemDown(row,$index)" :disabled="$index === dataDictItemList.length - 1">
                  ↓
                </button>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false;getList()">取 消</el-button>
        <el-button type="primary" @click="onEditSubmit">保 存</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :title="`${isDataDictItemAdd ? '新增' : '编辑'}数据字典明细项`"
      :visible.sync="dataDictItemEditDialogFormVisible"
      width="50%"
    >
      <el-form ref="dataDictItem" :model="dataDictItem" :rules="dataDictItemCheckRules" label-width="120px">
        <el-form-item v-if="!isDataDictItemAdd && !isAdd" label="明细项ID">
          <el-input v-model="dataDictItem.ddId" disabled />
        </el-form-item>
        <el-form-item label="名称" prop="ddName">
          <el-input v-model="dataDictItem.ddName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="默认值">
          <template>
            <el-radio v-model="dataDictItem.isDefault" :label="1">是</el-radio>
            <el-radio v-model="dataDictItem.isDefault" :label="0">否</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="数值" prop="sort">
          <template>
            <el-input v-model="dataDictItem.sort" type="number">是</el-input>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dataDictItemEditDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onDataDictItemEditSubmit">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getDataDictList,
  deleteDataDict,
  addDataDictItem,
  updateDataDictItem,
  deleteDataDictItem,
  getDataDictItemList,
  updateDataDict,
  addDataDictAndItem,
  updateDataDictAndItem
} from '@/api/data-dict' // secondary package based on el-pagination

export default {
  data() {
    return {
      keywords: '',
      inSearch: false,
      listLoading: false,
      dataDictDetailTableLoading: false,
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataDictList: null,
      dialogFormVisible: false,
      isAdd: true,
      dataDictDetail: {},
      isDataDictItemAdd: false,
      dataDictItemEditDialogFormVisible: false,
      dataDictItem: {},
      dataDictItemTableLoading: false,
      dataDictItemList: [],
      newDataDict: {},
      tempDataDictIndex: 0,
      dataDictItemTableKey: 0,
      dataDictItemCheckRules: {
        ddName: [
          {
            required: true,
            message: '名称不能为空',
            trigger: 'blur'
          }
        ],
        sort: [
          {
            required: true,
            trigger: 'blur',
            validator: (rule, value, callback) => {
              if (!value || value.length === 0) {
                callback(new Error('数值不能为空'))
                return
              }
              if (this.isSortExist(value)) {
                callback(new Error('数值重复'))
              }
            }
          }
        ]
      },
      newDataDictCheckRules: {
        ChineseName: [
          {
            required: true,
            message: '名称不能为空',
            trigger: 'blur'
          }
        ],
        EnglishName: [
          {
            required: true,
            message: '关键字不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted() {
    this.list = []
    this.total = 10
    this.getList()
    this.initDataDictDetail()
  },
  methods: {
    onNewDataDictDialogClosed() {
      this.dataDictItemTableLoading = false
    },
    onDataDictItemUp(row, index) {
      const tempDataDictItem = this.dataDictItemList[index]
      this.dataDictItemList[index] = this.dataDictItemList[index - 1]
      this.dataDictItemList[index - 1] = tempDataDictItem
      this.dataDictItemTableKey = Math.random()
    },
    onDataDictItemDown(row, index) {
      const tempDataDictItem = this.dataDictItemList[index]
      this.dataDictItemList[index] = this.dataDictItemList[index + 1]
      this.dataDictItemList[index + 1] = tempDataDictItem
      this.dataDictItemTableKey = Math.random()
    },
    onNewDataDict() {
      this.initDataDictDetail()
      this.dataDictItemList = []
      this.isAdd = true
      this.dialogFormVisible = true
      this.$refs.dataDictDialogForm.resetFields()
    },
    getDataDictItemList(dId) {
      this.dataDictItemTableLoading = true
      getDataDictItemList(dId).then(res => {
        if (res.data.code === '200') {
          this.dataDictItemList = res.data.data
        } else {
          this.$message.error(res.data.msg)
        }
        this.dataDictItemTableLoading = false
      })
    },
    isDefaultExist() {
      for (const dataDictItem of this.dataDictItemList) {
        if (dataDictItem.isDefault === 1) {
          return true
        }
      }
      return false
    },
    isSortExist(newSort) {
      for (const dataDictItem of this.dataDictItemList) {
        if (dataDictItem.sort == newSort) {
          return true
        }
      }
      return false
    },
    onDataDictItemEditSubmit() {
      if (this.isAdd) {
        // 新建数据字典
        if (this.isDataDictItemAdd) {
          // 增加数字字典明细项
          if (this.dataDictItem.isDefault && this.isDefaultExist()) {
            for (const dataDictItem of this.dataDictItemList) {
              dataDictItem.isDefault = 0
            }
          }
          this.dataDictItemList.push({
            ddId: 0,
            ddName: this.dataDictItem.ddName,
            isDefault: this.dataDictItem.isDefault,
            sort: this.dataDictItem.sort
          })
          this.dataDictItemEditDialogFormVisible = false
        } else {
          // 编辑数字字典明细项
          this.dataDictItemList[this.tempDataDictIndex].ddName = this.dataDictItem.ddName
          if (this.dataDictItem.isDefault && this.isDefaultExist()) {
            for (const dataDictItem of this.dataDictItemList) {
              dataDictItem.isDefault = 0
            }
          }
          this.dataDictItemList[this.tempDataDictIndex].isDefault = this.dataDictItem.isDefault
          this.dataDictItemEditDialogFormVisible = false
        }
      } else {
        if (this.isDataDictItemAdd) {
          // 增加数字字典明细项
          if (this.dataDictItem.isDefault && this.isDefaultExist()) {
            for (const dataDictItem of this.dataDictItemList) {
              dataDictItem.isDefault = 0
            }
          }
          this.dataDictItemList.push({
            ddId: 0,
            ddName: this.dataDictItem.ddName,
            isDefault: this.dataDictItem.isDefault,
            sort: this.dataDictItem.sort
          })
          this.dataDictItemEditDialogFormVisible = false
          // addDataDictItem(this.dataDictDetail['d_id'], this.dataDictItem.ddName).then(res => {
          //   if (res.data.code === '200') {
          //     this.$message.success('添加成功')
          //     this.getDataDictItemList(this.dataDictDetail['d_id'])
          //     this.dataDictItemEditDialogFormVisible = false
          //   } else {
          //     this.$message.error(res.data.msg)
          //   }
          // })
        } else {
          // 编辑数字字典明细项
          this.dataDictItemList[this.tempDataDictIndex].ddName = this.dataDictItem.ddName
          if (this.dataDictItem.isDefault && this.isDefaultExist()) {
            for (const dataDictItem of this.dataDictItemList) {
              dataDictItem.isDefault = 0
            }
          }
          this.dataDictItemList[this.tempDataDictIndex].isDefault = this.dataDictItem.isDefault
          this.dataDictItemEditDialogFormVisible = false
          // updateDataDictItem(this.dataDictItem.ddId, this.dataDictItem.ddName).then(res => {
          //   if (res.data.code === '200') {
          //     this.$message.success('更新成功')
          //     this.getDataDictItemList(this.dataDictDetail['d_id'])
          //     this.dataDictItemEditDialogFormVisible = false
          //   } else {
          //     this.$message.error(res.data.msg)
          //   }
          // })
        }
      }
    },
    onDeleteDataDictItem(row, index) {
      this.$alert('您确定要删除该数据字典项吗？', '删除确认', {
        confirmButtonText: '确定',
        callback: action => {
          if (action === 'confirm') {
            if (this.isAdd) {
              this.dataDictItemList.splice(index, 1)
            } else {
              deleteDataDictItem(row.ddId).then(res => {
                if (res.data.code === '200') {
                  this.$message.success('删除成功')
                  this.getDataDictItemList(this.dataDictDetail['d_id'])
                } else {
                  this.$message.error(res.data.msg)
                }
              })
            }
          }
        }
      })
    },
    initDataDictDetail() {
      this.dataDictDetail = {
        id: 0,
        chineseName: '',
        englishName: '',
        items: []
      }
    },
    initDataDictItem() {
      this.dataDictItem = {
        ddId: 0,
        ddName: '',
        isDefault: 0,
        sort: this.dataDictItemList.length
      }
    },
    sizeChangeHandle(newSize) {
      this.pageSize = newSize
      this.getList()
    },
    currentChangeHandle(newCurrent) {
      this.pageIndex = newCurrent
      this.getList()
    },
    handleDataDictUpdate(row) {
      this.dataDictItemList = []
      this.getDataDictItemList(row['d_id'])
      this.dataDictDetail = row
      this.isAdd = false
      this.dialogFormVisible = true
    },
    handleDataDictItemUpdate(row, index) {
      this.dataDictItem = {
        ddId: row.ddId,
        ddName: row.ddName,
        did: row.did,
        isDefault: row.isDefault,
        sort: row.sort
      }
      this.tempDataDictIndex = index
      this.isDataDictItemAdd = false
      this.dataDictItemEditDialogFormVisible = true
    },
    handleDelete(row, index) {
      this.$alert('您确定要删除该数据字典吗？', '删除确认', {
        confirmButtonText: '确定',
        callback: action => {
          if (action === 'confirm') {
            deleteDataDict(row['d_id']).then(res => {
              if (res.data.code === '200') {
                this.getList()
                this.$message.success('删除成功')
              } else {
                this.$message.error(res.data.msg)
              }
            })
          }
        }
      })
    },
    onEditSubmit() {
      if (this.$refs.dataDictDialogForm.validate()) {
        if (this.isAdd) {
          const items = []
          for (let i = 0; i < this.dataDictItemList.length; i++) {
            const dataDictItem = this.dataDictItemList[i]
            items.push({
              ddName: `${dataDictItem.ddName}`,
              isDefault: `${dataDictItem.isDefault}`,
              sort: `${i}`
            })
          }
          const data = {
            chineseName: `${this.dataDictDetail.ChineseName}`,
            englishName: `${this.dataDictDetail.EnglishName}`,
            items: items
          }
          addDataDictAndItem(data).then(res => {
            if (res.data.code === '200') {
              this.dialogFormVisible = false
              this.getList()
              this.$message.success('创建成功')
            } else {
              this.$message.error(res.data.msg)
            }
          })
        } else {
          const items = []
          for (let i = 0; i < this.dataDictItemList.length; i++) {
            const dataDictItem = this.dataDictItemList[i]
            items.push({
              ddId: `${dataDictItem.ddId}`,
              ddName: `${dataDictItem.ddName}`,
              isDefault: `${dataDictItem.isDefault}`,
              sort: `${i}`
            })
          }
          const data = {
            dId: `${this.dataDictDetail['d_id']}`,
            chineseName: `${this.dataDictDetail.ChineseName}`,
            englishName: `${this.dataDictDetail.EnglishName}`,
            items: items
          }
          updateDataDictAndItem(data).then(res => {
            if (res.data.code === '200') {
              this.dialogFormVisible = false
              this.getList()
              this.$message.success('更新成功')
            } else {
              this.$message.error(res.data.msg)
            }
          })
        }
      }
    },
    getList() {
      getDataDictList(this.pageIndex, this.pageSize).then(res => {
        if (res.data.code === '200') {
          this.dataDictList = res.data.data
          this.totalPage = res.data.total
        } else {
          this.$message.error(res.data.msg)
        }
      })
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
  margin-top: 1em;
}

.dict-value {
  border: 1px solid rgba(0,0,0,.05);
  border-radius: 5px;
  margin: 2px;
  color: rgba(0,0,0,.3);
  padding: 0 2px;
  background-color: rgba(0,0,0,.1);
  font-size: 12px;
}

.dict-value-default {
  border: 1px solid #d9ecff;
  color: #409EFF;
  background-color: #ecf5ff;
  font-weight: 900;
}

.change-order-button {
  background-color: #319795;
  color: white;
  font-weight: 900;
  border: 0;
  margin: 1px;
  cursor: pointer;
  border-radius: 3px;
}

.change-order-button:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}
</style>

