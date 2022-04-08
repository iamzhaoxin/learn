<template>
  <el-main>
<!--    <el-main>-->
      <el-container>
<!--        todo 给header固定高度，然后让两个卡片相同高度-->
        <el-header style="height: 300px">
          <el-container style="height: 100%">
            <el-main style="width: 50%;">
              <TaskCard/>
            </el-main>
            <el-main style="width: 50%;">
              <ShortCut/>
            </el-main>
          </el-container>
        </el-header>
        <el-main>
          <MyBudget/>
        </el-main>
      </el-container>
<!--    </el-main>-->
  </el-main>
</template>

<script>
import TaskCard from "@/components/home/TaskCard";
import ShortCut from "@/components/home/ShortCut";
import MyBudget from "@/components/home/MyBudget";
export default {
  name: "HomeDashboard",
  components: {MyBudget, ShortCut, TaskCard},
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