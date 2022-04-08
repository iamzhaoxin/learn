<template>
  <el-container style="height: 100%">
    <el-aside>
      <GlobalAside/>
    </el-aside>
    <el-container>
      <el-header class="GlobalHeader">
        <GlobalHeader/>
      </el-header>
      <el-main>
        <UserExcel/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import GlobalAside from "@/components/page/GlobalAside";
import GlobalHeader from "@/components/page/GlobalHeader";
import UserExcel from "@/components/user/UserExcel";

export default {
  name: "UserPage",
  components: {
    GlobalAside,
    GlobalHeader,
    UserExcel,
  }
}
</script>

<style scoped>

</style>