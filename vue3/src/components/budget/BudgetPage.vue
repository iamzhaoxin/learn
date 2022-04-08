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
        <BudgetInsert/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import GlobalAside from "@/components/page/GlobalAside";
import GlobalHeader from "@/components/page/GlobalHeader";
import BudgetInsert from "@/components/budget/BudgetInsert";
export default {
  name: "BudgetPage",
  components: {
    BudgetInsert,
    GlobalAside,
    GlobalHeader,
  }
}
</script>

<style scoped>

</style>