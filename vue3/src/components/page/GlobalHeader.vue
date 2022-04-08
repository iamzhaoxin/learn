<template>
<!--  <el-header>-->
    <el-row>
      <el-col :span="12">
        <div class="introduction">
          资产全生命周期信息化管理平台
        </div>
      </el-col>
      <el-col :span="12">
        <div class="user">
          <el-popover>
            <template #reference>
              {{ userInfo && userInfo.userNameReal || '' }}
            </template>
            <div style="text-align: right">
              <p>登录名：{{ userInfo && userInfo.userNameReal || '' }}</p>
              <p>昵称：{{ userInfo && userInfo.userName || '' }}</p>
              <el-tag effect="dark" @click="logout">退出</el-tag>
            </div>
          </el-popover>
        </div>
      </el-col>
    </el-row>
<!--  </el-header>-->
</template>

<script>
import {localGet, localRemove} from "@/utils";
import router from "@/router";

export default {
  name: "GlobalHeader",
  setup() {
    const userInfo = localGet('token')
    const logout=()=>{
      localRemove('token')
      // localRemove('autoLogin')
      router.push('/login')
    }

    return {
      userInfo,
      logout,
    }
  }
}
</script>

<style scoped>
.introduction{
  white-space: nowrap;
  width: min-content;
  padding-top: 15px;
  padding-left: 30px;
}
.user {
  padding-top: 15px;
  padding-right: 30px;
  float: right;
  width: 50px
}
</style>