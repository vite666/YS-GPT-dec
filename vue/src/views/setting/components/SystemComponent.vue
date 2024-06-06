<script setup>

import {ref} from "vue";
import router from "@/router";

const menu = ref([
  {
    icon: 'orders.svg',
    text: '使用明细',
    to: '/setting/balanceRecords'
  },
  {
    icon: 'chat.svg',
    text: '模型总览',
    to: '/setting/userGpt'
  },
  {
    icon: 'prompt.svg',
    text: '帮助文档',
    to: '/setting/help'
  },
  {
    icon: 'applets.svg',
    text: '安卓应用',
    url: 'https://rzve.f-bq.cn/=0Shj'
  }
]);

const handleClick = (item) => {
  if (item.to) {
    router.push({ path: item.to });
  } else if (item.url) {
    window.open(item.url, '_blank');
  }
};

</script>

<template>
  <div class="appearance-title">
    系统信息
  </div>
  <div class="appearance-body">
    <div class="card-btn">
      <div class="operating-wrap" v-for="(item, index) in menu" :key="index" @click="handleClick(item)">
        <div>
          <div class="wrap-btn">
            <img :src="require('../../../assets/console/' + item.icon)" alt=""/>
          </div>
          <div class="wrap-text">{{ item.text }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.appearance-title {
  font-size: 14px;
  padding-left: 10px;
  font-weight: 550;
}

.appearance-body {
  background-color: white;
  margin-top: 10px;
  border-radius: 8px;
  padding: 12px;
  display: flex;
  align-items: center;
  margin-bottom: 30px;
}

.appearance-img-default {
  border-radius: 8px;
  width: 140px;
  height: 90px;
  overflow: hidden;
  border: 3px solid white;
}

.appearance-img-active {
  border-radius: 8px;
  width: 140px;
  height: 90px;
  overflow: hidden;
  border: 3px solid #9a93ff;
}

.appearance-img img {
  width: 100%;
  height: 100%;
}

.img-prompt {
  padding-top: 6px;
  font-size: 13px;
}

.appearance-spacing {
  margin-right: 10px;
}

.card-btn {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.operating-wrap {
  display: flex;
  margin: 10px 12px;
}

.wrap-btn {
  transition: transform 0.3s; /* 定义过渡效果的时间 */
  border-radius: 5px;
  background-color: #ededed;
  width: 60px;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 1%;
  cursor: pointer;
}

.wrap-btn:hover {
  transform: scale(1.2);
}

.wrap-btn img {
  width: 30px;
  height: 30px;
}

.wrap-text {
  text-align: center;
  padding-top: 6px;
  color: #a0a0a0;
}
</style>
