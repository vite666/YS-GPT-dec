<script setup>

import {onMounted, ref} from "vue";
import store from "@/store";
import router from "@/router";


const active = ref('Exemption Statement')

// 免责
const EStext = ref('' )

const CAtext = ref('')

const DAtext = ref('')

onMounted(()=>{
  let userInfo = store.getters.userInfo;
  if (userInfo === null) {
    router.push({
      path: "/#"
    });
  } else {
    // 设置 text 的初始值
    EStext.value = `
### 免责申明

**在使用本网站提供的 AI 服务前，请您务必仔细阅读并理解本《免责声明》（以下简称“本声明”）。请您知悉，如果您选择继续访问本网站、或使用本网站提供的本服务以及通过各类方式利用本网站的行为（以下统称“本服务”） ，则视为接受并同意本声明全部内容。**

**一.** 本服务仅供个人学习、学术研究目的使用，未经许可，请勿分享、传播输入及生成的文本、图片内容。您在从事与本服务相关的所有行为(包括但不限于访问浏览、利用、转载、宣传介绍)时，必须以善意且谨慎的态度行事；您确保不得利用本服务故意或者过失的从事危害国家安全和社会公共利益、扰乱经济秩序和社会秩序、侵犯他人合法权益等法律、行政法规禁止的活动，并确保自定义输入文本不包含以下违反法律法规、政治相关、侵害他人合法权益的内容：
    1. 反对宪法所确定的基本原则的；
    2. 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；
    3. 损害国家荣誉和利益的；
    4. 歪曲、丑化、亵渎、否定英雄烈士事迹和精神，以侮辱、诽谤或者其他方式侵害英雄烈士的姓名、肖像、名誉、荣誉的；
    5. 宣扬恐怖主义、极端主义或者煽动实施恐怖活动、极端主义活动的；
    6. 煽动民族仇恨、民族歧视，破坏民族团结的；
    7. 破坏国家宗教政策，宣扬邪教和封建迷信的；
    8. 散布谣言，扰乱经济秩序和社会秩序的；
    9. 散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；
    10. 侮辱或者诽谤他人，侵害他人名誉、隐私和其他合法权益的；
    11. 含有虚假、有害、胁迫、侵害他人隐私、骚扰、侵害、中伤、粗俗、猥亵、或其它道德上令人反感的内容
    12. 中国法律、法规、规章、条例以及任何具有法律效力之规范所限制或禁止的其它内容。

**二.** 您确认使用本服务时输入的内容将不包含您的个人信息。您同意并承诺，在使用本服务时，不会披露任何保密、敏感或个人信息。

**三.** 您确认并知悉本服务生成的所有内容都是由人工智能模型生成，所以可能会出现意外和错误的情况，请确保检查事实。我们对其生成内容的准确性、完整性和功能性不做任何保证，并且其生成的内容不代表我们的态度或观点，仅为提供更多信息，也不构成任何建议或承诺。对于您根据本服务提供的信息所做出的一切行为，除非另有明确的书面承诺文件，否则我们不承担任何形式的责任。

**四.** 本服务来自于法律法规允许的包括但不限于公开互联网等信息积累，因互联网的开放性属性，不排除其中部分信息具有瑕疵、不合理或引发不快。

**五.** 不论在何种情况下，本网站均不对由于网络连接故障，电力故障，罢工，劳动争议，暴乱，起义，骚乱，火灾，洪水，风暴，爆炸，不可抗力，战争，政府行为，国际、国内法院的命令，黑客攻击，互联网病毒，网络运营商技术调整，政府临时管制或任何其他不能合理控制的原因而造成的本服务不能访问、服务中断、信息及数据的延误、停滞或错误，不能提供或延迟提供服务而承担责任。

**六.** 当本服务以链接形式推荐其他网站内容时，我们并不对这些网站或资源的可用性负责，且不保证从这些网站获取的任何内容、产品、服务或其他材料的真实性、合法性。在法律允许的范围内，本网站不承担您就使用本服务所提供的信息或任何链接所引致的任何直接、间接、附带、从属、特殊、继发、惩罚性或惩戒性的损害赔偿。
`;
    CAtext.value = `### 价格说明

本项目旨在寻用低价方式或渠道来为有学习和研究需求的本项目赞赏者提供一条实惠且稳定的途径去接触和了解 OpenAI 最新模型服务。通过本项目，其接触成本仅为原 [OpenAI 官方 API](https://openai.com/api/pricing/) 价格的三分之一。

### 使用指南

本项目内容输出框支持流程图，数学公式，markdown 语法的渲染。

gpt-4o 新特性，多模态，支持识别图片，生成图片，文档分析，联网查询。详情可看 OpenAI 官网介绍。

其他使用技巧：使用预设词。

### 错误处理

**1.** 输入内容含敏感信息
**解决方案：** 按免责声明的要求检查并修改输入内容

**2.** 输入内容太长
**解决方案：** 新建一个对话、删除部分聊天内容、设置中心选择压缩内容长度。gpt-4o 支持 128k 上下文，gpt-3.5-turbo 支持 16k 的上下文。

**3.** 网络不稳定
**解决方案：** 检查本地网络连接，重新发送对话

**4.** 模型接口方负载过大
**解决方案：** 换一个模型或稍后使用

**5.** 系统繁忙
**解决方案：** 重新返送对话

若还有疑问，请联系微信：vite-66`;
    DAtext.value = `
### 价格说明

MidJourney 绘图模型 0.35 一张

DALL -3 绘图模型 0.15 一张

### 使用指南

**提示词分为三部分：**
1、image prompts[图片提示]：可以将图像 URL 添加到提示中以影响最终结果的样式和内容。图片 URL 始终位于提示的前面。

2、Prompt Text[文本提示]：您要生成的图像的文本描述。

3、Parameters[参数]：参数改变图像的生成方式。参数可以更改宽高比、模型、放大器等等，参考参数 。

**提示词书写语法**

提示词 不需要太复杂，也不能太简单，太简单依赖于 ai 的基本样式，关键是要抓住你想要的核心描述 AI 不太擅长理解人类的语法，句子结构。单词的选择比较重要，可以找一些绘画描述的关键词，而不是自己想当当然的描述可以用逗号，括号，连字符来组织你的 prompt 如果你不要什么 不要描述 “no red” 要描述成 --no red

想清楚你的图像包含哪些内容主题：
人、动物、人物、地点、物体等。person, animal, character, location, object,

媒介：照片、绘画、插图、雕塑、涂鸦、挂毯等。photo, painting, illustration, sculpture, doodle, tapestry

环境：室内、室外、月球上、纳尼亚、水下、翡翠城等。indoors, outdoors, on the moon, in Narnia, underwater, the Emerald City

照明：柔和、环境、阴天、霓虹灯、工作室灯等 soft, ambient, overcast, neon, studio lights

颜色：充满活力、柔和、明亮、单色、彩色、黑白、柔和等。vibrant, muted, bright, monochromatic, colorful, black and white, pastel

情绪：稳重、平静、喧闹、精力充沛等。Sedate, calm, raucous, energetic

构图：人像、爆头、特写、鸟瞰图等。Portrait, headshot, closeup, birds-eye view,

举一个例子：A fluffy Samoyed puppy frolicking in a snowy landscape, the puppy's white fur contrasting with the stark white snow, pine trees dusted with snow in the background, a quiet, serene winter day, hyperrealistic style with attention to individual hairs and snowflakes, shot with a Canon EOS R5, using a telephoto lens to capture the dog from a distance, backlit by soft winter sunlight, reminiscent of the work by Rinko Kawauchi, --ar 1:1 --v 5

一只毛茸茸的萨摩耶小狗在雪景中嬉戏，小狗的白色皮毛与纯白的雪形成鲜明对比，背景中的松树上布满了雪，安静、安详的冬日，超现实的风格，注重个体的毛发和雪花，用佳能EOS R5，使用长焦镜头从远处捕捉狗，背光是柔和的冬日阳光，让人想起川内凛子的作品。

**参数表（部分）**

|命令  |作用   |参数值  |
| ------------ | ------------ | ------------ |
|- - ar n:m  |是长 m是宽 调整图片的宽长比。  |默认 1:1，可用 9:16、3:2 等|
| - - no  | 负提示词，指定不要什么  |   |
| - -stop n |  stop参数可以控制图像生成的阶段。以较早的百分比会产生更模糊、更不详细的结果 |  默认 100， 可用10~100|
| - - seed n |  设置随机种子使用相同的种子编号和提示将产生相似的结束图像 | 默认：随机0–4294967295  |
| - - creative  |  修改 test 和 testp 模型更加多样化和创造性 |   |


### 错误处理

**1.** 输入内容含敏感信息
**解决方案：** 按免责声明的要求检查并修改输入内容

**2.** 网络不稳定
**解决方案：** 检查本地网络连接，重新发起绘图任务

**3.** 模型接口方负载过大
**解决方案：** 换一个模型或稍后使用

**4.** 系统繁忙
**解决方案：** 重新发起绘图任务

**5.** 生成的图片被清理了
**解决方案：** 图片生成后，如有需要请自行下载保存，系统会定时清理已生成超过3天的图片。

若还有疑问，请联系微信：vite-66`;
  }
})


</script>


<template>
  <div class="container">
    <div class="body">
      <el-tabs v-model="active">
        <el-tab-pane label="免责声明" name="Exemption Statement" style="height: 100%">
          <v-md-editor
              :model-value = "EStext"
              mode="preview"
          />

        </el-tab-pane>
        <el-tab-pane label="对话帮助" name="Conversation Assistance"  style="height: 100%">
          <v-md-editor
              :model-value = "CAtext"
              mode="preview"
          />
        </el-tab-pane>
        <el-tab-pane label="绘图帮助" name="Drawing AssistanceP" style="height: 100%">
          <v-md-editor
              :model-value = "DAtext"
              mode="preview"
          />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<style scoped>

:deep(.el-tabs__content) {
  height: 86vh !important;
  overflow-y: auto; /* 允许垂直滚动 */
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
