<template>
  <el-container class="layout-container">
    <GlobalAside/>
    <el-container>
      <el-header class="header">
        <GlobalHeader/>
      </el-header>
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
.header {
  height: 5%;
  padding-left: 0;
  padding-right: 0;
  /*background-image: url('../../assets/img.png');*/
  background-color: lightskyblue;
}

.layout-container {
  height: 100%;
  border: 1px solid #eee
}
</style>