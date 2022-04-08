<template>
  <el-form :model="formData" ref="vForm" :rules="rules" label-position="left" label-width="80px"
           style="height: 100%" @submit.prevent>
    <div class="card-container"  style="height: 100%">
      <el-card style="height: 100%;{width: 100% !important}" shadow="never">
        <template #header>
          <div class="clear-fix">
            <span>快捷入口</span>
            <i class="float-right el-icon-arrow-down"></i>
          </div>
        </template>
        <el-row>
          <el-col :span="6" class="grid-cell">
            <div class="static-content-item">
              <el-button type="info">新增申请</el-button>
            </div>
          </el-col>
          <el-col :span="6" class="grid-cell">
            <div class="static-content-item">
              <el-button type="warning">待审申请</el-button>
            </div>
          </el-col>
          <el-col :span="6" class="grid-cell">
            <div class="static-content-item">
              <el-button type="success">个人查询</el-button>
            </div>
          </el-col>
          <el-col :span="6" class="grid-cell">
            <div class="static-content-item">
              <el-button type="primary">申请统计</el-button>
            </div>
          </el-col>
        </el-row>
        <br>
        <el-row>
          <el-col :span="6" class="grid-cell">
            <div class="static-content-item">
              <el-button type="danger">用户管理</el-button>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </div>
  </el-form>

</template>

<script>
import {
  defineComponent,
  toRefs,
  reactive,
  getCurrentInstance
}
  from 'vue'
export default defineComponent({
  components: {},
  props: {},
  setup() {
    const state = reactive({
      formData: {},
      rules: {},
    })
    const instance = getCurrentInstance()
    const submitForm = () => {
      instance.ctx.$refs['vForm'].validate(valid => {
        if (!valid) return
        //TODO: 提交表单
      })
    }
    const resetForm = () => {
      instance.ctx.$refs['vForm'].resetFields()
    }
    return {
      ...toRefs(state),
      submitForm,
      resetForm
    }
  }
})

</script>

<style lang="scss">
.el-input-number.full-width-input,
.el-cascader.full-width-input {
  width: 100% !important;
}

.el-form-item--medium {
  .el-radio {
    line-height: 36px !important;
  }

  .el-rate {
    margin-top: 8px;
  }
}

.el-form-item--small {
  .el-radio {
    line-height: 32px !important;
  }

  .el-rate {
    margin-top: 6px;
  }
}

.el-form-item--mini {
  .el-radio {
    line-height: 28px !important;
  }

  .el-rate {
    margin-top: 4px;
  }
}

.clear-fix:before,
.clear-fix:after {
  display: table;
  content: "";
}

.clear-fix:after {
  clear: both;
}

.float-right {
  float: right;
}

</style>

<style lang="scss" scoped>
div.table-container {
  table.table-layout {
    width: 100%;
    table-layout: fixed;
    border-collapse: collapse;

    td.table-cell {
      display: table-cell;
      height: 36px;
      border: 1px solid #e1e2e3;
    }
  }
}

div.tab-container {}

.label-left-align :deep(.el-form-item__label) {
  text-align: left;
}

.label-center-align :deep(.el-form-item__label) {
  text-align: center;
}

.label-right-align :deep(.el-form-item__label) {
  text-align: right;
}

.custom-label {}

.static-content-item {
  min-height: 20px;
  display: flex;
  align-items: center;

  :deep(.el-divider--horizontal) {
    margin: 0;
  }
}

</style>