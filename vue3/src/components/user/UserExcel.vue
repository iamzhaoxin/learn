<template>
  <el-card class="guest-container">
    <template #header>
      <div class="header">
        <el-button type="primary" size="small" icon="el-icon-plus" @click="handleSolve">添加用户</el-button>
        <el-button type="primary" round size="small" icon="el-icon-plus" @click="handleSolve">Excel导入用户</el-button>
        <el-button type="danger" size="small" icon="el-icon-delete" @click="handleForbid">删除选中用户</el-button>
      </div>
    </template>
    <el-table
        v-loading="loading"
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
      <!--      选择框-->
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="nickName"
          label="用户名"
      >
      </el-table-column>
      <el-table-column
          prop="loginName"
          label="姓名"
      >
      </el-table-column>
      <el-table-column
          label="工号"
      >
      </el-table-column>
      <el-table-column
          label="性别"
      >
      </el-table-column>
      <el-table-column
          label="电话"
      >
      </el-table-column>
      <el-table-column
          label="邮箱"
      >
      </el-table-column>
      <el-table-column
          label="所在单位"
      >
      </el-table-column>
      <el-table-column
          label="角色"
      >
      </el-table-column>
      <el-table-column
          prop="createTime"
          label="出生年月"
      >
      </el-table-column>
      <!-- <el-table-column
        label="操作"
        width="100"
      >
        <template #default="scope">
          <a style="cursor: pointer; margin-right: 10px" @confirm="handleSolve(scope.row)">解除禁用</a>
          <el-popconfirm
            title="确定禁用吗？"
            @confirm="handleForbid(scope.row)"
          >
            <template #reference>
              <a style="cursor: pointer">禁用账户</a>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column> -->
    </el-table>
    <!--总数超过一页，再展示分页器-->
    <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="changePage"
    />
  </el-card>
</template>

<script>
import {onMounted, reactive, ref, toRefs} from 'vue'
import axios from '@/utils/axios'
import {ElMessage} from 'element-plus'

export default {
  name: 'UserExcel',
  setup() {
    const multipleTable = ref(null)
    const state = reactive({
      loading: false,
      tableData: [], // 数据列表
      multipleSelection: [], // 选中项
      total: 0, // 总条数
      currentPage: 1, // 当前页
      pageSize: 10 // 分页大小
    })
    onMounted(() => {
      getGuestList()
    })
    // 获取用户数据
    const getGuestList = async () => {
      state.loading = true
      axios
          .get('/api/user/all', {
            params: {
              pageNumber: state.currentPage,
              pageSize: state.pageSize,
            }
          })
          .then(response => {
            //将分页数据保存
            state.tableData=response.data.records
            state.currentPage=response.data.current
            state.total=response.data.total
            state.loading=false

            console.log(state.tableData)
          })
          .catch((error) => {
            console.log("error_UserExcel_129")
            ElMessage.error(error.toString())
            console.log(error)
          })
    }
    // 选择项
    const handleSelectionChange = (val) => {
      state.multipleSelection = val
    }
    const changePage = (val) => {
      state.currentPage = val
      getGuestList()
    }
    const handleSolve = () => {
      if (!state.multipleSelection.length) {
        ElMessage.error('请选择项')
        return
      }
      axios.put(`/users/0`, {
        ids: state.multipleSelection.map(item => item.userId)
      }).then(() => {
        ElMessage.success('解除成功')
        getGuestList()
      })
    }
    const handleForbid = () => {
      if (!state.multipleSelection.length) {
        ElMessage.error('请选择项')
        return
      }
      axios.put(`/users/1`, {
        ids: state.multipleSelection.map(item => item.userId)
      }).then(() => {
        ElMessage.success('禁用成功')
        getGuestList()
      })
    }
    return {
      ...toRefs(state),
      multipleTable,
      handleSelectionChange,
      getGuestList,
      changePage,
      handleSolve,
      handleForbid
    }
  }
}
</script>

<style scoped>
.guest-container {
  min-height: 100%;
}

.el-card.is-always-shadow {
  min-height: 100% !important;
}
</style>