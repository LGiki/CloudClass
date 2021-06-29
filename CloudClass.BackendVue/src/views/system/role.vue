<template>
  <div class="app-container">
    <!--    <div class="operation-button">-->
    <!--      <el-button type="danger">-->
    <!--        <i class="el-icon-plus"/>&nbsp;新建角色-->
    <!--      </el-button>-->
    <!--    </div>-->
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
        prop="id"
        header-align="center"
        align="center"
        width="80"
        label="ID"
      >
      </el-table-column>
      <el-table-column
        prop="rName"
        header-align="center"
        align="center"
        label="角色名称"
      >
      </el-table-column>
      <!--      <el-table-column-->
      <!--        prop="status"-->
      <!--        header-align="center"-->
      <!--        align="center"-->
      <!--        label="角色状态"-->
      <!--      >-->
      <!--        <template slot-scope="scope">-->
      <!--          <el-tag v-if="scope.row.status == '0'" size="small">正常</el-tag>-->
      <!--          <el-tag v-else-if="scope.row.status == '1'" size="small" type="info">停用</el-tag>-->
      <!--          <el-tag v-else size="small" type="info">未知</el-tag>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!--      <el-table-column-->
      <!--        prop="createTime"-->
      <!--        header-align="center"-->
      <!--        align="center"-->
      <!--        width="180"-->
      <!--        label="创建时间"-->
      <!--      >-->
      <!--      </el-table-column>-->
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
    <!--    <div class="pagination-center">-->
    <!--      <el-pagination-->
    <!--        @size-change="sizeChangeHandle"-->
    <!--        @current-change="currentChangeHandle"-->
    <!--        :current-page="pageIndex"-->
    <!--        :page-sizes="[10, 20, 50, 100]"-->
    <!--        :page-size="pageSize"-->
    <!--        :total="totalPage"-->
    <!--        layout="total, sizes, prev, pager, next, jumper"-->
    <!--      >-->
    <!--      </el-pagination>-->
    <!--    </div>-->
    <el-dialog
      title="编辑角色权限"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <!--      <el-form ref="dataDictItem" :model="dataDictItem" :rules="dataDictItemCheckRules" label-width="120px">-->
      <!--        <el-form-item v-if="!isDataDictItemAdd && !isAdd" label="明细项ID">-->
      <!--          <el-input v-model="dataDictItem.ddId" disabled />-->
      <!--        </el-form-item>-->
      <!--        <el-form-item label="名称" prop="ddName">-->
      <!--          <el-input v-model="dataDictItem.ddName" placeholder="请输入名称" />-->
      <!--        </el-form-item>-->
      <!--        <el-form-item label="默认值">-->
      <!--          <template>-->
      <!--            <el-radio v-model="dataDictItem.isDefault" :label="1">是</el-radio>-->
      <!--            <el-radio v-model="dataDictItem.isDefault" :label="0">否</el-radio>-->
      <!--          </template>-->
      <!--        </el-form-item>-->
      <!--        <el-form-item label="数值" prop="sort">-->
      <!--          <template>-->
      <!--            <el-input v-model="dataDictItem.sort" type="number">是</el-input>-->
      <!--          </template>-->
      <!--        </el-form-item>-->
      <!--      </el-form>-->
      <el-tree
        :props="props"
        :data="menus"
        node-key="id"
        show-checkbox
        :default-checked-keys="enableMenuIds"
        @check-change="handleCheckChange">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleUpdateSubmit">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getRoleMenu, updateRoleMenu} from "@/api/role";
import {getAllMenuWithoutPagination} from "@/api/menu";

export default {
  data() {
    return {
      props: {
        label: 'name',
        children: 'children'
      },
      menus: [],
      enableMenuIds: [],
      dialogVisible: false,
      dataList: [
        {
          id: 1,
          rId: 0,
          rName: '教师'
        },
        {
          id: 2,
          rId: -1,
          rName: '管理员'
        }
      ],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      toUpdateList: {},
      updateRoleId: 1
    }
  },
  async mounted() {
    // await this.getData()
  },
  methods: {
    async getData(roleId) {
      const roleMenuResponse = await getRoleMenu(roleId)
      const roleMenuData = roleMenuResponse.data.data
      console.log(roleMenuData)
      const enableMenuIds = []
      for (const item of roleMenuData) {
        if (item.status === 1) {
          enableMenuIds.push(item.mid)
        }
      }
      await getAllMenuWithoutPagination().then(res => {
        const menus = []
        for (const item of res.data.data) {
          const menuItem = {
            id: item.mId,
            // name: item.menuName + item.mId
            name: item.menuName
          }
          let childrenEnableCount = 0
          if (item.items.length > 0) {
            menuItem.children = []
            for (const childItem of item.items) {
              menuItem.children.push({
                id: childItem.mid,
                name: childItem.menuName
                // name: childItem.menuName + childItem.mid
              })
              if (enableMenuIds.includes(menuItem.mid)) {
                childrenEnableCount++
              }
            }
            if (childrenEnableCount !== item.items.length) {
              enableMenuIds.splice(enableMenuIds.indexOf(item.mId), 1)
            }
          }
          menus.push(menuItem)
        }
        this.menus = menus
        this.enableMenuIds = enableMenuIds
      })
    },
    handleUpdateSubmit() {
      let updateResult = true
      console.log(this.toUpdateList)
      for (const key of Object.keys(this.toUpdateList)) {
        updateRoleMenu(this.updateRoleId, key, this.toUpdateList[key].status).then(
          res => {
            if (res.data.code !== '200') {
              updateResult = false
            }
          }
        )
      }
      if (updateResult) {
        this.$store.commit('menus/SET_IS_MENU_UPDATED', true)
        this.$message.success('更新成功')
        this.dialogVisible = false
      } else {
        this.$message.error('更新失败')
      }
    },
    handleCheckChange(data, checked, indeterminate) {
      // console.log(data, checked, indeterminate)
      this.toUpdateList[data.id] = {}
      this.toUpdateList[data.id].status = indeterminate ? 1 : (checked ? 1 : 0)
      if (data.children !== undefined && data.children.length > 0) {
        for (const item of data.children) {
          if (item.id === data.id) {
            this.toUpdateList[item.id] = {}
            this.toUpdateList[item.id].status = checked ? 1 : 0
          }
        }
      }
      // console.log(this.toUpdateList)
    },
    async handleUpdate(row) {
      this.menus = []
      this.enableMenuIds = []
      this.toUpdateList = {}
      if (row.id === 1) {
        this.updateRoleId = 1
      } else if (row.id === 2) {
        this.updateRoleId = -1
      }
      await this.getData(this.updateRoleId)
      this.dialogVisible = true
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
