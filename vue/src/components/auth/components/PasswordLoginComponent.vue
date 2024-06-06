<script setup>
import {defineEmits, ref, reactive} from 'vue';
import {ElNotification} from "element-plus";
import {validatePhoneNumber} from "@/utils/Utils";
import {reqPasswordLogin, reqGetCurrentUserInfo} from "@/api/auth";
import store from "@/store";
import Vcode from "vue3-puzzle-vcode";

const emits = defineEmits(['change-status-event', 'on-close']);

//提交表单
const form = ref({
  phoneNumber: '',
  password: ''
})

//登录加载按钮
const btnLoad = ref(false)

// 控制验证码弹窗的显示
const showVcode = ref(false)

// 拼图验证码成功后的回调
const handleVcodeSuccess = async () => {
  showVcode.value = false;
  await handleLogin();
}

const onClose = () => {
  showVcode.value = false;
};

// 拼图的图片资源 可为空，此时会自动下载使用随机网络图片
const images = reactive([

])



/**
 * 登录
 */
const handleLogin = async () => {
  const {phoneNumber, password} = form.value;
  if (!phoneNumber || !password) {
    ElNotification({
      title: "校验失败", message: '手机号或密码不能为空', type: "error",
    });
    return
  }

  if (!validatePhoneNumber(phoneNumber)) {
    ElNotification({
      message: "手机号有误", type: "error",
    });
    return;
  }
  try {
    //按钮动画
    btnLoad.value = true
    //执行登录
    const {data} = await reqPasswordLogin({phoneNumber, password});
    //设置身份令牌
    localStorage.setItem("token", data);
    //获取用户信息数据
    const res = await reqGetCurrentUserInfo();
    store.commit("setUserInfo", res.data);
    ElNotification({
      title: "登录成功",
      message: '欢迎体验YS-GPT',
      type: "success",
    });
    emits('on-close');
    location.reload();
  } catch (e) {
    ElNotification({
      title: "登陆失败",
      message: e.msg,
      type: "error",
    });
    btnLoad.value = false
  }
}


</script>

<template>
  <el-form ref="formRef" size="large">
    <el-form-item prop="phoneNumber">
      <el-input
          size="large"
          class="phoneNumber "
          v-model="form.phoneNumber"
          type="text"
          clearable
          placeholder="手机号"
          autocomplete="on"
      >
      </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
          size="large"
          class="phoneNumber "
          v-model="form.password"
          type="password"
          show-password
          placeholder="密码"
          autocomplete="on"
      >
      </el-input>
    </el-form-item>
    <el-form-item>
      <el-button
          :loading="btnLoad"
          type="primary"
          size="large"
          class="btn-submit"
          @click="showVcode = true">
        登录
      </el-button>
    </el-form-item>

    <el-form-item>
      <vcode :show="showVcode"
             :zIndex="9999" :imgs="images" @success="handleVcodeSuccess" @close="onClose"></vcode>
    </el-form-item>
  </el-form>



</template>

<style scoped>

.phoneNumber {
  font-size: 14px;
  height: 45px;
  font-family: SF;
}

.code-model {
  display: flex;
  align-items: center;
  flex: 1
}

.code {
  width: 70%;
  font-size: 14px;
  height: 45px;
  font-family: SF;
}

.code-btn {
  color: #b0b0b0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 30%;
  height: 45px;
  background-color: #F1F3F5;
  border-top-right-radius: 3px;
  border-bottom-right-radius: 3px;
  font-size: 14px
}

.code-btn-disable {

  color: #b0b0b0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 30%;
  height: 45px;
  background-color: #F1F3F5;
  border-top-right-radius: 3px;
  border-bottom-right-radius: 3px;
  font-size: 14px
}

.code-btn:hover {
  color: #783CFA;
  font-weight: 550;
  cursor: pointer;
}

.btn-submit {
  font-family: SF;
  font-size: 14px;
  margin-top: 35px;
  width: 100%;
  background: linear-gradient(90deg, rgba(93, 81, 254, 0.62) 1%, rgba(160, 73, 247, 0.55) 30.31%, rgba(203, 77, 164, 0.56) 66.24%, rgba(255, 128, 128, 0.57) 97.8%);;
  border: none;
  letter-spacing: 2px;
}

>>> .inputBox > .el-input__wrapper {
  background-color: #F1F3F5;
  outline: none;
  box-shadow: none;
  border-top-right-radius: 0;
  border-bottom-right-radius: 0
}


.btn-submit {
  background: linear-gradient(90deg, rgba(93, 81, 254, 0.62) 1%, rgba(160, 73, 247, 0.55) 30.31%, rgba(203, 77, 164, 0.56) 66.24%, rgba(255, 128, 128, 0.57) 97.8%);;
}

.btn-submit:hover,
.btn-submit:focus,
.btn-submit:active {
  background: linear-gradient(90deg, rgba(93, 81, 254, 0.62) 1%, rgba(160, 73, 247, 0.55) 30.31%, rgba(203, 77, 164, 0.56) 66.24%, rgba(255, 128, 128, 0.57) 97.8%);;
  outline: 0;
}

.to-enroll-div text {
  color: #7365FF;
}


</style>
