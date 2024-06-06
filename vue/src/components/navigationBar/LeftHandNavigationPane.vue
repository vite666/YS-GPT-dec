<template>

  <!-- 页面根元素，用于监听点击事件 -->
  <div @click="handleOutsideClick">
    <!-- 菜单栏 -->
    <div :class="{ 'menu-hidden': isMenuHidden }" class="menu-container">
      <menu-component :menu-list="menuList"/>
    </div>
    <!-- 菜单按钮（仅在小屏幕上显示） -->
    <button :class="{ 'menu-btn-shifted': isMenuButtonShifted }" class="menu-toggle-btn" @click="toggleMenu">☰</button>

    <!-- 视图渲染 -->
    <render-component/>
  </div>
</template>


<script setup>
import {onMounted, ref} from "vue";
import MenuComponent from "@/components/navigationBar/components/MenuComponent.vue";
import RenderComponent from "@/components/navigationBar/components/RenderComponent.vue";
import store from "@/store";

const menuList = ref([
  {
    iconDefault: 'chat.svg',
    iconActive: 'active/chat-active.svg',
    to: "/",
  },
  {
    iconDefault: 'drawing.svg',
    iconActive: 'active/drawing-active.svg',
    to: "/drawing",
  }, {
    iconDefault: 'app.svg',
    iconActive: 'active/app-active.svg',
    to: "/app",
  }
  , {
    iconDefault: 'programs.svg',
    iconActive: 'active/programs-active.svg',
    to: '/programs',
  }, {
    iconDefault: 'setting.svg',
    iconActive: 'active/setting-active.svg',
    to: '/setting',
  }
])
const isMenuHidden = ref(window.innerWidth <= 767); // Menu hidden by default on mobile
const isMenuButtonShifted = ref(window.innerWidth > 767); // Menu button shifted by default on desktop
const toggleMenu = () => {
  isMenuHidden.value = !isMenuHidden.value;
  isMenuButtonShifted.value = !isMenuButtonShifted.value;
};
const handleOutsideClick = (event) => {
  // 获取菜单栏元素
  const menuContainer = document.querySelector('.menu-container');
  const menuToggleBtn = document.querySelector('.menu-toggle-btn')
  const isSmallScreen = window.innerWidth <= 767;
  // 在移动端且点击位置不是按钮的地方
  if (isSmallScreen && menuToggleBtn && !menuToggleBtn.contains(event.target)) {
    // 检查点击事件的目标元素是否在菜单栏内
    if ((menuContainer && !menuContainer.contains(event.target))) {
      // 如果点击的不是菜单栏内部，则隐藏菜单栏
      isMenuHidden.value = true;
      isMenuButtonShifted.value = false;
    }
  }

};

onMounted(() => {
  if (store.getters.userInfo) {
    if (store.getters.userInfo.type === "ADMIN") {
      menuList.value.push({
        iconDefault: 'console.svg',
        iconActive: 'active/console-active.svg',
        to: '/console',
      })
    }
  }
})

</script>

<style scoped>
.menu-container {
  width: 60px; /* 根据需要调整宽度 */
  transition: transform 0.3s ease;
}

.menu-hidden {
  transform: translateX(-150%);
}


.menu-toggle-btn {
  display: none; /* 默认不显示按钮 */
  position: absolute;
  top: 2px;
  left: 5px;
  z-index: 11;
  background-color: #fff;
  border: none;
  padding: 10px;
  cursor: pointer;
  transition: left 0.3s ease; /* 添加过渡效果 */
}

.menu-btn-shifted {
  left: 65px; /* 调整按钮位置以避免遮挡菜单栏 */
}

@media (max-width: 767px) {
  .menu-container {
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    z-index: 10;
    background-color: #fff;
  }
  .menu-toggle-btn {
    display: block; /* 小屏幕上显示按钮 */
  }
}
</style>
