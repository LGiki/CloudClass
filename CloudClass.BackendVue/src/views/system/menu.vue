<template>
  <div class="app-container">
    <div class="operation-button">
      <el-button type="danger" @click="handlerAddMenu">
        <i class="el-icon-plus"/>&nbsp;新建菜单
      </el-button>
    </div>
    <el-table
      :data="menuList"
      style="width: 100%"
      row-key="menuId"
      border
      lazy
      :loading="loading"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column
        prop="mId"
        label="ID"
        width="100"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="menuName"
        label="名称"
        align="center"
      >
      </el-table-column>
      <!--      <el-table-column-->
      <!--        header-align="center"-->
      <!--        align="center"-->
      <!--        label="图标"-->
      <!--      >-->
      <!--        <template slot-scope="scope">-->
      <!--          <svg-icon :icon-class="scope.row.icon || ''"/>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column
        prop="sort"
        header-align="center"
        align="center"
        width="150"
        label="排序号"
      >
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        width="100"
        label="类型"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isPage" size="small">页面</el-tag>
          <el-tag v-else-if="!scope.row.isPage" size="small" type="success">按钮</el-tag>
          <!--<el-tag v-else-if="scope.row.type === '1'" size="small" type="info">按钮</el-tag>-->
        </template>
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        width="100"
        label="是否显示"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isDisplay" size="small">是</el-tag>
          <el-tag v-else-if="!scope.row.isDisplay" size="small" type="info">否</el-tag>
          <!--<el-tag v-else-if="scope.row.type === '1'" size="small" type="info">按钮</el-tag>-->
        </template>
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        width="150"
        label="上级菜单"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.parentName === ''" size="small" type="info">无</el-tag>
          <el-tag v-else size="small">{{ scope.row.parentName }}</el-tag>
          <!--<el-tag v-else-if="scope.row.type === '1'" size="small" type="info">按钮</el-tag>-->
        </template>
      </el-table-column>
      <el-table-column
        prop="creator"
        header-align="center"
        align="center"
        width="150"
        label="创建者"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="200"
        label="创建时间"
      >
        <template slot-scope="scope">
          {{ scope.row.createTime.replaceAll('T', ' ') }}
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
    <el-dialog
      :title="`${isAdd ? '创建' : '编辑'}菜单`"
      :visible.sync="updateMenuDialogVisible"
      width="60%"
    >
      <el-form ref="menuDetail" :model="menuDetail" :rules="checkRules">
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="menuDetail.menuName" placeholder="请输入菜单名称"/>
        </el-form-item>
        <el-form-item label="菜单顺序" prop="sort">
          <el-input v-model="menuDetail.sort" placeholder="请输入菜单顺序" type="number"/>
        </el-form-item>
        <el-form-item label="菜单页面" prop="Link">
          <el-select v-model="menuDetail.Link" placeholder="请选择菜单页面">
            <el-option
              v-for="item in menuLinkOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <!--          <el-input v-model="menuDetail.Link" placeholder="请输入菜单顺序" type="number"/>-->
        </el-form-item>
        <template v-if="isAdd">
          <el-form-item label="是否有上级菜单" prop="hasParent">
            <el-switch
              v-model="menuDetail.hasParent"
              active-text="是"
              inactive-text="否">
            </el-switch>
          </el-form-item>
          <el-form-item label="上级菜单" prop="parentId" v-if="menuDetail.hasParent">
            <el-select v-model="menuDetail.parentId" placeholder="请选择">
              <el-option
                v-for="item in parentMenuList"
                :key="item.mid"
                :label="item.menuName"
                :value="item.mid">
              </el-option>
            </el-select>
          </el-form-item>
        </template>
        <el-form-item label="标识" prop="isPage">
          <el-switch
            v-model="menuDetail.isPage"
            active-text="页面"
            inactive-text="按钮">
          </el-switch>
        </el-form-item>
        <el-form-item label="是否可见" prop="isDisplay">
          <el-switch
            v-model="menuDetail.isDisplay"
            active-text="可见"
            inactive-text="不可见">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateMenuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onEditSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getAllMenu, getAllParentMenu, addMenu, updateMenu, deleteMenu} from "@/api/menu";
import menuMap from "@/menu_map";

export default {
  computed: {
    menuLinkOptions: function() {
      const result = []
      for (const key of Object.keys(menuMap)) {
        result.push({
          value: key,
          label: menuMap[key].label
        })
      }
      return result
    }
  },
  data() {
    return {
      updateMenuDialogVisible: false,
      // 遮罩层
      loading: false,
      menuList: [],
      menuOptions: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      menuDetail: {
        hasParent: false
      },
      parentMenuList: [],
      isAdd: false,
      checkRules: {
        menuName: [
          {
            required: true,
            message: '菜单名称不能为空',
            trigger: 'blur'
          }
        ],
        sort: [
          {
            required: true,
            message: '菜单顺序不能为空',
            trigger: 'blur'
          }
        ]
        // Link: [
        //   {
        //     required: true,
        //     message: '菜单页面不能为空',
        //     trigger: 'visible-change'
        //   }
        // ]
      }
    }
  },
  mounted() {
    this.getMenuList()
  },
  methods: {
    initMenuDetail() {
      this.menuDetail = {
        // sort: this.totalPage + 1,
        sort: '',
        Link: '',
        menuName: '',
        hasParent: false,
        isDisplay: true,
        isPage: false
      }
    },
    handlerAddMenu() {
      this.initMenuDetail()
      this.getAllParentMenu()
      this.isAdd = true
      this.updateMenuDialogVisible = true
    },
    handleUpdate(row) {
      this.menuDetail = {
        Link: row.Link === null ? '' : row.Link,
        isDisplay: row.isDisplay,
        isPage: row.isPage,
        mId: row.mId,
        menuName: row.menuName,
        sort: row.sort
      }
      console.log(this.menuDetail)
      this.isAdd = false
      this.updateMenuDialogVisible = true
    },
    getAllParentMenu() {
      getAllParentMenu().then(res => {
        if (res.data.code === '200') {
          this.parentMenuList = res.data.data
        } else {
          this.$message.error(res.data.msg)
        }
      })
    },
    onEditSubmit() {
      if (this.$refs.menuDetail.validate()) {
        if (this.isAdd) {
          if (this.menuDetail.hasParent && !this.menuDetail.parentId) {
            this.$message.error('父级菜单不能为空')
            return
          }
          addMenu(this.menuDetail.menuName, this.menuDetail.sort, this.menuDetail.isDisplay, this.menuDetail.isPage, this.menuDetail.hasParent ? this.menuDetail.parentId : -1, this.menuDetail.Link).then(res => {
            if (res.data.code === '200') {
              this.getMenuList()
              this.updateMenuDialogVisible = false
              this.$message.success('添加成功')
              this.$store.commit('menus/SET_IS_MENU_UPDATED', true)
            } else {
              this.$message.error(res.data.msg)
            }
          })
        } else {
          updateMenu(this.menuDetail.mId, this.menuDetail.menuName, this.menuDetail.sort, this.menuDetail.isDisplay, this.menuDetail.isPage, this.menuDetail.Link).then(res => {
            if (res.data.code === '200') {
              this.getMenuList()
              this.updateMenuDialogVisible = false
              this.$message.success('更新成功')
              this.$store.commit('menus/SET_IS_MENU_UPDATED', true)
            } else {
              this.$message.error(res.data.msg)
            }
          })
        }
      }
    },
    handleDelete(row, index) {
      this.$alert('您确定要删除该菜单吗？', '删除确认', {
        confirmButtonText: '确定',
        callback: action => {
          deleteMenu(row.mId).then(res => {
            if (res.data.code === '200') {
              this.getMenuList()
              this.$message.success('删除成功')
              this.$store.commit('menus/SET_IS_MENU_UPDATED', true)
            } else {
              this.$message.error('删除失败')
            }
          })
        }
      })
    },
    getMenuList() {
      getAllMenu(this.pageIndex, this.pageSize).then(res => {
        if (res.data.code === '200') {
          this.menuList = res.data.data
          this.totalPage = res.data.total
        } else {
          this.$message.error(res.data.msg)
        }
      })
    },
    sizeChangeHandle(newSize) {
      this.pageSize = newSize
      this.getMenuList()
    },
    currentChangeHandle(newCurrent) {
      this.pageIndex = newCurrent
      this.getMenuList()
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
