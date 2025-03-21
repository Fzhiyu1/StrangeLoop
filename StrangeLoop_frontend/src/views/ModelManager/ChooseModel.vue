<script setup lang="ts">
import {nextTick, onMounted, ref} from "vue";
import { listModelAiOline } from "@/api/manageOl.ts";
import axios from "axios";

const linkType = ref(false);
const modelAiOlineList = ref([]);
const modelAiLocalList = ref([]);
const modelStore = useModelStore()
const ollamaRequest=ref(new OllamaRequest())
const form = ref({
  modelName: "",
  linkType: 0,
  description: "",
  localmodelName: "",
  aiolId: "",
  localmodelNameFront:""

});

// 获取在线模型和本地ollama模型
const listAll = () => {
  listModelAiOline({ data: {} }).then(res => {
    modelAiOlineList.value = res.data.data;
    console.log(modelAiOlineList)
  });
  ollamaRequest.value.sendOllamaBackBase().then(res => {
    modelAiLocalList.value = res;
  })
}
listAll();
onMounted(async ()=>{
  modelStore.modelIndex = 3;
  modelStore.modelList = (await listModelInfo({data: {}})).data.data;
})

import { computed } from "vue";
import {addModelInfo} from "@/api/manage.ts";
import router from "@/router";
import {ElMessage} from "element-plus";
import {useModelStore} from "@/store/ModelStore.ts";
import {listModelInfo} from "../../api/manage.ts";
import {OllamaRequest} from "../../utils/OllamaRequest.ts";
import {Logger} from "sass";
import {PinyinUtil} from "../../utils/pinyinUtil.ts";

const rules = computed(() => ({
  modelName: [
    { required: true, message: "请输入模型名称", trigger: "change" }, // 改为 change
  ],
  aiolId: [
    { required: !linkType.value, message: "请选择在线AI模型", trigger: ["change", "blur"] },
  ],
  localmodelName: [
    { required: linkType.value, message: "请选择本地AI模型", trigger: ["change", "blur"] },
  ],
  description: [
    { max: 500, message: "描述不能超过500个字符", trigger: "blur" }
  ]
}));

// 表单实例
const formRef = ref(null);

// 添加模型信息
const clickAddModelInfo = async () => {
  form.value.linkType = linkType.value ? 0 : 1;
  // 强制触发表单验证
  try {
    await formRef.value.validate(); // 使用 await 确保验证完成
    const modelData = {
      modelName: form.value.modelName,
      linkType: form.value.linkType,
      description: form.value.description,
      aiolId: form.value.aiolId,
      localmodelName: form.value.localmodelName,
      ollamaModelName:form.value.localmodelNameFront
    };
    // 检测是否为本地ai如果是则进入一下判断
    if(form.value.linkType==0) {
      // 如果添加模型到ollama成功则继续下面的代码 如果失败则直接结束
      await ollamaRequest.value.sendOllamaCreatedModel(
          {
            model: form.value.localmodelNameFront,
            from: form.value.localmodelName,
          }
      )
    }
    // 将数据添加到后端数据库
    addModelInfo({data:modelData}).then(async () => {
      // 还剩一个跳转操作
      ElMessage.success("创建成功");
        //跟新模型列表
      // modelStore.updateModelList()
      router.push("/modelManager?modelId+"+modelData)
      modelStore.modelList = (await listModelInfo({data: {}})).data.data;

       await nextTick(() => {
         const cards = document.querySelectorAll('.card')
         cards[cards.length - 1].click()
       })
    });
  } catch (error) {
    console.log("表单验证失败:", error);
  }
};
// 实时转换汉字为大写函数
const changeToLetter=()=>{
  // 后缀名
  let suffix = ":latest";
  const index = form.value.modelName;
  // 引入拼英工具类
  let pinyinUtil = new PinyinUtil();
  form.value.localmodelNameFront = form.value.modelName==""?pinyinUtil.PinYin(index):pinyinUtil.PinYin(index)+suffix;
}
// 切换时清空form对应的数据
const changeLinkType=()=>{
  if(linkType.value){
    form.value.aiolId = "";
  }else {
    form.value.localmodelName="";
  }
}
</script>

<template>
  <div>
    <div>
      <el-card id="card">
        <h2>创建一个专属于你的AI吧！</h2>
        <el-form :model="form" :rules="rules" ref="formRef">
          <el-form-item>
            <span>起一个名字</span>
          </el-form-item>
          <el-form-item class="input" prop="modelName">
            <el-input placeholder="输入模型名称" style="width: 40%" v-model="form.modelName" @input="changeToLetter()"></el-input>
            <span>{{form.localmodelNameFront}}</span>
          </el-form-item>
          <el-form-item>
            <span>选择基底模型</span>
          </el-form-item>
          <div class="flex-container">
            <el-form-item class="input" prop="aiolId">
              <el-select :disabled="linkType" placeholder="请选择在线AI" style="width: 200px" v-model="form.aiolId" prop="aiolId">
                <el-option v-for="dict in modelAiOlineList" :value="dict.aiId" :label="dict.aiName"></el-option>
              </el-select>
            </el-form-item>

            <el-tooltip content="点击切换链接方式">
              <el-switch
                  v-model="linkType"
                  @change="changeLinkType"
                  class="mb-2"
                  style="--el-switch-on-color: #13ce66; --el-switch-off-color: #49b9ff"
              />
            </el-tooltip>

            <el-form-item prop="localmodelName">
              <el-select :disabled="!linkType" placeholder="请选择本地AI" style="width: 200px" v-model="form.localmodelName" prop="localmodelName">
                <el-option v-for="dict in modelAiLocalList" :value="dict.name" :label="dict.name" />
              </el-select>
            </el-form-item>
          </div>

          <el-form-item>
            <span>描述（可选）</span>
          </el-form-item>
          <el-form-item class="input" prop="description">
            <el-input
                style="width: 60%"
                :autosize="{ minRows: 2, maxRows: 4 }"
                type="textarea"
                placeholder="输入描述"
                v-model="form.description"
            />
          </el-form-item>
          <el-form-item>
            <el-button @click="clickAddModelInfo" style="margin: 0 auto;background-color: #3f4583;color: white;width: 300px;height: 50px;font-size: 20px;border-radius: 15px">
              确认
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
#card {
  width: 600px;
  margin: 0 auto;
  position: relative;
  top: 150px;
}
#modelForm span {
  font-size: 15px;
  font-weight: bold;
}
.input{
  margin-top: -10px;

}
input{
  border: none; /* 移除所有默认边框 */
  outline: none; /* 移除获得焦点时的默认outline */
  height: 25px;
}
input:focus{

  border-bottom: 1px solid black; /* 设置下边框，可以根据需要调整宽度和颜色 */

}
/* Flexbox 容器样式 */
.flex-container {
  display: flex;
  justify-content: left; /* 根据需要调整对齐方式 */
  gap: 10px; /* 设置元素之间的间距 */
}
</style>
