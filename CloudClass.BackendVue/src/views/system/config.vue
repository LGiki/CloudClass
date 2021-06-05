<template>
  <div class="app-container">
    <div class="operation-button">
      <el-button type="danger">
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
        prop="configId"
        header-align="center"
        align="center"
        width="80"
        label="ID"
      />
      <el-table-column
        prop="configName"
        header-align="center"
        align="center"
        label="参数名称"
      />
      <el-table-column
        prop="configKeyword"
        header-align="center"
        align="center"
        label="关键词"
      />
      <el-table-column
        prop="configValue"
        header-align="center"
        align="center"
        label="值"
      />
      <el-table-column
        prop="configSystemDefault"
        header-align="center"
        align="center"
        label="是否系统内置"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.configSystemDefault" size="small">是</el-tag>
          <el-tag v-else size="small" type="info">否</el-tag>
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
  </div>
</template>

<script>
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
      systemConfigList: [
        {
          configId: 1,
          configName: '签到经验值',
          configValue: 1,
          configKeyword: '签到经验值',
          configSystemDefault: false
        }
      ],
      systemConfigRules: {
        expPerSign: [
          {
            required: true, trigger: 'blur', validator: (rule, value, callback) => {
              console.log(value)
              if (!value || value.len === 0) {
                callback(new Error('不能为空'))
              } else if (parseInt(value) <= 0) {
                callback(new Error('每次签到的经验值必须大于0'))
              } else {
                callback()
              }
            }
          }
        ]
      }
    }
  },
  methods: {
    onSubmit() {
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
