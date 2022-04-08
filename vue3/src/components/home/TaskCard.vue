<template>
  <el-form :model="formData" ref="vForm" :rules="rules" label-position="left" label-width="80px"
           style="height: 100%" @submit.prevent>
    <div class="tab-container" style="height: 100%">
      <el-tabs style="height: 100%" v-model="tab115121ActiveTab" type="border-card">
        <el-tab-pane name="tab1" label="待处理">
          <el-row>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>状态</div>
              </div>
            </el-col>
            <el-col :span="12" class="grid-cell">
              <div class="static-content-item">
                <div>项目名称</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>申报日期</div>
              </div>
            </el-col>
          </el-row>
          <div class="static-content-item">
            <el-divider direction="horizontal"></el-divider>
          </div>
          <el-row>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>待提交</div>
              </div>
            </el-col>
            <el-col :span="12" class="grid-cell">
              <div class="static-content-item">
                <div>科研设备采购</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>2022-04-13</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>待确认</div>
              </div>
            </el-col>
            <el-col :span="12" class="grid-cell">
              <div class="static-content-item">
                <div>货物类采购申请</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>2022-04-09</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>待审批</div>
              </div>
            </el-col>
            <el-col :span="12" class="grid-cell">
              <div class="static-content-item">
                <div>常规设备采购</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>2022-04-05</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>待审批</div>
              </div>
            </el-col>
            <el-col :span="12" class="grid-cell">
              <div class="static-content-item">
                <div>常规设备采购</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>2022-03-25</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>待审批</div>
              </div>
            </el-col>
            <el-col :span="12" class="grid-cell">
              <div class="static-content-item">
                <div>常规设备采购</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>2022-03-19</div>
              </div>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane name="tab-pane-66077" label="已处理">
          <el-row>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>任务状态</div>
              </div>
            </el-col>
            <el-col :span="12" class="grid-cell">
              <div class="static-content-item">
                <div>任务名称</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>申报日期</div>
              </div>
            </el-col>
          </el-row>
          <div class="static-content-item">
            <el-divider direction="horizontal"></el-divider>
          </div>
          <el-row>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>审批中</div>
              </div>
            </el-col>
            <el-col :span="12" class="grid-cell">
              <div class="static-content-item">
                <div>财政测试项目</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>2022-03-15</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>已通过</div>
              </div>
            </el-col>
            <el-col :span="12" class="grid-cell">
              <div class="static-content-item">
                <div>行政测试项目</div>
              </div>
            </el-col>
            <el-col :span="6" class="grid-cell">
              <div class="static-content-item">
                <div>2022-03-10</div>
              </div>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>
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
      'tab115121ActiveTab': 'tab1',
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