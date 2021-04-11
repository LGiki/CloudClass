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
        <i class="el-icon-plus"/>&nbsp;新建菜单
      </el-button>
    </div>
    <el-table
      :data="menuList"
      style="width: 100%"
      row-key="menuId"
      border
      lazy
      :load="loading"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column
        prop="menuName"
        width="150"
        label="名称"
        align="center"
      >
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        label="图标"
      >
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon || ''"/>
        </template>
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        label="类型"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isMenu == '1'" size="small">目录</el-tag>
          <el-tag v-else-if="scope.row.isMenu == '0'" size="small" type="success">菜单</el-tag>
          <!--<el-tag v-else-if="scope.row.type === '1'" size="small" type="info">按钮</el-tag>-->
        </template>
      </el-table-column>
      <el-table-column
        prop="orderNum"
        header-align="center"
        align="center"
        label="排序号"
      >
      </el-table-column>
      <el-table-column
        prop="link"
        header-align="center"
        align="center"
        width="150"
        label="菜单URL"
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
      </el-pagination>    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      keywords: '',
      inSearch: false,
      // 遮罩层
      loading: false,
      menuList: [
        {
          menuName: '开始',
          icon: 'drag',
          isMenu: 1,
          orderNum: 1,
          link: '/start'
        },
        {
          menuName: '修改',
          icon: 'form',
          isMenu: 0,
          orderNum: 1,
          link: '/modify'
        },
        {
          menuName: '列表',
          icon: 'list',
          isMenu: 0,
          orderNum: 1,
          link: '/list'
        }
      ],
      menuOptions: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0
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
      this.$alert('您确定要删除该菜单吗？', '删除确认', {
        confirmButtonText: '确定',
        callback: action => {
          // this.$message({
          //   type: 'success',
          //   message: '删除成功'
          // })
        }
      })
    },
    sizeChangeHandle() {},
    currentChangeHandle() {}
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
