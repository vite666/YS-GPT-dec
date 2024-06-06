<script setup>

import LeftOperationComponent from "@/views/chat/components/LeftOperationComponent.vue";
import RightViewComponent from "@/views/chat/components/RightViewComponent.vue";
import {ref} from "vue";
import store from "@/store";

const rightRef = ref(null)

const leftRef = ref(null)

const leftForbid = ref(false)

/**
 * 替换聊天
 */
const replacementChatTemplate = (data) => {
  rightRef.value.replaceChatTemplate(data)
}

/**
 * 往下滚动
 */
const scrollDown = () => {
  rightRef.value.scrollToTheBottom()
}

/**
 * 刷新左侧数据源
 */
const flushedChatCache = () => {
  leftRef.value.flushedChatCache()
}


/**
 * 刷新禁用变量
 */
const leftControlEnable = (bol) => {
  leftForbid.value = bol
}


</script>

<template>
  <div class="body">
    <!-- 左侧操作菜单 -->
    <left-operation-component ref="leftRef"
                              v-if="store.getters.userInfo"
                              @replacement-chat-template="replacementChatTemplate"
                              @scroll-down="scrollDown"
                              :leftForbid="leftForbid"
                              class="left-menu"/>
    <!-- 聊天窗口 -->
    <right-view-component ref="rightRef"
                          @flushed-chat-cache="flushedChatCache"
                          @left-control-enable="leftControlEnable"
                          class="chat-window"/>
  </div>
</template>

<style scoped>
.body {
  display: flex;
  flex: 1;
  height: 100vh;
  position: relative; /* 确保父元素设置为相对定位 */
}

.left-menu {
  z-index: 13; /* 确保左侧操作菜单位于聊天窗口之上 */
}

.chat-window {
  flex: 1; /* 聊天窗口占据剩余空间 */
}

/* 当屏幕宽度小于768px时，调整布局使左侧操作菜单覆盖右侧聊天窗口 */
@media (max-width: 767px) {
  .left-menu {
    position: absolute;
    top: 0; /* 将左侧操作菜单置于父元素顶部 */
    left: 0; /* 将左侧操作菜单置于父元素左侧 */
  }

  .chat-window {
    margin-left: 0; /* 取消左边距 */
  }
}
</style>
