<script setup>

import {Plus} from "@element-plus/icons-vue";
import {defineExpose, ref} from "vue";
import {reqGetPromptWordsRandomly} from "@/api/drawing";
import {ElNotification} from "element-plus";

//图片大小
const sizeList = ref([
  {
    proportion: '1:1',
    text: '头像',
    image: 'size-1-1.f9b344b9.svg',
    isSelected: true,
    width: 512,
    height: 512
  },
  {
    proportion: '1:2',
    text: '手机壁纸',
    image: 'size-1-2.62c2da58.svg',
    isSelected: false,
    width: 1080,
    height: 2160
  },
  {
    proportion: '3:4',
    text: '小红书',
    image: 'size-3-4.ba364264.svg',
    isSelected: false,
    width: 384,
    height: 512
  },
  {
    proportion: '4:3',
    text: '文案图',
    image: 'size-4-3.a0ec2a1c.svg',
    isSelected: false,
    width: 512,
    height: 384
  },
  {
    proportion: '9:16',
    text: '海报',
    image: 'size-9-16.498b0472.svg',
    isSelected: false,
    width: 768,
    height: 1365
  },
  {
    proportion: '16:9',
    text: '电脑壁纸',
    image: 'size-4-3.a0ec2a1c.svg',
    isSelected: false,
    width: 1980,
    height: 1080
  }
])

//提交表单
const form = ref({
  images: '',
  prompt: '',
})


/**
 * 选择参考图
 * @param e
 * @returns {boolean}
 */
const onChangeFileTemp = (e) => {
  if (e.raw.type === 'image/jpg' || e.raw.type === 'image/png' || e.raw.type === 'image/jpeg') {
    if (e.raw.size / 1024 / 1024 > 2) {
      ElNotification({
        title: "MISTAKE",
        message: '图像大小不得超过2MB',
        type: "warning",
      });
      return false
    }
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = (event) => {
        form.value.images = event.target.result
        resolve(e);
      };
      reader.onerror = (error) => {
        reject(error);
      };
      reader.readAsDataURL(e.raw);
    });
  } else {
    ElNotification({
      title: "参考图",
      message: '请上传正确的图片',
      type: "error",
    });
    return false
  }
}

/**
 * 随机获取提示词
 */
const handleClickRandomPrompt = async () => {
  try {
    const {data} = await reqGetPromptWordsRandomly('SD');
    if (data){
      form.value.prompt = data.prompt
    }
  } catch (e) {
    console.log(e)
  }

}

/**
 * 返回表单参数

 */
const getFormParameter = () => {
  return form.value
}

/**
 * 清空表单参数

 */
const clearFormParameter = () => {
  form.value.prompt = ''
  form.value.images = ''
}


defineExpose(
    {getFormParameter, clearFormParameter}
)
</script>

<template>


  <div class="row">
    <div class="row-title">
      <div class="drawing-prompt">
        <span>
          提示词
        </span>
        <img :src="require('../../../../assets/app/random.svg')" alt="" @click="handleClickRandomPrompt">
      </div>
    </div>
    <div class="textarea-top">
      <el-input type="textarea" v-model="form.prompt"/>
    </div>
  </div>
  <div class="row">

    <div class="drawing-vae">
      <span>参考图</span>
    </div>
    <el-upload class="upload-file" :auto-upload="false"
               :on-change="($event)=>onChangeFileTemp($event)"
               :show-file-list="false">
      <el-image fit="cover" class="referenceDiagram" v-if="form.images" :src="form.images" alt=""/>
      <el-icon v-else>
        <Plus/>
      </el-icon>
    </el-upload>

  </div>

</template>

<style scoped>
.size-logo {
  padding-top: 10px;
  padding-bottom: 2px
}

.model-info {
  display: flex;
  align-items: center
}

.referenceDiagram {
  width: 100px;
  height: 100px;
}

.upload-file {

  border-radius: 8px;
  background-color: #efefef;
  width: 100px;
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.size-logo img {
  width: 23px;
  height: 23px
}


.size-proportion {
  padding-bottom: 2px;
  font-size: 14px;
  font-weight: 550;
  color: #a2a2a2
}

.size-text {
  font-size: 11px;

  color: #535353;
  padding-bottom: 10px;
}

.size-model-selected {
  border-radius: 12px;
  margin: 5px auto;
  width: 30%;
  height: 40%;
  background-color: #efefef;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 5px solid #9189ff;
}

:deep(.el-slider__button) {
  background-color: #6b5df8;
  border: none;
}

:deep(.el-slider__bar) {
  background-color: #6b5df8;
}

:deep(.el-slider__runway ) {
  background-color: #efefef;
}

:deep(.el-slider__button ) {
  width: 15px;
  height: 15px;
}


.size-model {
  border-radius: 12px;
  margin: 2% auto;
  width: 30%;
  height: 40%;
  background-color: #efefef;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 5px solid #efefef;
}

.size-container {

  display: flex;
  align-items: center;
  flex-wrap: wrap;
  margin-top: 10px;
}


.model-container {
  padding: 5px;
  border-radius: 8px;
  border: 1px solid #efefef;
  margin-top: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center

}

.el-select {
  width: 100%;
}


.el-select >>> .el-input__inner {
  background-color: #efefef;
  color: #6b6b6b;
}

.el-select >>> .el-input__wrapper {
  background-color: #efefef;
  border-radius: 15px;
}


:deep(.el-textarea__inner) {

  height: 80px;
  background-color: #efefef;
  box-shadow: none !important;
  resize: none !important;
  outline: none !important;
  border-radius: 15px;
}

.model-select >>> .el-input__wrapper {
  background-color: #ffffff;
  border: none;
  box-shadow: none;
}

.model-select >>> .el-input__inner {
  background-color: #ffffff;
  font-weight: 550;
}


.drawing-prompt {
  display: flex;
  justify-content: space-between
}

.textarea-top {
  margin-top: 5px
}

.drawing-prompt img {
  width: 20px;
  cursor: pointer;
  height: 20px
}

.drawing-vae {
  margin-bottom: 5px;
  font-size: 13px;
  margin-top: 10px;
  display: flex;
  align-items: center
}

.drawing-vae span {
  padding-right: 5px
}

.el-select-width {
  width: 100%;
}


.row {
  border-radius: 8px;
  background-color: white;
  padding: 10px;
  margin-bottom: 10px;
}

.row-title {
  font-weight: 550;
  font-size: 14px;
  color: #3c3c3c
}

.drawing-model {
  display: flex;
  align-items: center
}

.drawing-icon {
  padding: 10px 15px;
  background-color: #d2c9f8;
  border-radius: 5px;
}

.drawing-icon img {
  width: 50px;
  height: 50px
}

.drawing-text {
  padding-left: 10px;
  font-size: 14px;
  font-weight: 550;
  color: #5f5f5f;
  display: flex;
  align-items: center
}

.drawing-text span {
  padding-right: 5px
}

.drawing-handoff {
  padding-right: 15px

}

.part {
  border-radius: 10px;
  border: 1px solid #e5e5e5;
  padding: 10px 10px;
  margin-top: 10px;
  display: flex;
  align-items: flex-start
}

.part-image {
  width: 80px;
  height: 80px;
  background-color: #efefef
}

.part-context {
  padding-left: 8px
}

.part-size {
  font-size: 14px;
  font-weight: 550
}

.part-noise {
  font-size: 12px;
  margin-top: 10px;
  color: #767676
}

.fistula {
  display: flex;
  align-items: center;
  justify-content: space-between
}

.fistula-delete {
  width: 20px;
  height: 20px
}
</style>
