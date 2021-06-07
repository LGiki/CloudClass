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
      <el-table-column prop="EnglishName" label="编码" align="center"/>
      <el-table-column prop="comment" label="字典值" align="center">
        <template slot-scope="{row}">
          <div style="display: flex; flex-wrap: wrap">
          <span v-for="item of row['items']" :key="item.ddId" class="dict-value">
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
    <el-dialog :title="`${isAdd ? '新增' : '编辑'}数据字典`" :visible.sync="dialogFormVisible" width="80%">
      <el-form ref="dictDetail" :model="dataDictDetail" label-width="120px">
        <el-form-item v-if="!isAdd" label="数据字典ID">
          <el-input v-model="dataDictDetail['d_id']" disabled/>
        </el-form-item>
        <el-form-item label="中文标识" prop="chineseName">
          <el-input v-model="dataDictDetail.ChineseName" placeholder="请输入中文标识"/>
        </el-form-item>
        <el-form-item label="英文标识" prop="englishName">
          <el-input v-model="dataDictDetail.EnglishName" placeholder="请输入英文标识"/>
        </el-form-item>
      </el-form>
      <div class="operation-button">
        <el-button type="danger" size="small"
                   @click="initDataDictItem(); isDataDictItemAdd = true; dataDictItemEditDialogFormVisible = true">
          <i class="el-icon-plus"/>&nbsp;新建数据字典项
        </el-button>
      </div>
      <el-table
        v-loading="dataDictItemTableLoading"
        :data="dataDictItemList"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column prop="ddId" label="数据字典项ID" align="center" v-if="!isAdd"/>
        <el-table-column prop="ddName" label="名称" align="center"/>
        <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
          <template slot-scope="{row,$index}">
            <el-button type="primary" size="mini" @click="handleDataDictItemUpdate(row,$index)">
              编辑
            </el-button>
            <el-button size="mini" type="danger" @click="onDeleteDataDictItem(row,$index)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-center">
        <el-pagination
          :current-page="dataDictItemTablePageIndex"
          :page-sizes="[5, 10]"
          :page-size="dataDictItemTablePageSize"
          :total="dataDictItemTableTotal"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="dataDictItemTablePageSizeChangeHandle"
          @current-change="dataDictItemTablePageCurrentChangeHandle"
        />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false;getList()">取 消</el-button>
        <el-button type="primary" @click="onEditSubmit">保 存</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :title="`${isDataDictItemAdd ? '新增' : '编辑'}数据字典项`" :visible.sync="dataDictItemEditDialogFormVisible"
      width="50%">
      <el-form ref="dataDictItem" :model="dataDictItem" label-width="120px">
        <el-form-item v-if="!isDataDictItemAdd && !isAdd" label="数据字典项ID">
          <el-input v-model="dataDictItem.ddId" disabled/>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="dataDictItem.ddName" placeholder="请输入名称"/>
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
  addDataDict,
  deleteDataDict,
  addDataDictItem,
  updateDataDictItem,
  deleteDataDictItem,
  getDataDictItemList,
  updateDataDict
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
      dataDictItemTablePageIndex: 1,
      dataDictItemTablePageSize: 5,
      dataDictItemTableTotal: 0,
      dataDictItemTableLoading: false,
      dataDictItemList: [],
      newDataDict: {},
      tempDataDictIndex: 0
    }
  },
  mounted() {
    this.list = []
    this.total = 10
    this.getList()
    this.initDataDictDetail()
  },
  methods: {
    onNewDataDict() {
      this.initDataDictDetail()
      this.dataDictItemList = []
      this.isAdd = true
      this.dialogFormVisible = true
      this.dataDictItemTableTotal = 0
    },
    dataDictItemTablePageSizeChangeHandle(newSize) {
      this.dataDictItemTablePageSize = newSize
      this.getDataDictItemList(this.dataDictDetail['d_id'])
    },
    dataDictItemTablePageCurrentChangeHandle(newCurrent) {
      this.dataDictItemTablePageIndex = newCurrent
      this.getDataDictItemList(this.dataDictDetail['d_id'])
    },
    getDataDictItemList(dId) {
      this.dataDictItemTableLoading = true
      getDataDictItemList(dId, this.dataDictItemTablePageIndex, this.dataDictItemTablePageSize).then(res => {
        if (res.data.code === '200') {
          this.dataDictItemList = res.data.data
          this.dataDictItemTableTotal = res.data.total
          console.log(this.dataDictItemTableTotal)
        } else {
          this.$message.error(res.data.msg)
        }
        this.dataDictItemTableLoading = false
      })
    },
    onDataDictItemEditSubmit() {
      if (this.isAdd) {
        if (this.isDataDictItemAdd) {
          this.dataDictItemList.push({
            ddId: 0,
            ddName: this.dataDictItem.ddName
          })
          this.dataDictItemEditDialogFormVisible = false
        } else {
          this.dataDictItemList[this.tempDataDictIndex].ddName = this.dataDictItem.ddName
          this.dataDictItemEditDialogFormVisible = false
        }
      } else {
        if (this.isDataDictItemAdd) {
          addDataDictItem(this.dataDictDetail['d_id'], this.dataDictItem.ddName).then(res => {
            if (res.data.code === '200') {
              this.$message.success('添加成功')
              this.getDataDictItemList(this.dataDictDetail['d_id'])
              this.dataDictItemEditDialogFormVisible = false
            } else {
              this.$message.error(res.data.msg)
            }
          })
        } else {
          updateDataDictItem(this.dataDictItem.ddId, this.dataDictItem.ddName).then(res => {
            if (res.data.code === '200') {
              this.$message.success('更新成功')
              this.getDataDictItemList(this.dataDictDetail['d_id'])
              this.dataDictItemEditDialogFormVisible = false
            } else {
              this.$message.error(res.data.msg)
            }
          })
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
        ddName: ''
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
        did: row.did
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
      if (this.isAdd) {
        addDataDict(this.dataDictDetail.ChineseName, this.dataDictDetail.EnglishName).then(res => {
          if (res.data.code === '200') {
            this.dialogFormVisible = false
            this.getList()
            this.$message.success('创建成功')
          } else {
            this.$message.error(res.data.msg)
          }
        })
      } else {
        updateDataDict(this.dataDictDetail['d_id'], this.dataDictDetail.ChineseName, this.dataDictDetail.EnglishName).then(res => {
          if (res.data.code === '200') {
            this.dialogFormVisible = false
            this.getList()
            this.$message.success('更新成功')
          } else {
            this.$message.error(res.data.msg)
          }
        })
      }
    },
    getList() {
      getDataDictList(this.pageIndex, this.pageSize).then(res => {
        if (res.data.code === '200') {
          this.dataDictList = res.data.data
          this.totalPage = res.data.total
        } else {

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
  border: 1px solid #d9ecff;
  border-radius: 5px;
  margin: 2px;
  color: #409EFF;
  padding: 0 2px;
  background-color: #ecf5ff;
}
</style>

