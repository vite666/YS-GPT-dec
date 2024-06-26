<script setup>
import {defineEmits, reactive, ref} from 'vue';
import {ElNotification} from "element-plus";
import {reqPhoneNumberRegister, reqGetCurrentUserInfo, reqSMScode} from "@/api/auth";
import {validatePhoneNumber} from "@/utils/Utils";
import store from "@/store";
import Vcode from "vue3-puzzle-vcode";

const emits = defineEmits(['change-status-event', 'on-close']);

//提交表单
const form = ref({
  phoneNumber: '',
  password: '',
  code: ''
})


// 控制验证码弹窗的显示
const showVcode = ref(false)

// 拼图验证码成功后的回调
const handleVcodeSuccess = async () => {
  showVcode.value = false;
  await handleGetCaptcha();
}

const onClose = () => {
  showVcode.value = false;
};




//登录加载按钮
const btnLoad = ref(false)

//获取验证码按钮OBJ
const btnCodeObj = ref({
  text: '获取验证码',
  disabled: false,
})

//验证码倒计时控制器
const countdown = ref(null)

/**
 * 获取验证码
 * @returns {Promise<void>}
 */
const handleGetCaptcha = async () => {
  const {phoneNumber} = form.value;
  if (!phoneNumber) {
    ElNotification({
      message: "手机号不能为空", type: "error",
    });
    return
  }
  if (!validatePhoneNumber(phoneNumber)) {
    ElNotification({
      message: "手机号格式不正确", type: "error",
    });
    return
  }
  let seconds = 120;
  try {
    btnCodeObj.value.text = "正在发送中";
    btnCodeObj.value.disabled = true
    //发送验证码
    await reqSMScode({phoneNumber});
    ElNotification({
      message: "短信验证码已发送 注意查收", type: "success",
    });
  } catch (e) {
    ElNotification({
      title: "获取验证码失败", message: e.msg, type: "error",
    });
    btnCodeObj.value.text = "获取验证码";
    btnCodeObj.value.disabled = false
    return
  }
  countdown.value = setInterval(() => {
    if (seconds === 0) {
      clearInterval(countdown.value);
      countdown.value = null;
      btnCodeObj.value.disabled = false;
      btnCodeObj.value.text = "获取验证码";
    } else {
      seconds--;
      btnCodeObj.value.text = `${seconds}` + "后获取";
    }
  }, 1000);


}

/**
 * 登录
 */
const handleLogin = async () => {
  const {phoneNumber, password, code} = form.value;
  if (!phoneNumber || !password || !code) {
    ElNotification({
      title: "校验失败", message: '手机号,密码或验证码不能为空', type: "error",
    });
    return
  }
  if (!validatePhoneNumber(phoneNumber)) {
    ElNotification({
      message: "手机号格式不正确", type: "error",
    });
    return
  }
  try {
    //按钮动画
    btnLoad.value = true
    //执行登录
    const {data} = await reqPhoneNumberRegister({phoneNumber, password, code});
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
          type="passwordFieldType"
          show-password
          placeholder="密码"
          autocomplete="on"
      >

      </el-input>
    </el-form-item>
    <el-form-item prop="code">
      <div class="code-model">
        <el-input
            size="large"
            class="inputBox code"
            v-model="form.code"
            type="text"
            placeholder="验证码"
            clearable
            autocomplete="off"
            maxlength="8"
        >
        </el-input>
        <div class="code-btn" @click="showVcode=true" v-if="!btnCodeObj.disabled">
          {{ btnCodeObj.text }}
        </div>
        <div class="code-btn-disable" v-else>
          {{ btnCodeObj.text }}
        </div>
      </div>
    </el-form-item>
    <el-form-item>
      <el-button
          :loading="btnLoad"
          type="primary"
          size="large"
          class="btn-submit"
          @click="handleLogin">
        注册
      </el-button>
    </el-form-item>
  </el-form>

  <vcode :show="showVcode"
         :zIndex="9999"  @success="handleVcodeSuccess" @close="onClose"></vcode>

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
