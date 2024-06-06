<script setup>

import {onMounted, ref} from "vue";
import store from "@/store";
import router from "@/router";
import GptModelComponent from "@/views/setting/components/GptModelComponent.vue";

const active = ref('CHAT MODEL CONFIG')

onMounted(()=>{
  let userInfo = store.getters.userInfo;
  if (userInfo === null) {
    router.push({
      path: "/#"
    });
  }
})
</script>

<template>
  <div class="container">
    <div class="body">
      <el-tabs v-model="active">
        <el-tab-pane label="对话模型" name="CHAT MODEL CONFIG" style="height: 100%">
          <gpt-model-component/>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<style scoped>

:deep(.el-tabs__content) {
  height: 86vh !important;
}

@keyframes explainAnimation {
  from {
    transform: scale(0);
  }

  to {
    transform: scale(1);
  }
}
.container {
  animation: explainAnimation 0.3s;
  width: 100%;
  height: 100vh;
  padding: 10px;
  overflow: hidden;
  background-color: rgb(242, 242, 242);
}

.title {
  font-size: 17px;
  color: #535353;
  font-weight: 550
}

:deep(.el-tabs__item:hover) {
  color: #8a80ec;
}

:deep(.el-tabs__item.is-active) {
  color: #8a80ec;
  font-weight: 600;
}

:deep(.el-tabs__active-bar) {
  background-color: #8a80ec;
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
</style>
