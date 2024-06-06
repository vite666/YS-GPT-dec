import {createApp} from 'vue'
import App from '@/App.vue'
import router from '@/router'
import store from '@/store'
import '@/utils/Title'

// TODO ElementPlus
import ElementPlus from 'element-plus';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/theme-chalk/index.css';
import 'element-plus/theme-chalk/display.css';
import locale from 'element-plus/lib/locale/lang/zh-cn'
import 'element-plus/theme-chalk/dark/css-vars.css'

// TODO v-md-editor
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
// TODO Prism
import Prism from 'prismjs';
// TODO 代码高亮
import 'prismjs/components/prism-json';

// TODO Copy Code 快捷复制代码
import createCopyCodePlugin from "@kangc/v-md-editor/lib/plugins/copy-code";
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css';

// TODO 数学公式
import createKatexPlugin from '@kangc/v-md-editor/lib/plugins/katex/cdn';

// TODO 流程图
import createMermaidPlugin from '@kangc/v-md-editor/lib/plugins/mermaid/cdn';
import '@kangc/v-md-editor/lib/plugins/mermaid/mermaid.css';

// TODO 行号
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';

VMdEditor.use(vuepressTheme, {
    Prism,
});

// 复制代码
VMdEditor.use(createCopyCodePlugin());

// 数学公式
VMdEditor.use(createKatexPlugin());

// 流程图
VMdEditor.use(createMermaidPlugin());

// 行号
VMdEditor.use(createLineNumbertPlugin())

let app = createApp(App)
// v-md-editor
app.use(VMdEditor);


app.use(ElementPlus, {locale})
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(store);
app.use(router);
app.mount('#app')
