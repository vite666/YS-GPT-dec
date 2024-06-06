<script setup>

import PopoverComponent from "@/views/chat/components/components/PopoverComponent.vue";
import {defineEmits, defineProps} from "vue";


const emits = defineEmits(['handle-copy-content', 'handle-delete-gpt', 'handle-copy-code']);


const props = defineProps({
  isLoading: {
    type: Boolean
  },
  answer: {
    type: String
  },
  index: {
    type: Number
  },
  error: {
    type: Object
  }
});

</script>

<template>
  <div class="bot-chat">
    <el-popover placement="top-end" :width="80" :disabled="props.isLoading">
      <popover-component @copy-content-event="emits('handle-copy-content',props.answer)"
                         @delete-chat-event="emits('handle-delete-chat',props.index)"/>
      <template #reference>
        <div v-if="error.isError">
          <!--        普通失败-->
          <div v-show="error.type==='EXCEPTION_REPORTED'">
            <div class="error-message">
              <div>
                <img :src="require('../../../../assets/app/server_error.svg')" alt=""/>
              </div>
              <div class="error-message-text">
                <div class="error-message-title">您好,这是一条错误信息</div>
                <div class="error-message-detail">
                  <div>1.内容不合规，含敏感信息或上下文太长</div>
                  <div>2.网络不稳定，请重发信息</div>
                  <div>3.模型接口方负载过大，请换个模型 </div>
                  <div>4.系统繁忙，请稍后重试</div>
                </div>
                <div class="error-message-prompt">
                  已通知站点管理员修复该问题,可稍后再试,此条信息不会影响后续对话使用
                </div>
              </div>
            </div>
          </div>
          <!--        会员过期-->
          <div v-show="error.type==='MEMBERSHIP_EXPIRES'">
            <div class="error-message">
              <div>
                <img :src="require('../../../../assets/app/member_overdue.svg')" alt=""/>
              </div>
              <div class="error-message-text">
                <div class="error-message-title">您的额度不足</div>
                <div class="error-message-detail">
                  <div>您的额度不足,请您选择免费模型<br/>或点击右上角+号打赏本项目<br/>感谢您的理解和支持<br/>有任何问题请联系微信：vite-66</div>
                </div>

              </div>
            </div>
          </div>
        </div>

        <v-md-editor
            v-else
            :model-value="props.answer"
            mode="preview"
            @copy-code-success="emits('handle-copy-code')"
        />


      </template>
    </el-popover>
  </div>
</template>

<style scoped>


.error-message {
  display: flex;
  align-items: center
}

.error-message img {
  width: 120px;
  height: 120px
}

.error-message-text {
  padding-left: 10px
}

.error-message-title {
  font-size: 14px;
  font-weight: 550;
  color: #646464
}

.error-message-detail {
  padding-top: 10px;
  font-size: 11px
}

.error-message-prompt {
  padding-top: 10px;
  font-size: 11px;
  color: #535353
}
</style>
