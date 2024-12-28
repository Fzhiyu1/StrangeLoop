# 怪圈API需求



## eg：

###  **API 需求文档**

- **功能描述**：明确每个API的业务功能和使用场景。

- **用户角色**：API的使用者（前端用户、管理员、系统对接等）。

- 输入输出：

  - **请求 URL**：包括路径和请求方法（如GET、POST、PUT、DELETE）。

  - 请求参数：

    - | 参数名称 | 类型 | 是否必填 | 请求类型 | 说明 |
      | -------- | ---- | -------- | -------- | ---- |
      |          |      |          |          |      |

    - 示例：

      ```json
      {
        
      }
      ```

      

  - 响应数据：

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |
      |          |          |      |      |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data": {
          "userId": "12345",
          "username": "John",
          "roles": ["admin"]
        }
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



## 一、模型对话模块

### 1.1 对话记录

#### 1.11 对话记录列表

* **功能描述**：根据用户传入的参数，查询用户的对话记录以用来展示。传入的参数可多可少，动态根据传入的参数进行查询。涉及model_conversation表

* **用户角色**：用户。

* 输入输出：

  - **请求 URL**：GET /model/message/list

  - 请求参数：

    - | 参数名称         | 数据类型   | 是否必填 | 请求类型 | 说明                         |
      | ---------------- | ---------- | -------- | -------- | ---------------------------- |
      | title            | String(50) | false    | query    | 消息记录标题                 |
      | aiName           | String(50) | false    | query    | 发送模型                     |
      | createdTimeStart | date       | false    | query    | 开始创建时间（根据区间查询） |
      | createdTimeEnd   | date       | false    | query    | 结束创建时间（根据区间查询） |
      
    - 示例：
  
      ```json
      1、无参数请求
      {
        
      }
      2、少量参数请求
      {
          "aiName":"gpt-3.5-turbo-0125"
      }
      3、全部参数请求
      {
           "aiName":"gpt-3.5-turbo-0125",
          "title":"java巴拉巴拉",
          "createdTimeStart":"2024/12/18 09:37",
          "createdTimeStart":"2024/12/21 09:00"
      }
      ```
  
      
  
  - 响应数据：
  
    - | 参数名称 | 参数说明 | 类型       | 说明                 |
      | -------- | -------- | ---------- | -------------------- |
      | id       | 编号     | int        |                      |
      | userId   | 用户id   | int        |                      |
      | title    | 会话标题 | String(50) | 会话标题由ai总结生成 |
      | aiName   | 会话模型 | String(50) |                      |
  
    - 示例：
  
      ```json
      {
        "code": 200,
        "message": "Success",
        "data": [
        	{
        		"id":"1",
        		"userId":"1",
        		"title":"java类的解释"
        	},
        	{
        		"id":"2",
        		"userId":"1",
        		"title":"猫娘扮演"
        	},
        	{
        		"id":"3",
        		"userId":"1",
        		"title":"环境生成"
        	}
        ]
      }
      ```
  
  - 错误信息：
  
    - 400:参数不正确
    - 404:找不到用户



#### 1.12 删除对话记录

- **功能描述**：删除一个或者多个会话记录，注意传入的ids为一个可变参数。涉及model_conversation表

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：DELETE `/model/message/#{ids}`。

  - 请求参数：

    - | 参数名称 | 类型   | 是否必填 | 请求类型 | 说明                 |
      | -------- | ------ | -------- | -------- | -------------------- |
      | ids      | long[] | true     | path     | model_conversation表 |

    - 示例：`/model/message/delete?#{ids}`。

  - - 响应数据：

      - | 参数名称 | 参数说明 | 类型 | 说明 |
        | -------- | -------- | ---- | ---- |
        |          |          |      |      |
      
    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data": null
      }
      ```
    
  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 1.13 新增会话

- **功能描述**：新增一条会话。涉及model_conversation表

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：POST /model/message。

  - 请求参数：

    - | 参数名称 | 类型   | 是否必填 | 请求类型 | 说明       |
      | -------- | ------ | -------- | -------- | ---------- |
      | userId   | int    | true     | query    | 用户id     |
      | aiName   | string | false    | query    | 对话AI名称 |
      
    - 示例：`{"userId": "1","aiName":"gpt-3.5-turbo"}`。
  
  - 响应数据：
  
    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |
      |          |          |      |      |
  
    - 示例：
  
      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
      }
      ```
  
  - 错误信息：
  
    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。

#### 1.14 修改会话

- **功能描述**：修改一条会话。涉及model_conversation表

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：PUT /model/message 。

  - 请求参数：

    - | 参数名称 | 类型       | 是否必填 | 请求类型 | 说明                    |
      | -------- | ---------- | -------- | -------- | ----------------------- |
      | id       | int        | true     | query    | 编号 model_conversation |
      | userId   | int        | false    | query    | 用户id                  |
      | title    | String(50) | false    | query    | 会话标题                |
      | aiName   | String(50) | false    | query    | 发送模型                |

    - 示例：

      ```json
      {
        "userId":"1",
         "id":"1",
         "title":"java类型有哪些",
         "aiName":"gpt-3.5-turbo"
      }
      ```

    

    ⚪响应数据

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |
      |          |          |      |      |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 1.15 获取会话详情

- **功能描述**：获取一条会话的详情信息，包括所有的历史记录。涉及model_conversation表和model_message

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：GET /model/message/detail/ 。

  - 请求参数：

    - | 参数名称 | 类型 | 是否必填 | 请求类型 | 说明                    |
      | -------- | ---- | -------- | -------- | ----------------------- |
      | id       | int  | true     | path     | 编号 model_conversation |

    - 示例：

      ```json
      /model/message/detail/#{id}
      ```

    

    ⚪响应数据

    - | 参数名称         | 参数说明     | 类型       | 说明                                                         |
      | ---------------- | ------------ | ---------- | ------------------------------------------------------------ |
      | userId           | 用户id       | int        |                                                              |
      | title            | 会话标题     | String(50) |                                                              |
      | aiName           | 发送模型     | String(50) | 数据唯一                                                     |
      | id               | 编号         | int        |                                                              |
      | createdTime      | 创建时间     | Date       | 日期+时间                                                    |
      | modelMessageList | 消息记录集合 | List       | 返回消息记录里面conversation_id与主表相同的数据里面全部的参数。 |
      |                  |              |            |                                                              |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":{
            "id":"1",
            "aiName":"gpt-3.5-turbo",
            "userId":"1",
            "title":"生成了多个java程序",
            "createdTime":"2024/12/18 08:50",
            "modelMessageList":[
                {
                   "id":"1",
                   "role":"user",
                    "content":"生成一个冒泡排序",
                    "conversationId":"1"
                },
                 {
                   "id":"2",
                   "role":"assistant",
                    "content":"这是一个冒泡排序：...",
                    "conversationId":"1"
                },
                 {
                   "id":"3",
                   "role":"user",
                    "content":"生成一个快排",
                    "conversationId":"1"
                },
                 {
                   "id":"1",
                   "role":"assistant",
                    "content":"这是一个快排：....",
                    "conversationId":"1"
                },
            ]
        }
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



### 1.2 消息记录

#### 1.21 删除消息记录

- **功能描述**：删除一条或者多条消息记录。注意传入的ids为一个可变参数。

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：DELETE  /model/message/detail/ 。

  - 请求参数：

    - | 参数名称 | 类型   | 是否必填 | 请求类型 | 说明                                       |
      | -------- | ------ | -------- | -------- | ------------------------------------------ |
      | ids      | long[] | true     | path     | 编号 model_message ids表示多条数据或者一条 |

    - 示例：

      ```json
      /model/message/detail/#{ids}
      ```

    

    ⚪响应数据

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |
      |          |          |      |      |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 1.22 新增消息记录

- **功能描述**：新增一条消息记录。

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：POST /model/message/detail/ 。

  - 请求参数：

    - | 参数名称       | 类型                  | 是否必填 | 请求类型 | 说明                                    |
      | -------------- | --------------------- | -------- | -------- | --------------------------------------- |
      | conversationId | int                   | true     | query    | 主表会话表id                            |
      | role           | String(50)            | false    | query    | 发送角色（user/assistant） 可能之后会变 |
      | content        | String(text.length()) | false    | query    | 消息内容                                |
      | id             | int                   | false    | query    | 消息记录编号                            |

    - 示例：

      ```json
      {
          "conversationId":"1",
          "role":"user",
          "content":"什么是api需求文档"
      }
      ```

    

    ⚪响应数据

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |
      |          |          |      |      |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



## 二、模型管理

### 2.1 模型管理

#### 2.11 查询模型列表

- **功能描述**：查询可用的模型列表，并展示,关于model_info表

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：GET /model/manage/。

  - 请求参数：

    - | 参数名称         | 类型       | 是否必填 | 请求类型 | 说明                       |
      | ---------------- | ---------- | -------- | -------- | -------------------------- |
      | modelName        | string(50) | false    | query    | 角色名称                   |
      | aiName           | string     | false    | query    | 对话AI名称                 |
      | createdTimeStart | date       | false    | query    | 创建日期开始               |
      | createdTimeEnd   | date       | false    | query    | 创建日期结束               |
      | userId           | int        | false    | query    | 归属用户id                 |
      | linkType         | int        | false    | query    | 链接类型(本地：0，在线：1) |
      | disable          | int        | false    | query    | 禁用(1:为禁用，0：正常)    |

    - 示例：

      ```json
      
      {
          "modelName":"小智",
          "aiName":"qwen2.5",
          "createdTimeStart":"2024/12/19 08:52",
          "createdTimeEnd":"2024/12/20 08:52",
          "linkType":"0",
          "disable":"0"
      }
      ```


​    

  - 响应数据：

    - | 参数名称     | 参数说明       | 类型        | 说明                 |
      | ------------ | -------------- | ----------- | -------------------- |
      | modelId      | 模型编号       | int         |                      |
      | modelVersion | 模型版本       | String(50)  |                      |
      | modelName    | 角色名称       | String(50)  |                      |
      | modelFileId  | 角色设定       | int         |                      |
      | disable      | 禁用           | int         |                      |
      | description  | 模型描述       | String(500) |                      |
      | linkType     | 链接类型       | int         |                      |
      | aiName       | 基底模型名称   | String(50)  | 这个为基底模型的名称 |
      | userId       | 模型所属用户id | int         |                      |
      | updatedTime  | 更新时间       | date        |                      |
      | updated_by   | 更新人         | int         |                      |
      | createdTime  | 创建时间       | date        |                      |
      | createdBy    | 创建人         | int         |                      |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":[
            {
                "modelId":"1",
                "modelName":"小智",
                "modelVersion":"7b",
                "modelFileId":"1",
                "disable":"0",
                "description":"这个ai为智造领航系统服务",
                "linkType":"0",
                "aiName":"qwen2.5",
                "userId":"1",
                "updateTime":"2024/12/19 08:52",
                "updateBy":"1",
                "createdTime":"2024/12/18 08:52",
                "createdBy":"1"
            },
            {
                "modelId":"2",
                "modelName":"GPT-3.5",
                "modelVersion":"gpt-3.5-turbo-0125",
                "modelFileId":"2",
                "disable":"0",
                "description":"这是openAI的GPT-3.5",
                "linkType":"1",
                "aiName":"gpt-3.5-turbo-0125",
                "userId":"1",
                "updateTime":"2024/12/19 08:52",
                "updateBy":"1",
                "createdTime":"2024/12/18 08:52",
                "createdBy":"1"
            },
        ]
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 2.12 查询模型详情

- **功能描述**：查询一个模型的详细信息，涉及表model_info、model_ai_online、model_file、model_file_egmessage

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：GET /model/manage/。

  - 请求参数：

    - | 参数名称 | 类型 | 是否必填 | 请求类型 | 说明     |
      | -------- | ---- | -------- | -------- | -------- |
      | modelId  | int  | true     | path     | 模型编号 |

    - 示例：

      ```json
      /model/manage/#{modelId}
      ```

    

  - 响应数据：

    - | 参数说明                   | 参数名称                           | 类型                        | 说明                                                    |
      | -------------------------- | ---------------------------------- | --------------------------- | ------------------------------------------------------- |
      | 模型编号                   | modelId                            | int                         |                                                         |
      | 模型版本                   | modelVersion                       | String(50)                  |                                                         |
      | 角色名称                   | modelName                          | String(50)                  |                                                         |
      | 角色设定                   | modelFileId                        | int                         |                                                         |
      | 禁用                       | disable                            | int                         |                                                         |
      | 模型描述                   | description                        | String(500)                 |                                                         |
      | 链接类型                   | linkType                           | int                         |                                                         |
      | 基底模型名称               | aiName                             | String(50)                  | 这个为基底模型的名称                                    |
      | 模型所属用户id             | userId                             | int                         |                                                         |
      | 更新时间                   | updatedTime                        | date                        |                                                         |
      | 更新人                     | updated_by                         | int                         |                                                         |
      | 创建时间                   | createdTime                        | date                        |                                                         |
      | 创建人                     | createdBy                          | int                         |                                                         |
      | 关联在线模型               | modelAiOnline                      | modelAiOnline               | 当linkeType==1时，才链接这个对象，所属表model_ai_online |
      | ai版本                     | modelAiOnline: aiVersion           | String(50)                  |                                                         |
      | ai名称                     | modelAiOnline: aiName              | String(50)                  | ai名称在表为唯一                                        |
      | 编号                       | modelAiOnline: aiId                | int                         |                                                         |
      | ai类型                     | modelAiOnline: aiType              | int                         | llm:1,chat:2,image:3                                    |
      | 认证token                  | modelAiOnline：aiToken             | String(500)                 |                                                         |
      | 请求超时时间               | modelAiOnline：timeout             | int                         |                                                         |
      | api接口                    | modelAiOnline：aiApi               | String(500)                 |                                                         |
      | 创建者                     | modelAiOnline：createdBy           | int                         |                                                         |
      | 创建时间                   | modelAiOnline：createdTime         | date                        |                                                         |
      | 更新人                     | modelAiOnline：updatedBy           | int                         |                                                         |
      | 更新时间                   | modelAiOnline：updatedTime         | date                        |                                                         |
      | 模型角色设定详情           | modelFile                          | modelFile                   | 根据modelFileId链接model_file表                         |
      | 系统提示词                 | modelFile: system                  | String(text)                | 长度为数据库能储存的上线                                |
      | 角色设定id                 | modelFile: modelFileId             | int                         |                                                         |
      | mirostat算法设定           | modelFile: mirostat                | int                         | 详情见数据库备注                                        |
      | mirostat算法学习速率       | modelFile: mirostatEat             | double                      | 详情见数据库备注                                        |
      | mirostat算法文本一致性作用 | modelFile: mirostatTau             | double                      | 详情见数据库备注                                        |
      | 模型上下文联系长度         | modelFile: numCtx                  | int                         |                                                         |
      | 重复生成长度作用           | modelFile: repeatLastN             | int                         | 详情见数据库备注                                        |
      | 重复token惩罚力度作用      | modelFile: repeatPenalty           | double                      | 详情见数据库备注                                        |
      | 生成随机性作用             | modelFile: temperature             | double                      | 详情见数据库备注                                        |
      | 随机数种子                 | modelFile: seed                    | int                         |                                                         |
      | 停止触发序列               | modelFile: stop                    | String(500)                 |                                                         |
      | 减少不常见token            | modelFile: tfsZ                    | double                      | 详情见数据库备注                                        |
      | 设置生成的最大token数      | modelFile: numPredict              | int                         | 详情见数据库备注                                        |
      | 控制采样高概率阈值         | modelFile: topP                    | double                      | 详情见数据库备注                                        |
      | topP的替代参数             | modelFile: minP                    | double                      | 详情见数据库备注                                        |
      | 模型角色设定实例信息       | modelFileEmessageList              | List<modelFileEmessageList> | 一个数组                                                |
      | 信息编号                   | modelFileEmessageList: messageId   | int                         |                                                         |
      | 用户消息                   | modelFileEmessageList: userMessage | String(text)                |                                                         |
      | 模型回复                   | modelFileEmessageList:modelMessage | String(text)                |                                                         |
      | 主表modelFile的链接id      | modelFileEmessageList: modelFileId | int                         |                                                         |
      
    - 示例：
  
      ```json
      {
        "code": 200,
        "message": "Success",
        "data":
            {
                "modelId":"1",
                "modelName":"小智",
                "modelVersion":"7b",
                "modelFileId":"1",
                "disable":"0",
                "description":"这个ai为智造领航系统服务",
                "linkType":"0",
                "aiName":"qwen2.5",
                "userId":"1",
                "updateTime":"2024/12/19 08:52",
                "updateBy":"1",
                "createdTime":"2024/12/18 08:52",
                "createdBy":"1",
                "modelFile":{
                    "system":"你是一个智造领航系统中的智能AI助手小智",
                    "modelFileId":"1",
                    "mirostat":"2",
                    "mirostatEat":"0.1",
                    "mirostatTau":"5.0",
                    "numCtx":"2568",
                    "repeatLastN":"0",
                    "repeatPenalty":"1.1",
                    "temperature":"0.5",
                    "seed":"1734674529",
                    "stop":null,
                    "tfsZ":"1.5",
                    "numPredict":"258",
                    "topP":"0.6",
                    "minP":"0.6",
                    "modelFileId":"1",
                    "modelFileEmessageList":[
                        {
                            "messageId":"1",
                            "userMessage":"打开销售管理页面",
                            "modelMessage":"好的我已经打开了页面#/mes/detail#",
                            "modelFileId":"1",
                        },{
                            "messageId":"2",
                            "userMessage":"什么是智造领航",
                            "modelMessage":"智造领航系统是.......",
                            "modelFileId":"1"
                        }
                        
                       
                    ]
                  
                }
            }
        
      }
      ```
  
    - 
  
      ```json
      {
        "code": 200,
        "message": "Success",
        "data":
            {
                "modelId":"2",
                "modelName":"GPT-3.5",
                "modelVersion":"gpt-3.5-turbo-0125",
                "modelFileId":"2",
                "disable":"0",
                "description":"这是openAI的GPT-3.5",
                "linkType":"1",
                "aiName":"gpt-3.5-turbo-0125",
                "userId":"1",
                "updateTime":"2024/12/19 08:52",
                "updateBy":"1",
                "createdTime":"2024/12/18 08:52",
                "createdBy":"1",
                "modelAiOnline":{
                    "aiVersion":"gpt-3.5-turbo-0125",
                    "aiName":"gpt-3.5-turbo-0125",
                    "aiId":"1",
                    "aiType":"1",
                    "aiApi":"https://api.bianxie.ai/v1/chat/completions",
                    "aiToken":"sk-qxoWrNw4JDPobNAZxkIV11rXP8cFFTtZa4kUbYthQAhxdJJq",
                    "timeOut":"500",
                    "createdBy":"1",
                    "createdTime":"2024/12/20 14:15",
                    "updatedTime":"2024/12/21 14:15",
                    "updatedBy":"1"
            		
                },
                "modelFile":{
                    "system":null,
                    "modelFileId":null,
                    "mirostat":null,
                    "mirostatEat":null,
                    "mirostatTau":null,
                    "numCtx":null,
                    "repeatLastN":null,
                    "repeatPenalty":null,
                    "temperature":null,
                    "seed":null,
                    "stop":null,
                    "tfsZ":null,
                    "numPredict":null,
                    "topP":null,
                    "minP":null,
                    "modelFileId":"1",
                    "modelFileEmessageList":[]
                  
                }
            }
        
      }
      ```
  
    - 
  
  - 错误信息：
  
    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 2.13 新增模型

- **功能描述**：用户增加一个模型，并自动创建对应的在线模型和模型角色设定详情空数据

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：POST /model/manage

  - 请求参数：

    - | 参数名称    | 类型        | 是否必填 | 请求类型 | 说明                       |
      | ----------- | ----------- | -------- | -------- | -------------------------- |
      | modelName   | string(50)  | ture     | query    | 角色名称                   |
      | aiName      | string      | true     | query    | 对话AI名称                 |
      | linkType    | int         | false    | query    | 链接类型(本地：0，在线：1) |
      | description | String(500) | false    | query    | 对模型的描述               |

    - 示例：

      ```json
      {
          "modelName":"小智",
          "aiName":"qwen2.5",
          "linkType":"0",
          "description":"AI小智是智造领航系统中的智能助手。"
          
      }
      ```


​    

  - 响应数据：

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 2.14 修改模型

- **功能描述**：用户对一个模型进行修改

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：PUT /model/manage

  - 请求参数：

    - | 参数名称    | 类型        | 是否必填 | 请求类型 | 说明                       |
      | ----------- | ----------- | -------- | -------- | -------------------------- |
      | modelName   | string(50)  | false    | query    | 角色名称                   |
      | aiName      | string      | false    | query    | 对话AI名称                 |
      | linkType    | int         | false    | query    | 链接类型(本地：0，在线：1) |
      | disable     | int         | false    | query    | 禁用(1:为禁用，0：正常)    |
      | description | String(500) | false    | query    | 对模型的描述               |
      | modelId     | int         | true     | query    | 修改模型的id               |

    - 示例：

      ```json
      {
          "modelName":"小智",
          "aiName":"qwen2.5",
          "linkType":"0",
          "disable":"0",
          "description":"就这样",
          "modelId":"1"
          
      }
      ```


​    

  - 响应数据：

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 2.15 删除模型

- **功能描述**：用户根据id删除模型信息,注意删除后将删除其关联的model_file和model_file_emessage表,涉及表model_info、model_file、model_file_egmessage

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：DELETE /model/manage/

  - 请求参数：

    - | 参数名称 | 类型 | 是否必填 | 请求类型 | 说明         |
      | -------- | ---- | -------- | -------- | ------------ |
      | aiId     | int  | false    | path     | 模型角色编号 |

    - 示例：

      ```json
      /model/manage/#{aiId}
      ```


​    

  - 响应数据：

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
        
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。





### 2.2在线模型管理

#### 2.21 查询在线模型列表

- **功能描述**：用户查询在线模型列表,参数可变,涉及表model_ai_online

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：PUT /model/ol/manage

  - 请求参数：

    - | 参数名称         | 类型       | 是否必填 | 请求类型 | 说明                                 |
      | ---------------- | ---------- | -------- | -------- | ------------------------------------ |
      | aiId             | int        | false    | query    | ai在线模型编号                       |
      | aiName           | String(50) | false    | query    | ai在线模型名称                       |
      | aiVersion        | String(50) | false    | query    | ai在线模型版本                       |
      | aiType           | int        | false    | query    | ai在线模型类型(llm 1,chat 2,image 3) |
      | createdTimeStart | data       | false    | query    | ai在线模型创建日期开始查询区间       |
      | createdTimeEnd   | data       | false    | query    | ai在线模型创建日期结束查询区间       |

    - 示例：

      ```json
      {
        	"aiId":"1",
          "aiName":"gpt-3.5-turbo-0125",
          "aiVersion":"gpt-3.5-turbo-0125",
          "aiType":"1",
          "createdTimeStart":"2024/12/28 16:09",
          "createdTimeStart":"2024/12/29 16:09",
      }
      ```


​    

  - 响应数据：

    - | 参数名称    | 参数说明             | 类型        | 说明                            |
      | ----------- | -------------------- | ----------- | ------------------------------- |
      | aiId        | ai在线模型编号       | int         |                                 |
      | aiName      | ai在线模型名称       | String(50)  |                                 |
      | aiVersion   | ai在线模型型号       | String(50)  |                                 |
      | aiType      | ai类型               | int         | (llm 1,chat 2,image 3)          |
      | aiToken     | 在线模型token        | String(500) |                                 |
      | aiApi       | 在线模型地址         | String(500) |                                 |
      | timeOut     | 在线模型最大请求时间 | int         | 设置后 超过请求时间时将停止请求 |
      | createdBy   | 创建者               | int         |                                 |
      | createdTime | 创建时间             | date        |                                 |
      | updatedBy   | 更新人               | int         |                                 |
      | updatedTime | 更新时间             | date        |                                 |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":[
            {
                "aiId":"1",
                "aiName":"gpt-3.5-turbo-0125",
                "aiVersion":"gpt-3.5-turbo-0125",
                "aiType":"1",
                "aiToken":"sk-qxoWrNw4JDPobNAZxkIV11rXP8cFFTtZa4kUbYthQAhxdJJq",
                "aiApi":"https://api.bianxie.ai/v1/chat/completions",
                "timeOut":null,
                "createdBy":"1",
                "updatedBy":"1",
                "createdTime":"2024/12/28 16:09",
                "updatedTime":"2024/12/29 16:09"
            },
            {
                "aiId":"2",
                "aiName":"gpt-4o-turbo-0125",
                "aiVersion":"gpt-4o-turbo-0125",
                "aiType":"1",
                "aiToken":"sk-qxoWrNw4JDPobNAZxkIV11rXP8cFFTtZa4kUbYthQAhxdJJq",
                "aiApi":"https://api.bianxie.ai/v1/chat/completions",
                "timeOut":null,
                "createdBy":"1",
                "updatedBy":"1",
                "createdTime":"2024/12/28 16:09",
                "updatedTime":"2024/12/29 16:09"
            },
        ]
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 2.22 查询在线模型详情

- **功能描述**：用户根据id查询在线模型详情信息,涉及表model_ai_online

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：GET /model/ol/manage/

  - 请求参数：

    - | 参数名称 | 类型 | 是否必填 | 请求类型 | 说明           |
      | -------- | ---- | -------- | -------- | -------------- |
      | aiId     | int  | false    | path     | ai在线模型编号 |

    - 示例：

      ```json
      /model/ol/manage/#{aiId}
      ```


​    

  - 响应数据：

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":
            {
                "aiId":"1",
                "aiName":"gpt-3.5-turbo-0125",
                "aiVersion":"gpt-3.5-turbo-0125",
                "aiType":"1",
                "aiToken":"sk-qxoWrNw4JDPobNAZxkIV11rXP8cFFTtZa4kUbYthQAhxdJJq",
                "aiApi":"https://api.bianxie.ai/v1/chat/completions",
                "timeOut":null,
                "createdBy":"1",
                "updatedBy":"1",
                "createdTime":"2024/12/28 16:09",
                "updatedTime":"2024/12/29 16:09"
            }
        
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 2.23 删除在线模型

- **功能描述**：用户根据id删除在线模型信息,涉及表model_ai_online

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：DELETE /model/ol/manage/

  - 请求参数：

    - | 参数名称 | 类型 | 是否必填 | 请求类型 | 说明           |
      | -------- | ---- | -------- | -------- | -------------- |
      | aiId     | int  | false    | path     | ai在线模型编号 |

    - 示例：

      ```json
      /model/ol/manage/#{aiId}
      ```


​    

  - 响应数据：

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
        
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 2.24 修改在线模型

- **功能描述**：用户根据id修改在线模型详情信息,涉及表model_ai_online

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：PUT /model/ol/manage/

  - 请求参数：

    - | 参数名称  | 类型        | 是否必填 | 请求类型 | 说明                   |
      | --------- | ----------- | -------- | -------- | ---------------------- |
      | aiId      | int         | true     | query    | ai在线模型编号         |
      | aiName    | String(50)  | false    | query    | ai在线模型名称         |
      | aiVersion | String(50)  | false    | query    | ai在线模型版本         |
      | aiToken   | String(500) | false    | query    | ai在线模型token        |
      | aiApi     | String(50)  | false    | query    | ai在线模型api接口      |
      | aiType    | int         | false    | query    | ai在线模型类型         |
      | timeout   | int         | false    | query    | ai在线模型请求超时时间 |

      ​	

    - 示例：

      ```json
      {
          "aiId":"1",
          "aiName":"gpt-3.5-turbo-0125",
          "aiVersion":"gpt-3.5-turbo-0125",
          "aiToken":"sk-qxoWrNw4JDPobNAZxkIV11rXP8cFFTtZa4kUbYthQAhxdJJq",
          "aiApi":"https://api.bianxie.ai/v1/chat/completions",
          "aiType":"1",
          "timeout":"5000"
      }
      ```


​    

  - 响应数据：

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
        
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



#### 2.25 新增在线模型

- **功能描述**：用户增加一条在线模型详情信息,涉及表model_ai_online

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：POST /model/ol/manage/

  - 请求参数：

    - | 参数名称  | 类型        | 是否必填 | 请求类型 | 说明                   |
      | --------- | ----------- | -------- | -------- | ---------------------- |
      | aiId      | int         | true     | query    | ai在线模型编号         |
      | aiName    | String(50)  | false    | query    | ai在线模型名称         |
      | aiVersion | String(50)  | false    | query    | ai在线模型版本         |
      | aiToken   | String(500) | false    | query    | ai在线模型token        |
      | aiApi     | String(50)  | false    | query    | ai在线模型api接口      |
      | aiType    | int         | false    | query    | ai在线模型类型         |
      | timeout   | int         | false    | query    | ai在线模型请求超时时间 |

      ​	

    - 示例：

      ```json
      {
          "aiId":"1",
          "aiName":"gpt-3.5-turbo-0125",
          "aiVersion":"gpt-3.5-turbo-0125",
          "aiToken":"sk-qxoWrNw4JDPobNAZxkIV11rXP8cFFTtZa4kUbYthQAhxdJJq",
          "aiApi":"https://api.bianxie.ai/v1/chat/completions",
          "aiType":"1",
          "timeout":"5000"
      }
      ```


​    

  - 响应数据：

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
        
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。



### 2.3模型角色设定详情管理

#### 2.31 修改模型角色设定详情

- **功能描述**：用户根据id修改在线模型详情信息,注意传入的modelFileEmessageList是model_file表的子表model_file_emessage的数据 涉及表model_file、model_file_emessage

- **用户角色**：用户。

- 输入输出：

  - **请求 URL**：PUT /model/manage/detail

  - 请求参数：

    - | 模型角色设定详情           | modelFile                          | modelFile                   | 根据modelFileId链接model_file表 |
      | -------------------------- | ---------------------------------- | --------------------------- | ------------------------------- |
      | 系统提示词                 | modelFile: system                  | String(text)                | 长度为数据库能储存的上线        |
      | 角色设定id                 | modelFile: modelFileId             | int                         |                                 |
      | mirostat算法设定           | modelFile: mirostat                | int                         | 详情见数据库备注                |
      | mirostat算法学习速率       | modelFile: mirostatEat             | double                      | 详情见数据库备注                |
      | mirostat算法文本一致性作用 | modelFile: mirostatTau             | double                      | 详情见数据库备注                |
      | 模型上下文联系长度         | modelFile: numCtx                  | int                         |                                 |
      | 重复生成长度作用           | modelFile: repeatLastN             | int                         | 详情见数据库备注                |
      | 重复token惩罚力度作用      | modelFile: repeatPenalty           | double                      | 详情见数据库备注                |
      | 生成随机性作用             | modelFile: temperature             | double                      | 详情见数据库备注                |
      | 随机数种子                 | modelFile: seed                    | int                         |                                 |
      | 停止触发序列               | modelFile: stop                    | String(500)                 |                                 |
      | 减少不常见token            | modelFile: tfsZ                    | double                      | 详情见数据库备注                |
      | 设置生成的最大token数      | modelFile: numPredict              | int                         | 详情见数据库备注                |
      | 控制采样高概率阈值         | modelFile: topP                    | double                      | 详情见数据库备注                |
      | topP的替代参数             | modelFile: minP                    | double                      | 详情见数据库备注                |
      | 模型角色设定实例信息       | modelFileEmessageList              | List<modelFileEmessageList> | 一个数组                        |
      | 信息编号                   | modelFileEmessageList: messageId   | int                         |                                 |
      | 用户消息                   | modelFileEmessageList: userMessage | String(text)                |                                 |
      | 模型回复                   | modelFileEmessageList:modelMessage | String(text)                |                                 |
      | 主表modelFile的链接id      | modelFileEmessageList: modelFileId | int                         |                                 |

      ​	

    - 示例：

      ```json
      {
                    "system":"你是一个智造领航系统中的智能AI助手小智",
                    "modelFileId":"1",
                    "mirostat":"2",
                    "mirostatEat":"0.1",
                    "mirostatTau":"5.0",
                    "numCtx":"2568",
                    "repeatLastN":"0",
                    "repeatPenalty":"1.1",
                    "temperature":"0.5",
                    "seed":"1734674529",
                    "stop":null,
                    "tfsZ":"1.5",
                    "numPredict":"258",
                    "topP":"0.6",
                    "minP":"0.6",
                    "modelFileId":"1",
                    "modelFileEmessageList":[
                        {
                            "messageId":"1",
                            "userMessage":"打开销售管理页面",
                            "modelMessage":"好的我已经打开了页面#/mes/detail#",
                            "modelFileId":"1",
                        },{
                            "messageId":"2",
                            "userMessage":"什么是智造领航",
                            "modelMessage":"智造领航系统是.......",
                            "modelFileId":"1"
                        }
                        
                       
                    ]
          
      }
      ```


​    

  - 响应数据：

    - | 参数名称 | 参数说明 | 类型 | 说明 |
      | -------- | -------- | ---- | ---- |

    - 示例：

      ```json
      {
        "code": 200,
        "message": "Success",
        "data":null
        
      }
      ```

  - 错误信息：

    - 常见错误码及其含义，例如：`400-参数错误`，`401-未授权`。













