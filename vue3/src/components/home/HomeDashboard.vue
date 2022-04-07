<template>
  <el-main>
    <el-container>
      <el-header>
        <el-container>
          <el-aside>
            左侧卡片
          </el-aside>
          <el-main>
            右侧卡片
          </el-main>
        </el-container>
      </el-header>
      <el-main>“我的申请”</el-main>
    </el-container>
  </el-main>
</template>

<script>
export default {
  name: "HomeDashboard",
  setup(){
    const log=()=>{
      console.log('123')
    }
    return{
      log
    }
  }
}
</script>

<style scoped>

</style>