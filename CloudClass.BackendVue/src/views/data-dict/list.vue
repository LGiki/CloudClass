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
      <el-button type="danger">
        <i class="el-icon-plus"/>&nbsp;新建数据字典
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
      <el-table-column label="数据字典ID" prop="id" align="center" width="120">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="中文标识" width="150" align="center">
        <template slot-scope="{row}">
          <span>{{ row.chinese }}</span>
        </template>
      </el-table-column>
      <el-table-column label="英文标识" min-width="150" align="center">
        <template slot-scope="{row}">
          <span>{{ row.english }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" width="100" align="center">
        <template slot-scope="{row}">
          <span>{{ row.comment }}</span>
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
  </div>
</template>

<script>
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  components: { Pagination },
  data() {
    return {
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
        chinese: '数据字典',
        english: 'data_dictionary',
        comment: 'Admin'
      })
    }
  },
  methods: {
    onSearch() {
    },
    onCancelSearch() {
    },
    getSortClass(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
    handleUpdate(row) {
      this.$router.push('/data-dict/edit/' + row.id)
    },
    handleDelete(row, index) {
      this.$alert('您确定要删除该数据字典吗？', '删除确认', {
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

