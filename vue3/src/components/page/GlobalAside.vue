<template>
  <el-scrollbar style="background-color: #ffffff;">


    <el-menu style="" :default-openeds="['1', '3','4','5'] ">


      <el-menu-item @click="$router.push('/home')" index=1>
        <template #title>我的主页</template>
      </el-menu-item>

      <el-sub-menu index=3>
        <template #title>计划管理</template>
        <el-menu-item-group>
          <el-sub-menu index=3-1>
            <template #title>申请管理</template>
            <el-menu-item-group>
              <el-menu-item @click="$router.push('/budget/increase')" index="3-1-1">新增申请</el-menu-item>
              <el-menu-item index="3-1-2">管理申请</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index=3-2>
            <template #title>申请审核</template>
            <el-menu-item-group>
              <el-menu-item index="3-2-1">待审申请</el-menu-item>
              <el-menu-item index="3-2-2">已审申请</el-menu-item>
              <el-menu-item index="3-2-3">审结申请</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="3-3">
            <template #title>申请查询</template>
            <el-menu-item-group>
              <el-menu-item index="3-3-1">个人申请查询</el-menu-item>
              <el-menu-item index="3-3-2">部门申请查询</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-menu-item index="3-4">申请统计</el-menu-item>
        </el-menu-item-group>
      </el-sub-menu>


      <el-sub-menu index=4>
        <template #title>基础信息</template>
        <el-menu-item-group>
          <el-sub-menu index=4-1>
            <template #title>部门管理</template>
            <el-menu-item-group>
              <el-menu-item index="4-1-1">职能部门</el-menu-item>
              <el-menu-item index="4-1-2">其他部门</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index=4-2>
            <template #title>角色用户</template>
            <el-menu-item-group>
              <el-menu-item index="4-2-1">角色管理</el-menu-item>
              <el-menu-item @click="$router.push('/user/manager')" index="4-2-2">用户管理</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-menu-item index="4-3">品目类别</el-menu-item>
        </el-menu-item-group>
      </el-sub-menu>

      <el-sub-menu index=5>
        <template #title>系统设置</template>
        <el-menu-item-group>
          <el-sub-menu index=5-1>
            <template #title>数据库管理</template>
            <el-menu-item-group>
              <el-menu-item index="5-1-1">表字段管理</el-menu-item>
              <el-menu-item index="5-1-2">数据备份</el-menu-item>
              <el-menu-item index="5-1-3">数据导入</el-menu-item>
              <el-menu-item index="5-1-4">数据清空</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index=5-2>
            <template #title>系统初始化</template>
            <el-menu-item-group>
              <el-menu-item index="5-2-1">模板字段</el-menu-item>
              <el-menu-item index="5-2-2">模板类型</el-menu-item>
              <el-menu-item index="5-2-3">系统参数</el-menu-item>
              <el-menu-item index="5-2-4">学校信息</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index=5-3>
            <template #title>流程设置</template>
            <el-menu-item-group>
              <el-menu-item index="5-3-1">流程</el-menu-item>
              <el-menu-item index="5-3-2">审核人</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index=5-4>
            <template #title>字典数据维护</template>
            <el-menu-item-group>
              <el-menu-item index="5-4-1">预算类型</el-menu-item>
              <el-menu-item index="5-4-2">归档类型</el-menu-item>
              <el-menu-item index="5-4-3">其他字典</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-menu-item index="5-5">附件管理</el-menu-item>
        </el-menu-item-group>
      </el-sub-menu>

      <el-menu-item index=6>
        <template #title>日志管理</template>
      </el-menu-item>

    </el-menu>
  </el-scrollbar>
</template>

<script>
import {reactive} from "vue";

export default {
  name: "GlobalAside",
  setup() {
    const menus = reactive([
      {
        menuName: '我的主页',
        menuId: 1,
        menuValue: ''
      },
      {
        menuName: '个人中心',
        menuId: 2,
        menuValue: ''
      },
      {
        menuName: '计划管理',
        menuId: 3,
        menuValue: [
          {
            menuName: '申请管理',
            menuId: 4,
            menuValue: [
              {
                menuName: '新增申请',
                menuId: 5,
                menuValue: ''
              },
              {
                menuName: '管理申请',
                menuId: 6,
                menuValue: ''
              },
            ]
          },
          {
            menuName: '申请审核',
            menuId: 7,
            menuValue: ''
          },
          {
            menuName: '申请查询',
            menuId: 8,
            menuValue: ''
          },
          {
            menuName: '申请统计',
            menuId: 9,
            menuValue: ''
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