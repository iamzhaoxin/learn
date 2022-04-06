<template>
  <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
    <el-scrollbar>
      <el-menu :default-openeds="['1', '2']" unique-opened>
        <el-menu-item-group>
          <el-menu-item @click="console.log(121)" index=1>
            <!--        <el-sub-menu v-for="(menu,id) in menus" index={{id+1}} :key=id>-->
            <template #title>
              我的主页
            </template>
          </el-menu-item>
        </el-menu-item-group>
        <el-sub-menu index=2>
<!--        <el-sub-menu v-for="(menu,id) in menus" index={{id+1}} :key=id>-->
          <template #title>
            计划管理
          </template>
          <el-menu-item-group>
            <el-sub-menu index=2-1>
              <!--        <el-sub-menu v-for="(menu,id) in menus" index={{id+1}} :key=id>-->
              <template #title>
                申请管理
              </template>
              <el-menu-item-group>
                <el-menu-item index="2-1-1">新增申请</el-menu-item>
                <el-menu-item index="2-1-2">管理申请</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-menu-item index="2-2">申请审核</el-menu-item>
            <el-menu-item index="2-3">申请查询</el-menu-item>
            <el-menu-item index="2-4">申请统计</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
      </el-menu>
    </el-scrollbar>
  </el-aside>
</template>

<script>
import {reactive} from "vue";

export default {
  name: "GlobalAside",
  setup(){
    const menus=reactive([
      {
        menuName:'我的主页',
        menuId:1,
        menuValue:''
      },
      {
        menuName:'个人中心',
        menuId:2,
        menuValue:''
      },
      {
        menuName:'计划管理',
        menuId:3,
        menuValue:[
          {
            menuName:'申请管理',
            menuId:4,
            menuValue:[
              {
                menuName:'新增申请',
                menuId:5,
                menuValue:''
              },
              {
                menuName:'管理申请',
                menuId:6,
                menuValue:''
              },
            ]
          },
          {
            menuName:'申请审核',
            menuId:7,
            menuValue:''
          },
          {
            menuName:'申请查询',
            menuId:8,
            menuValue:''
          },
          {
            menuName:'申请统计',
            menuId:9,
            menuValue:''
          },
        ]
      }
    ])
    return {
      menus
    }
  }
}
</script>

<style scoped>

</style>