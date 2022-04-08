<template>
  <el-form :model="formData" ref="vForm" :rules="rules" label-position="left" label-width="80px"
           size="default" @submit.prevent>
    <div class="static-content-item">
      <h1>我的预算</h1>
    </div>
    <el-row>
      <el-col :span="3" class="grid-cell">
        <div class="static-content-item">
          <el-button type="primary">全部</el-button>
        </div>
      </el-col>
      <el-col :span="3" class="grid-cell">
        <div class="static-content-item">
          <el-button type="primary">待提交</el-button>
        </div>
      </el-col>
      <el-col :span="3" class="grid-cell">
        <div class="static-content-item">
          <el-button type="primary">审批中</el-button>
        </div>
      </el-col>
      <el-col :span="3" class="grid-cell">
        <div class="static-content-item">
          <el-button type="primary">被退回</el-button>
        </div>
      </el-col>
      <el-col :span="3" class="grid-cell">
      </el-col>
      <el-col :span="3" class="grid-cell">
      </el-col>
      <el-col :span="3" class="grid-cell">
      </el-col>
      <el-col :span="3" class="grid-cell">
        <div class="static-content-item">
          <el-button type="info">查询</el-button>
        </div>
      </el-col>
    </el-row>
    <br>
    <div class="card-container">
      <el-card style="{width: 100% !important}" shadow="never">
        <template #header>
          <div class="clear-fix">
            <span>待提交</span>
            <i class="float-right el-icon-arrow-down"></i>
          </div>
        </template>
        <div class="table-container">
          <table class="table-layout">
            <tbody>
            <tr>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>物品名称</div>
                </div>
              </td>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>单价（元）</div>
                </div>
              </td>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>数量</div>
                </div>
              </td>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>总价</div>
                </div>
              </td>
            </tr>
            <tr>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>测试数据</div>
                </div>
              </td>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>static text</div>
                </div>
              </td>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>static text</div>
                </div>
              </td>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>static text</div>
                </div>
              </td>
            </tr>
            <tr>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>测试数据</div>
                </div>
              </td>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>static text</div>
                </div>
              </td>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>static text</div>
                </div>
              </td>
              <td class="table-cell">
                <div class="static-content-item">
                  <div>static text</div>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
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