<template>
  <el-container class="layout-container-demo" style="height:100%; border: 1px solid #eee">
    <GlobalAside/>
    <el-container>
      <GlobalHeader/>
      <HomeDashboard/>
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