<template>
  <el-card class="guest-container">
    <template #header>
      <div class="header">
        <el-row>
          <el-col :span="4">
            <el-button type="primary" @click="handleSolve">添加用户</el-button>
          </el-col>
          <el-col :span="4">
            <el-popover
                placement="bottom"
                title="Title"
                :width="200"
                trigger="hover"
                content="请严格按照模板Excel填写导入"
            >
              <template #reference>
                <el-upload
                    class="upload-demo"
                    action="api/user/upload"
                    :limit="1"
                    :on-success="uploadSuccessHandler"
                    :on-error="uploadErrorHandler">
                  <el-button type="primary">批量导入用户</el-button>
                </el-upload>
              </template>
            </el-popover>
          </el-col>
          <el-col :span="4">
            <el-button type="danger" @click="handleForbid">删除选中用户</el-button>
          </el-col>
          <!--          <el-col :span="4"><div class="grid-content bg-purple-light" /></el-col>-->
          <!--          <el-col :span="4"><div class="grid-content bg-purple" /></el-col>-->
          <!--          <el-col :span="4"><div class="grid-content bg-purple-light" /></el-col>-->
        </el-row>
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
          prop="userName"
          label="用户名"
      >
      </el-table-column>
      <el-table-column
          prop="userNameReal"
          label="姓名"
      >
      </el-table-column>
      <el-table-column
          prop="userId"
          label="工号"
      >
      </el-table-column>
      <el-table-column
          prop="userSex"
          label="性别"
      >
      </el-table-column>
      <el-table-column
          prop="userCellphone"
          label="电话"
      >
      </el-table-column>
      <el-table-column
          prop="userEmail"
          label="邮箱"
      >
      </el-table-column>
      <el-table-column
          prop="unitName"
          label="所在单位"
      >
      </el-table-column>
      <el-table-column
          prop="roleId"
          label="角色"
      >
      </el-table-column>
      <el-table-column
          prop="userBirthdate"
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
            state.tableData = response.data.records.filter(user=>{
              user.userBirthdate=user.userBirthdate.slice(0,10)
              if(user.roleId===0){
                user.roleId='管理员'
              }else{
                user.roleId='其他'
              }
              return user
            })
            state.currentPage = response.data.current
            state.total = response.data.total
            state.loading = false

            console.log(state.tableData)
          })
          .catch((error) => {
            console.log("error_UserExcel")
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
    /*添加用户*/
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
    /*删除选中用户*/
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
    const uploadSuccessHandler = () => {
      ElMessage.success("文件上传成功")
      this.$forceUpdate()
    }
    const uploadErrorHandler = () => {
      ElMessage.error("上传文件失败")
    }
    return {
      ...toRefs(state),
      multipleTable,
      handleSelectionChange,
      getGuestList,
      changePage,
      handleSolve,
      handleForbid,
      uploadSuccessHandler,
      uploadErrorHandler,
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