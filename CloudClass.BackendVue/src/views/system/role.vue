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
        <svg-icon icon-class="search"/>&nbsp;搜索
      </el-button>
      <el-button v-if="inSearch" type="primary" @click="onCancelSearch">
        <i class="el-icon-close"/>&nbsp;取消搜索
      </el-button>
      <el-button type="danger">
        <i class="el-icon-plus"/>&nbsp;新建角色
      </el-button>
    </div>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      style="width: 100%;"
    >
      <!--      <el-table-column-->
      <!--        type="selection"-->
      <!--        header-align="center"-->
      <!--        align="center"-->
      <!--        width="50"-->
      <!--      >-->
      <!--      </el-table-column>-->
      <el-table-column
        prop="roleId"
        header-align="center"
        align="center"
        width="80"
        label="ID"
      >
      </el-table-column>
      <el-table-column
        prop="roleName"
        header-align="center"
        align="center"
        label="角色名称"
      >
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="角色状态"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '0'" size="small">正常</el-tag>
          <el-tag v-else-if="scope.row.status == '1'" size="small" type="info">停用</el-tag>
          <el-tag v-else size="small" type="info">未知</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="创建时间"
      >
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
      <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      keywords: '',
      inSearch: false,
      dataForm: {
        roleName: ''
      },
      dataList: [
        {
          roleId: 1,
          roleName: '测试',
          status: 0,
          createTime: '2021-04-11 00:00:00'
        },
        {
          roleId: 2,
          roleName: '测试',
          status: 0,
          createTime: '2021-04-11 00:00:00'
        },
        {
          roleId: 3,
          roleName: '测试',
          status: 0,
          createTime: '2021-04-11 00:00:00'
        },
        {
          roleId: 4,
          roleName: '测试',
          status: 0,
          createTime: '2021-04-11 00:00:00'
        }
      ],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false
    }
  },
  methods: {
    onSearch() {
    },
    onCancelSearch() {
    },
    handleUpdate(row) {
    },
    handleDelete(row, index) {
      this.$alert('您确定要删除该角色吗？', '删除确认', {
        confirmButtonText: '确定',
        callback: action => {
          // this.$message({
          //   type: 'success',
          //   message: '删除成功'
          // })
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
  margin-top: 1em;
  display: flex;
  width: 100%;
  justify-content: center;
}
</style>
