<template>
  <div class="app-container">
    <el-form ref="dictDetail" :model="dataDictItem" label-width="120px">
      <el-form-item v-if="!isAdd" label="数据字典ID">
        <el-input v-model="dataDictItem.id" disabled/>
      </el-form-item>
      <el-form-item label="中文标识" prop="chineseName">
        <el-input v-model="dataDictItem.chineseName" placeholder="请输入中文标识"/>
      </el-form-item>
      <el-form-item label="英文标识" prop="englishName">
        <el-input v-model="dataDictItem.englishName" placeholder="请输入英文标识"/>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" @click="onSubmit">保存</el-button>
        <el-button size="small" @click="$router.back(-1)">取消</el-button>
      </el-form-item>
    </el-form>
    <div class="operation-button">
      <el-button type="danger" size="small"
                 @click="initDataDictItem(); isDataDictItemAdd = true; dataDictItemEditDialogFormVisible = true">
        <i class="el-icon-plus"/>&nbsp;新建数据字典项
      </el-button>
    </div>
    <el-table
      v-loading="dataDictDetailTableLoading"
      :data="dataDictItemList"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column prop="ddId" label="数据字典项ID" align="center"/>
      <el-table-column prop="ddName" label="名称" align="center"/>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleDataDictItemUpdate(row)">
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
        :page-sizes="[10, 20, 50, 100]"
        :page-size="dataDictItemTablePageSize"
        :total="dataDictItemTableTotalPage"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
      />
    </div>

    <el-dialog
      :title="`${isDataDictItemAdd ? '新增' : '编辑'}数据字典项`" :visible.sync="dataDictItemEditDialogFormVisible"
      width="50%">
      <el-form ref="dataDictItem" :model="dataDictItem" label-width="120px">
        <el-form-item v-if="!isDataDictItemAdd" label="数据字典项ID">
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
import {deleteDataDictItem, getDataDictItemList} from "@/api/data-dict";

export default {
  data() {
    return {
      isDataDictItemAdd: false,
      dataDictDetailTableLoading: false,
      dataDictDetail: {},
      dataDictItemTablePageIndex: 1,
      dataDictItemTablePageSize: 5,
      dataDictItemTableTotalPage: 0,
      dataDictItem: {},
      dataDictItemList: [],
      dataDictItemEditDialogFormVisible: false,
    }
  },
  computed: {
    dataDictId: function () {
      return this.$route.params.id
    }
  },
  mounted() {
    this.getDataDictItemDetail()
  },
  methods: {
    onDataDictItemEditSubmit() {

    },
    initDataDictItem() {
      this.dataDictItem = {
        ddId: 0,
        ddName: ''
      }
    },
    sizeChangeHandle() {

    },
    currentChangeHandle() {

    },
    onSubmit() {
    },
    handleDataDictItemUpdate(row) {
      this.dataDictItem = {
        ddId: row.ddId,
        ddName: row.ddName,
        did: row.did
      }
      this.isDataDictItemAdd = false
      this.dataDictItemEditDialogFormVisible = true
    },
    onDeleteDataDictItem(row, index) {
      this.$alert('您确定要删除该数据字典项吗？', '删除确认', {
        confirmButtonText: '确定',
        callback: () => {
          deleteDataDictItem(row.ddId).then(res => {
            if (res.data.code === '200') {
              this.$message.success('删除成功')
            } else {
              this.$message.error(res.data.msg)
            }
          })
        }
      })
    },
    getDataDictItemDetail() {
      getDataDictItemList(this.dataDictId, this.dataDictItemTablePageIndex, this.dataDictItemTablePageSize).then(res => {
        if (res.data.code === '200') {
          this.dataDictItem = res.data.data
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
</style>
