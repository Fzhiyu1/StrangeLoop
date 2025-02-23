<template>
  <div class="model-management">
    <!-- 上方输入框区域，连接Ollama和本地Ollama区域放在一行 -->
    <el-row class="api-container">
      <el-col :span="8">
        <!-- 输入框 -->
        <div>
          <el-row>
            <h3>OllamaAPI地址：</h3>
          </el-row>
          <el-input
              style="width: 90%"
              v-model="apiUrl"
              placeholder="请输入 Ollama API 地址"
              class="api-input"
          ></el-input>
          <el-row>
            <el-button
                style="width: 70%;margin: 20px auto;border-radius: 20px;background-color: #3f4583"
                type="primary"
                class="connect-btn"
                @click="connectOllama"
            >
              <svg-icon icon-name="icon-lianjie" style="height: 30px;margin-right: 10px"/>
              连接 Ollama
            </el-button>
          </el-row>
          <el-row>
            <h4>注意：</h4>

          </el-row>
          <el-row>
            <span style="margin-left: 30px ;text-align: left">
              &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp当本地Ollama模型列表无法自动显示的时候，可以检查Ollama是否已经安装，端口有没有被屏蔽，或者更改OllamaAPI地址来进行尝试。
            </span>
          </el-row>
        </div>
      </el-col>

      <el-col :span="16" class="local-model-col">
        <!-- 本地Ollama模型管理 -->
        <div class="table-container">
          <div class="table-title">本地Ollama模型管理</div>
          <el-table :data="ollamaModelList" style="width: 100%" v-if="ollamaModelList.length > 0" class="model-table">
            <el-table-column label="编号" width="80" type="index"></el-table-column>
            <el-table-column prop="name" label="模型名称"></el-table-column>
            <el-table-column prop="details.parameter_size" label="模型版本"></el-table-column>

            <!--            <el-table-column label="操作" width="120">-->
            <!--              <template #default="scope">-->
            <!--                <el-button-->
            <!--                    @click="handleDelete(scope.row.id)"-->
            <!--                    type="danger"-->
            <!--                    icon="el-icon-delete"-->
            <!--                    size="small"-->
            <!--                    class="delete-btn"-->
            <!--                ></el-button>-->
            <!--              </template>-->
            <!--            </el-table-column>-->
          </el-table>
        </div>
      </el-col>
    </el-row>

    <!-- 在线AI模型管理 -->
    <div class="table-container">
      <div class="table-title">在线AI模型管理
        <el-tooltip
            class="box-item"
            effect="dark"
            content="点击添加在线模型"
            placement="top-start"
        >
          <el-button @click="openAddOnLineModelAi=true" icon="Plus" circle type="primary"
                     style="width: 25px;height: 25px"/>
        </el-tooltip>
      </div>
      <el-table :data="aiList" style="width: 100%" v-if="aiList.length > 0" class="ai-table">
        <el-table-column type="index" label="编号" width="80"></el-table-column>
        <el-table-column prop="aiName" label="AI名称"></el-table-column>
        <el-table-column prop="aiApi" label="API"></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button
                @click="handleEditOne(scope.row.aiId)"
                type="text"
                size="small"
                class="ai-action-btn"
                icon="Edit"
            >修改
            </el-button>
            <el-popconfirm
                width="220"
                :icon="InfoFilled"
                icon-color="#626AEF"
                title="确认要删除吗?"

            >
              <template #reference>
                <el-button
                    type="text"
                    size="small"
                    class="ai-action-btn"
                    icon="Delete"
                >删除
                </el-button>
              </template>
              <template #actions="{ confirm, cancel }">
                <el-button size="small" @click="cancel">取消</el-button>
                <el-button
                    type="danger"
                    size="small"
                    @click="handleDeletedOne(scope.row.aiId)"
                >
                  确认?
                </el-button>

              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog v-model="openAddOnLineModelAi" :title="isEdit ? '修改在线模型' : '新增在线模型'" width="800">
      <el-form :model="olAiForm" :rules="formRules" ref="olAiFormRef" label-width="auto">
        <el-form-item label="模型名称" prop="aiName">
          <el-input v-model="olAiForm.aiName"></el-input>
        </el-form-item>
        <el-form-item label="模型版本" prop="aiVersion">
          <el-input v-model="olAiForm.aiVersion" />
        </el-form-item>
        <el-form-item label="API地址" prop="aiApi">
          <el-input v-model="olAiForm.aiApi" />
        </el-form-item>
        <el-form-item label="Token" prop="aiToken">
          <el-input v-model="olAiForm.aiToken" />
        </el-form-item>
        <el-form-item label="模型类型" prop="aiType">
          <el-select v-model="olAiForm.aiType" placeholder="请选择ai类型">
            <el-option label="LLM模型" value="1"></el-option>
            <el-option label="聊天模型" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmitOlAi">
            提交
            </el-button>
          <el-button @click="onCancelOlAi">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {ref} from "vue";
import {ElInput, ElButton, ElTable, ElTableColumn, ElMessage, ElRow, ElCol} from "element-plus";
import SvgIcon from "@/components/SvgIcon.vue";
import axios from "axios";
import Cookies from "js-cookie";
import {
  addModelAiOline,
  deleteModelAiOline,
  getModelAiOline,
  listModelAiOline,
  updateModelAiOline
} from "@/api/manageOl.ts";
import {InfoFilled} from "@element-plus/icons-vue";

export default {
  name: "ModelManagement",
  computed: {
    InfoFilled() {
      return InfoFilled
    }
  },
  components: {
    SvgIcon,
    ElInput,
    ElButton,
    ElTable,
    ElTableColumn,
    ElMessage,
    ElRow,
    ElCol,
  },
  setup() {
    const clicked = ref(false)
    const apiUrl = ref("");
    const ollamaModelList = ref([])
    const ollamaApi = "http://localhost:11434/api";
    const openAddOnLineModelAi = ref(false);
    // 新增状态变量
    const isEdit = ref(false);
    const currentEditId = ref(null);
    const requestOllama = async () => {
    }
    const olAiForm = ref({
      aiName: "",
      aiVersion: "",
      aiApi: "",
      aiToken: "",
      aiType: "",
    });

    // 表单验证规则
    const formRules = ref({
      aiName: [
        { required: true, message: "请输入模型名称", trigger: "blur" },
      ],
      aiApi: [
        { required: true, message: "请输入API地址", trigger: "blur" },
      ],
      aiToken: [
        { required: true, message: "请输入Token", trigger: "blur" },
      ],
      aiType: [
        { required: true, message: "请选择模型类型", trigger: "change" },
      ],
      aiVersion: [
        { required: false, message: "请输入模型版本", trigger: "blur" },
      ],
    });
    // 初始化cookies
    const initOllamaApiToCookie = () => {
      //如果没有这个cookies则初始化
      if (Cookies.get("OllamaApi") === undefined) {
        Cookies.set("OllamaApi", ollamaApi);
      }
      let api = Cookies.get("OllamaApi");
      apiUrl.value = api;
    }
    // 设置后续cookies
    const setOllamaApiToCookie = (api) => {
      const expirationDays = 365 * 10; // 10 年
      Cookies.set("OllamaApi", api, {
        expires: expirationDays, // 单位是天（Number 类型）
        path: "/"                // 确保全站有效
      });
    }
    initOllamaApiToCookie();


    const aiList = ref([
      {id: 1, aiName: "AI小智", api: "http://127.0.0.1:11434/api"}
    ]);

    //获取本地模型列表
    const listOllamaModel = () => {

    }
    const connectOllama = () => {
      setOllamaApiToCookie(apiUrl.value);
      // 模拟连接 Ollama 操作
      if (apiUrl.value) {

        axios.get(apiUrl.value + "/tags")
            .then(response => {
              // 请求成功，处理响应数据
              if (response.status === 200) {
                ElMessage.success("Ollama链接初始化成功！");
                ollamaModelList.value = response.data.models;
                console.log(response)
                console.log(ollamaModelList.value);
                // 这里处理成功逻辑
              } else {
                // 服务器返回了非200状态码（如404、500等）
                ElMessage.error(`请求异常，状态码：${response.status}`);
              }
            })
            .catch(error => {
              // 捕获请求过程中的错误
              if (error.response) {
                // 请求已发出，服务器响应状态码非2xx（如4xx、5xx）
                ElMessage.error(`服务器错误：${error.response.status}`);
              } else if (error.request) {
                // 请求已发出，但未收到响应（如网络断开、服务器未运行）
                ElMessage.error("无法连接到Ollama，请检查是否已安装并运行！");
              } else {
                // 其他错误（如请求配置错误）
                ElMessage.error(`请求配置错误：${error.message}`);
              }
            });


      } else {
        ElMessage.error("请输入有效的 API 地址！");
      }
    };
    const getModelAiOlineList = () => {
      listModelAiOline({data: {}}).then(res => {
        aiList.value = res.data.data;
        console.log(aiList.value)
      })
    }
    getModelAiOlineList();
    connectOllama();


    // 删除一条在线模型
    const handleDeletedOne = (id) => {


      deleteModelAiOline({id: id}).then(res => {

        getModelAiOlineList();
      })

    }

    // 修改handleEditOne方法
    const handleEditOne = (id) => {
      isEdit.value = true;
      currentEditId.value = id;
      // 获取模型详情并填充表单
      getModelAiOline({ id: id }).then(res => {
        const modelData = res.data.data;
        olAiForm.value = {
          aiId: modelData.aiId,
          aiName: modelData.aiName,
          aiVersion: modelData.aiVersion,
          aiApi: modelData.aiApi,
          aiToken: modelData.aiToken,
          aiType: modelData.aiType.toString(),
        };
        openAddOnLineModelAi.value = true;
      });
    };

    // 在 setup() 中添加表单 ref
    const olAiFormRef = ref(null);

/// 修改提交方法
    const onSubmitOlAi = async () => {
      try {
        const valid = await olAiFormRef.value.validate(); // 使用 async/await 让表单验证异步执行
        if (valid) {
          const apiCall = isEdit.value
              ? updateModelAiOline({ data: olAiForm.value })
              : addModelAiOline({ data: olAiForm.value });

          const res = await apiCall; // 等待异步请求的返回

          if (res.data.code === 200) {
            ElMessage.success(isEdit.value ? "修改成功！" : "添加成功！");
            await getModelAiOlineList(); // 确保在获取数据之前操作已完成
            openAddOnLineModelAi.value = false;
            resetForm(); // 清空表单数据
          }
        }
      } catch (error) {
        ElMessage.error("操作失败，请稍后再试！");
        console.error(error); // 捕获并输出错误
      }
    };


    // 添加重置表单方法
    const resetForm = () => {
      olAiFormRef.value.resetFields();
      isEdit.value = false;
      currentEditId.value = null;
    };

    // 修改取消方法
    const onCancelOlAi = () => {
      openAddOnLineModelAi.value = false;
      resetForm();
    };
    // 气泡取消
    function onCancel() {

    }

    console.log("组件加载")


    return {
      isEdit,
      currentEditId,
      olAiFormRef,
      onCancelOlAi,
      onSubmitOlAi,
      formRules,
      olAiForm,
      apiUrl,
      aiList,
      clicked,
      ollamaModelList,
      handleDeletedOne,
      connectOllama,
      handleEditOne,
      openAddOnLineModelAi
    };
  },
};
</script>

<style scoped>
.model-management {
  padding: 20px;
  background-color: #f4f7fc;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.api-container {
  margin-bottom: 20px;
}

.api-input {
  width: 100%;
}

.connect-btn {
  width: 100%;
  height: 40px;
  border-radius: 5px;
  margin-top: 10px;
}

/* 标题样式 */
.table-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-top: 0;
  padding-left: 10px;

}

/* 表格外容器样式 */
.table-container {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 20px;
  position: relative;
  text-align: left;
  min-height: 300px;


}

/* 本地Ollama区域靠右 */
.local-model-col {
  text-align: right;
}

/* 表格内容区域 */
.model-table, .ai-table {
  width: 100%;
}

/* 删除按钮和操作按钮样式 */
.delete-btn, .ai-action-btn {
  margin-left: 10px;
}

/* AI 表格的列居中 */
.ai-table .el-table-column {
  text-align: center;
}
</style>
