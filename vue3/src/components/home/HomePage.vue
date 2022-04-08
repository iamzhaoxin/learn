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
        <HomeDashboard/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import GlobalAside from "@/components/page/GlobalAside";
import GlobalHeader from "@/components/page/GlobalHeader";
import HomeDashboard from "@/components/home/HomeDashboard";

export default {
  name: "HomePage",
  components: {
    GlobalAside,
    GlobalHeader,
    HomeDashboard,
  }
}
</script>

<style scoped>

</style>