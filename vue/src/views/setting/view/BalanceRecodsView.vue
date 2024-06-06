<script setup>

import {onMounted, ref} from "vue";
import {ElLoading} from "element-plus";

import {reqGetBalanceRecordsPage} from "@/api/balanceRecordManagement";
import store from "@/store";
import router from "@/router";

const prompt = ref('')


const total = ref(0);

const tableData = ref([])

const pageNum = ref(1)



const init = async (pageNum, prompt, userId) => {
  let service = ElLoading.service({fullscreen: true, text: '正在获取数据'})
  try {
    const {data} = await reqGetBalanceRecordsPage(pageNum, prompt, userId);
    console.log(data.total)
    if (data.records) {
      tableData.value = data.records
      total.value = data.total
    }
  } catch (e) {
    console.log(e)
  } finally {
    setTimeout(() => {
      service.close()
    }, 300)
  }
}

const search = () => {
  let userInfo = store.getters.userInfo;
  if (userInfo === null) {
    router.push({
      path: "/#"
    });
    return
  }
  console.log(prompt.value)
  pageNum.value = 1
  total.value = 0
  if (prompt.value.trim()) {

    init(pageNum.value, prompt.value, userInfo.userId)
  } else {
    init(pageNum.value, '', userInfo.userId)
  }
}
onMounted(() => {
  let userInfo = store.getters.userInfo;
  if (userInfo === null) {
    router.push({
      path: "/#"
    });
    return
  }
  init(pageNum.value, prompt.value, userInfo.userId)
})

</script>

<template>
  <div class="container">
    <div class="body">
      <div class="title">使用明细</div>

      <div class="condition">
        <el-input v-model="prompt" placeholder="类型名称" class="input-height"/>
        <el-button type="primary" class="input-button" @click="search">查找记录</el-button>
      </div>

      <el-table :data="tableData" stripe class="el-table"
                :header-cell-style="{'text-align':'center','font-size':'13px','font-weight':'500'}"
                :cell-style="{'font-size':'12px'}"
      >
        <el-table-column prop="createdTime" label="时间" />
        <el-table-column prop="type" label="类型" >
          <template #default="scope">
            <span
                :style="{ color: scope.row.type === '消费' ? 'green' : (scope.row.type === '打赏' ? 'red' : 'inherit') }">
              {{ scope.row.type }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="modelName" label="模型"/>
        <el-table-column prop="promptTokens" label="请求 tokens"/>
        <el-table-column prop="completionTokens" label="响应 tokens"/>
        <el-table-column prop="balance" label="额度"/>


      </el-table>
      <div class="pagination-padding">
        <el-pagination background layout="prev, pager, next"
                       :total="total"
                       :page-size="15"
                       @current-change="($event)=>init($event,prompt)"/>
      </div>
    </div>
  </div>

</template>

<style scoped>
@keyframes explainAnimation {
  from {
    transform: scale(0);
  }

  to {
    transform: scale(1);
  }
}

.pagination-padding {
  padding-top: 10px
}

.prompt {
  margin-bottom: 35px;
  font-size: 11px;
  color: #9d9d9d
}

.prompt span {

  color: #a743c3
}

:deep(.el-form-item__label) {

  font-size: 13px;
}

.el-table {
  width: 100%;
  height: 100%
}

.update-div {
  padding-top: 20px;
  text-align: right;
  padding-right: 10px;
}

.upload-btn {
  margin-left: 0 !important;
  width: auto !important;
  font-size: 13px !important;
}

.el-dialog-box {
  padding: 20px
}

.el-dialog-title {
  color: #404040;
  font-weight: 550;
  font-size: 16px
}

.el-form {
  margin-top: 50px
}

.el-width {
  width: 280px
}


.container {
  width: 100%;
  height: 100vh;
  padding: 10px;
  overflow: hidden;
  background-color: rgb(242, 242, 242);
  animation: explainAnimation 0.3s;
}

@keyframes explainAnimation {
  from {
    transform: scale(0);
  }

  to {
    transform: scale(1);
  }
}

.title {
  font-size: 17px;
  color: #535353;
  font-weight: 550
}

.body {

  display: flex;
  flex-direction: column;
  background-color: rgb(255, 255, 255);
  height: 100%;
  border-radius: 10px;
  padding: 15px;
  overflow: hidden;
}

.column-flex {
  display: flex;
  align-items: center;
  justify-content: center
}

:deep(.el-pager li:hover) {
  color: rgb(153, 59, 255);;
}

.member-default {
  width: 80px;
  height: 25px;
  border-radius: 4px;
  background-color: rgb(153, 59, 255);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center
}

.member-active {
  width: 80px;
  height: 25px;
  border-radius: 4px;
  background-color: rgb(183, 59, 255);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center
}

.type-wait {
  width: 120px;
  height: 25px;
  border-radius: 4px;
  background-color: rgb(116, 158, 213);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center
}

.type-succeed {
  width: 120px;
  height: 25px;
  border-radius: 4px;
  background-color: rgb(116, 213, 134);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center
}

.type-cancel {
  width: 120px;
  height: 25px;
  border-radius: 4px;
  background-color: rgb(93, 93, 93);
  color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center
}


.condition {
  padding-bottom: 10px;
  width: 400px;
  padding-top: 10px;
  display: flex;
  align-items: center
}

@media (max-width: 767px) {
  .condition{
    width: auto;
  }
}
.input-height {
  height: 32px
}

.input-button {
  font-size: 13px;
  border: none;
  height: 32px;
  margin-left: 10px;
  width: 100px;
  background-color: rgb(153, 59, 255)
}

.input-button:hover,
.input-button:focus,
.input-button:active {
  font-size: 13px;
  border: none;
  height: 32px;
  margin-left: 10px;
  width: 100px;
  background-color: rgb(153, 59, 255)
}

:deep(.el-pagination.is-background .el-pager li.is-active) {
  background-color: rgb(153, 59, 255)
}

:deep(.el-pagination.is-background .el-pager li) {
  background-color: transparent;
}


</style>
