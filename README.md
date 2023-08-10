# ✨FlashPipi's Blog 开发文档

## 数据库设计

- 用户信息表( user )

| 字段     | 类型         | 说明                                           |
| -------- | ------------ | ---------------------------------------------- |
| id       | INT          | 用户ID，自增，主键                             |
| name     | VARCHAR(10)  | 用户姓名                                       |
| email    | VARCHAR(50)  | 邮箱                                           |
| password | VHARCHAR(20) | 用户密码，不明文存储，存储的内容为加密后的内容 |
| avatar   | VARCHAR(500) | 用户头像                                       |



- 博客信息表( blog )

| 字段         | 类型         | 说明               |
| ------------ | ------------ | ------------------ |
| id           | INT          | 主键，自增         |
| title        | VARCHAR(20)  | 博客标题           |
| description  | VARCHAR(50)  | 博客的简要描述     |
| image_path   | VARCHAR(500) | 博客的背景图片路径 |
| publish_time | DATE         | 发布时间           |
| update_time  | DATE         | 更新时间           |
| category     | VARCHAR(10)  | 分类               |
| collection   | VARCHAR(100) | 合集               |
| views_count  | INT          | 博客的阅读数量     |
| content      | LONGTEXT     | 文章内容           |
| sort_id      | INT          | 排序编号           |



- 网站其他信息( information )

| 字段            | 类型         | 说明             |
| --------------- | ------------ | ---------------- |
| id              | INT          | 主键             |
| background_path | VARCHAR(500) | 网站背景图片地址 |
| website         | VARCHAR(500) | 网站域名         |
| update_time     | DATE         | 最后一次更新时间 |
| views_count     | INT          | 本网站总访问量   |
| notice          | LONGTEXT     | 网站公告栏内容   |
| author_id       | INT          | 管理员用户id     |



- 验证码

| 字段  | 类型        | 说明   |
| ----- | ----------- | ------ |
| id    | INT         | 主键   |
| email | VARCHAR(50) | 邮箱   |
| code  | INT         | 验证码 |



## 接口设计


### Ⅰ、 UserController

#### 1. 获取网站作者信息

- 名称：/user/getUserInfo
- 描述：获取用户信息
- 请求方式：GET
- 请求参数：

| 字段  | 说明         | 类型   | 是否必须 | 备注 |
| ----- | ------------ | ------ | -------- | ---- |
| email | 用户邮箱地址 | String | 是       |      |

- 请求参数示例

``` json
{
    "email": "userEmail@xxx.com"
}
```

- 响应示例：

``` json
{
    "id": 1585469648,
    "name": "闪光皮皮",
    "email": "userEmail@163.com",
    "password": "e1qscs3d499659abbfr2e81s5931883e",
    "avatar": null
}
```



#### 2. 用户注册

- 名称：/user/register
- 描述：获取用户信息
- 请求方式：POST
- 请求参数：

| 字段     | 说明         | 类型   | 是否必须 | 备注                                                         |
| -------- | ------------ | ------ | -------- | ------------------------------------------------------------ |
| email    | 用户邮箱地址 | String | 是       |                                                              |
| password | 用户密码     | String | 是       | 采用MD5对用户密码进行处理和保存至数据库，该算法可以确保包括服务器拥有者在内的所有人都无法知道用户真正的密码是什么，极大提高了安全性。 |
| code     | 验证码       | String | 是       | 注册时，所需的验证码，用于验证邮箱真伪                       |

- 请求参数示例

``` json
{
    "email": "userEmail@xxx.com",
    "password": "123456",
    "code": "123456"
}
```

- 响应示例：

``` json
// 注册成功时
{
	1
}

// 邮箱已被注册时
{
    -1
}

// 验证码错误
{
    -2
}
```



#### 3. 用户登录

- 名称：/user/login
- 描述：用户登录
- 请求方式：POST
- 请求参数：

| 字段     | 说明         | 类型   | 是否必须 | 备注 |
| -------- | ------------ | ------ | -------- | ---- |
| email    | 用户邮箱地址 | String | 是       |      |
| password | 用户密码     | String | 是       |      |

- 请求参数示例

``` json
{
    "email": "userEmail@xxx.com",
    "password": "123456"
}
```

- 响应示例：

``` json
// 登录成功
{
    1
}

// 密码错误
{
    2
}

// 邮箱尚未注册
{
    3
}
```



#### 4. 修改用户密码

- 名称：/user/modifyPassword
- 描述：修改用户密码
- 请求方式：POST
- 请求参数：

| 字段        | 说明         | 类型   | 是否必须 | 备注 |
| ----------- | ------------ | ------ | -------- | ---- |
| email       | 用户邮箱地址 | String | 是       |      |
| newPassword | 用户新密码   | String | 是       |      |
| code        | 验证码       | String | 是       |      |

- 请求参数示例

``` json
{
    "email": "userEmail@xxx.com",
    "newPassword": "123456",
    "code": "123456"
}
```

- 响应示例：

``` json
// 修改成功
{
    1
}

// 验证码错误
{
    -1
}
```



#### 5. 修改用户信息

- 名称：/user/modifyInfo
- 描述：修改用户信息
- 请求方式：POST
- 请求参数：

| 字段   | 说明         | 类型   | 是否必须 | 备注 |
| ------ | ------------ | ------ | -------- | ---- |
| avatar | 用户头像地址 | String | 否       |      |
| name   | 用户名称     | String | 否       |      |
| email  | 用户邮箱     | String | 是       |      |

- 请求参数示例

``` json
{
    "email": "userEmail@xxx.com",
}
```

- 响应示例：

``` json
// 修改成功
{
    1
}
```

### Ⅱ、RandomCodeController

#### 1. 获取验证码

- 名称：/code/getCode
- 描述：获取验证码
- 请求方式：GET
- 请求参数：

| 字段     | 说明         | 类型   | 是否必须 | 备注 |
| -------- | ------------ | ------ | -------- | ---- |
| email    | 用户邮箱地址 | String | 是       |      |
| password | 用户密码     | String | 是       |      |

- 请求参数示例

``` json
{
    "email": "userEmail@xxx.com",
    "password": "123456"
}
```

- 响应示例：

``` json
// 登录成功
{
    1
}

// 密码错误
{
    2
}

// 邮箱尚未注册
{
    3
}
```



### Ⅲ、InformationController

#### 1. 获取小站资讯

- 名称：/info/getInfo
- 描述：获取小站资讯
- 请求方式：GET
- 请求参数：无

- 响应示例：

``` json
{
    "id": 1,
    "backgroundPath": null,
    "website": "http://flashpipi.com",
    "notice": "本网站目前还在继续开发完善中，当前网站中还有许多问题待解决，尽情谅解。若有BUG请及时联系我，不胜感激~",
    "viewsCount": 172,
    "createTime": "2023-07-03"
}
```



### Ⅳ、BlogController

#### 1. 获取验证码

- 名称：/blog/getAllBolgs
- 描述：获取博客列表
- 请求方式：GET
- 请求参数：

| 字段 | 说明   | 类型 | 是否必须 | 备注 |
| ---- | ------ | ---- | -------- | ---- |
| page | 页码数 | INT  | 是       |      |

- 请求参数示例

``` json
{
    "page": 1
}
```

- 响应示例：

``` json
[
    {
        "id": 1651974146,
        "title": "图像分类之KNN算法",
        "description": "通过KNN算法实现对图像进行分类",
        "imagePath": "https://img1.baidu.com/it/u=2156029344,4282783762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800https://img1.baidu.com/it/u=2156029344,4282783762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "publishTime": "2023-07-21",
        "updateTime": "2023-07-21",
        "category": "深度学习",
        "collection": null,
        "viewsCount": 8,
        "content": null,
        "sortId": null
    },
    {
        "id": -503136255,
        "title": "Numpy使用简述",
        "description": "Numpy一种开源的数值计算扩展",
        "imagePath": "https://img1.baidu.com/it/u=2396404004,3078543528&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "publishTime": "2023-07-17",
        "updateTime": "2023-07-17",
        "category": "深度学习",
        "collection": null,
        "viewsCount": 34,
        "content": null,
        "sortId": null
    },
    {
        "id": 8568834,
        "title": "Linux系统上安装mysql数据库",
        "description": "在Linux系统上安装MySQL数据库",
        "imagePath": "https://img1.baidu.com/it/u=94904965,608097738&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500",
        "publishTime": "2023-07-17",
        "updateTime": "2023-07-17",
        "category": "服务器部署",
        "collection": null,
        "viewsCount": 21,
        "content": null,
        "sortId": null
    },
    {
        "id": 1401077761,
        "title": "Linux系统上部署Vue项目",
        "description": "使用Nignx在服务器上部署Vue项目",
        "imagePath": "https://img0.baidu.com/it/u=3415509072,3592166175&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500",
        "publishTime": "2023-07-17",
        "updateTime": "2023-07-17",
        "category": "服务器部署",
        "collection": null,
        "viewsCount": 18,
        "content": null,
        "sortId": null
    }
]
```



#### 2. 根据id查询博客详细信息

- 名称：/blog/getBlogById
- 描述：根据id查询博客详细信息
- 请求方式：GET
- 请求参数：

| 字段 | 说明 | 类型 | 是否必须 | 备注 |
| ---- | ---- | ---- | -------- | ---- |
| id   | id   | INT  | 是       |      |

- 请求参数示例

``` json
{
	"id": 1401077761
}
```

- 响应示例：

``` json
{
    "id": 1401077761,
    "title": "Linux系统上部署Vue项目",
    "description": "使用Nignx在服务器上部署Vue项目",
    "imagePath": "https://img0.baidu.com/it/u=3415509072,3592166175&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500",
    "publishTime": "2023-07-17",
    "updateTime": "2023-07-17",
    "category": "服务器部署",
    "collection": "",
    "viewsCount": 19,
    "content": "# Linux系统上部署Vue项目\n\n## 1. 安装nginx\n\n1. 下载nginx\n\n   `wget http://nginx.org/download/nginx-1.24.0.tar.gz`\n\n2. 解压缩\n\n   `tar -zxvf nginx-1.24.0.tar.gz`\n\n  3. 将文件放到目录`/usr/local/nginx`目录下 注意需要使用sudo前缀\n\n  4. 安装前的准备\n\n- 安装gcc\n\n  `sudo apt-get install gcc`\n\n- 安装PCER库\n\n  `sudo apt-get install pcre pcre-devel`\n\n- 安装zlib库\n\n  `sudo apt-get install zlib zlib-devel`\n\n- 安装OpenSSL\n\n  `sudo apt-get install openssl openssl-devel`\n\n5. 编译\n\n- 若不需要ssl证书直接执行\n\n  `./configure`\n\n- 若需要ssl证书需要执行\n\n  `./configure --with-http_stub_status_module --with-http_ssl_module`\n\n6. 执行make指令\n\n   `make`\n\n   `sudo make install`\n\n7. 退出到上一级目录 进入sbin文件夹下运行`./nginx`\n\n8. 进行配置让其指向前端文件夹\n\n   路径为: `conf/nginx.conf`\n\n   <img src=\"https://cdn.jsdelivr.net/gh/guoxxxxxxx/Pic-Go@main/img/202307162129031.png\" alt=\"image-20230716212936970\" style=\"zoom:50%;\" />\n\n​\t修改该处到前端界面所在处\n\n9. 重启nignx\n\n   `sudo ./nignx -s reload`\n\n",
    "sortId": null
}
```



#### 3. 获取博客数量

- 名称：/blog/getBlogsCount
- 描述：获取博客数量
- 请求方式：GET
- 请求参数：无
- 响应示例：

``` json
{
    4
}
```



#### 4. 根据日期排序选取最近的5篇文章

- 名称：/blog/getBlogsCount
- 描述：根据日期排序选取最近的5篇文章
- 请求方式：GET
- 请求参数：

| 字段 | 说明     | 类型 | 是否必须 | 备注 |
| ---- | -------- | ---- | -------- | ---- |
| page | 页码     | INT  | 是       |      |
| size | 请求数量 | INT  | 是       |      |

- 请求参数示例

```json
{
    "page": 1,
    "size": 1
}
```

- 响应示例：

```json
[
    {
        "id": 1651974146,
        "title": "图像分类之KNN算法",
        "description": "通过KNN算法实现对图像进行分类",
        "imagePath": "https://img1.baidu.com/it/u=2156029344,4282783762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800https://img1.baidu.com/it/u=2156029344,4282783762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "publishTime": "2023-07-21",
        "updateTime": "2023-07-21",
        "category": "深度学习",
        "collection": "《深度学习与图像识别原理与实践》",
        "viewsCount": 8,
        "content": "# 图像分类之KNN算法\n\n\n\n## 一、KNN的理论基础与实现\n\n### 1. 理论知识\n\n​\tKNN被翻译为最近邻算法，顾名思义，找到最近的k个邻居，在前k个最近样本中选择最近的占比最高的类别作为预测类别。如下图所示。...",
        "sortId": 2
    }
]
```



#### 5. 查询博客种类 及其对应的数量 分页查询 当页码为-1时 查询全部记录

- 名称：/blog/getBlogsCategoryList
- 描述：查询博客种类 及其对应的数量 分页查询 当页码为-1时 查询全部记录
- 请求方式：GET
- 请求参数：

| 字段 | 说明 | 类型 | 是否必须 | 备注 |
| ---- | ---- | ---- | -------- | ---- |
| page | 页码 | INT  | 是       |      |

- 请求参数示例

``` json
{
	page: "1"
}
```

- 响应示例：

``` json
[
    {
        "count": 2,
        "category": "深度学习"
    },
    {
        "count": 2,
        "category": "服务器部署"
    }
]
```



#### 6. 查询博客种类数量

- 名称：/blog/
- 描述：查询博客种类数量
- 请求方式：GET
- 请求参数：无
- 响应示例：

```json
{
    2
}
```



#### 7. 查询右侧卡片信息

- 名称：/blog/getCardInformation
- 描述：查询右侧卡片信息
- 请求方式：GET
- 请求参数：无
- 响应示例：

```json
{
    "blogCount": 4,
    "allViews": [
        {
            "sum": 82
        }
    ],
    "categoryCount": 2,
    "collectionCount": 2
}
```



#### 8. 分类查询博客

- 名称：/blog/getBlogsByCategory
- 描述：分类查询博客
- 请求方式：GET
- 请求参数：

| 字段        | 说明           | 类型    | 是否必须 | 备注 |
| ----------- | -------------- | ------- | -------- | ---- |
| category    | 类别           | String  | 是       |      |
| pageSize    | 页面大小       | INT     | 是       |      |
| currentPage | 当前显示的页面 | INT     | 是       |      |
| isSortDesc  | 是否降序排列   | Boolean | 是       |      |

- 请求参数示例

```json
{
    "category": "深度学习",
    "pageSize": 3,
    "currentPage": 1,
    "isSortDesc": true
}
```

- 响应示例：

```json
[
    {
        "id": 1651974146,
        "title": "图像分类之KNN算法",
        "description": "通过KNN算法实现对图像进行分类",
        "imagePath": "https://img1.baidu.com/it/u=2156029344,4282783762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800https://img1.baidu.com/it/u=2156029344,4282783762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "publishTime": "2023-07-21",
        "updateTime": null,
        "category": "深度学习",
        "collection": null,
        "viewsCount": 8,
        "content": "# 图像分类之KNN算法\n\n\n\n## 一、KNN的理论基础与实现\n\n### 1. 理论知识\n\n​\tKNN被翻译为最近邻算法，顾名思义，找到最近的k个邻居，",
        "sortId": null
    },
    {
        "id": -503136255,
        "title": "Numpy使用简述",
        "description": "Numpy一种开源的数值计算扩展",
        "imagePath": "https://img1.baidu.com/it/u=2396404004,3078543528&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "publishTime": "2023-07-17",
        "updateTime": null,
        "category": "深度学习",
        "collection": null,
        "viewsCount": 34,
        "content": "# Numpy使用简述",
        "sortId": null
    }
]
```



#### 9. 分类查询博客数量

- 名称：/blog/getBlogsCountByCategory
- 描述：分类查询博客数量
- 请求方式：GET
- 请求参数：

| 字段     | 说明 | 类型   | 是否必须 | 备注 |
| -------- | ---- | ------ | -------- | ---- |
| category | 类别 | String | 是       |      |

- 请求参数示例

```json
{
    "category": "深度学习"
}
```

- 响应示例：

```json
{
    2
}
```



#### 10. 查询所有合集数量

- 名称：/blog/getCollectionCount
- 描述：查询合集数量
- 请求方式：GET
- 请求参数：无

- 响应示例：

```json
[
    {
        "name": "《深度学习与图像识别原理与实践》",
        "value": 2
    },
    {
        "name": "",
        "value": 2
    }
]
```



#### 11. 分类查询合集

- 名称：/blog/getBlogsByCollection
- 描述：分类查询合集
- 请求方式：GET
- 请求参数：

| 字段             | 说明                     | 类型    | 是否必须 | 备注 |
| ---------------- | ------------------------ | ------- | -------- | ---- |
| collection       | 合集                     | String  | 是       |      |
| pageSize         | 页面大小                 | INT     | 是       |      |
| currentPage      | 当前页面                 | INT     | 是       |      |
| isSortByTimeDesc | 是否按照发布时间降序排列 | Boolean | 是       |      |
| isSortDesc       | 是否按照ID降序排列       | Boolean | 是       |      |

- 请求参数示例

```json
{
    "category": "深度学习",
    "pageSize": 3,
    "currentPage": 1,
    "isSortByTimeDesc": false,
    "isSortDesc": true
}
```

- 响应示例：

```json
[
    {
        "id": 1651974146,
        "title": "图像分类之KNN算法",
        "description": "通过KNN算法实现对图像进行分类",
        "imagePath": "https://img1.baidu.com/it/u=2156029344,4282783762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800https://img1.baidu.com/it/u=2156029344,4282783762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "publishTime": "2023-07-21",
        "updateTime": "2023-07-21",
        "category": "深度学习",
        "collection": "《深度学习与图像识别原理与实践》",
        "viewsCount": 8,
        "content": "# 图像分类之KNN算法\n\n\n\n## 一、KNN的理论基础与实现\n\n### 1. 理论知识\n\n​\tKNN被翻译为最近邻算法，顾名思义，找到最近的k个邻居，在前k个最近样本中选择最近的占比最高的类别作为预测类别。",
        "sortId": 2
    },
    {
        "id": -503136255,
        "title": "Numpy使用简述",
        "description": "Numpy一种开源的数值计算扩展",
        "imagePath": "https://img1.baidu.com/it/u=2396404004,3078543528&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "publishTime": "2023-07-17",
        "updateTime": "2023-07-17",
        "category": "深度学习",
        "collection": "《深度学习与图像识别原理与实践》",
        "viewsCount": 34,
        "content": "# Numpy使用简述\n\n\n\n**🔗[Numpy官方文档](https://numpy.org/doc/stable/index.html)**\n\n##  一、创建矩阵\n\n​\t\t在创建数组时，需要引入Numpy库：`import numpy as np`\n\n​\t\t我们可以通过创建Python列表(List)的方式来创建Numpy矩阵，",
        "sortId": 1
    }
]
```



#### 12. 获取指定合集数量

- 名称：/blog/getCountByCollection
- 描述：获取指定合集数量
- 请求方式：GET
- 请求参数：

| 字段       | 说明     | 类型   | 是否必须 | 备注 |
| ---------- | -------- | ------ | -------- | ---- |
| collection | 合集名称 | String | 是       |      |

- 请求参数示例

```json
{
    "collection": "《深度学习与图像识别原理与实践》"
}
```

- 响应示例：

```json
{
    2
}
```



#### 13. 获取指定数量的博客(不包含content)

- 名称：/blog/getShrotBlogsList
- 描述：获取指定数量的博客(不包含content)
- 请求方式：GET
- 请求参数：

| 字段        | 说明     | 类型 | 是否必须 | 备注 |
| ----------- | -------- | ---- | -------- | ---- |
| currentPage | 当前页面 | INT  | 是       |      |
| pageSize    | 页面大小 | INT  | 是       |      |

- 请求参数示例

```json
{
    "currentPage": 1,
    "pageSize": 2
}
```

- 响应示例：

```json
[
    {
        "id": 1651974146,
        "title": "图像分类之KNN算法",
        "description": "通过KNN算法实现对图像进行分类",
        "imagePath": null,
        "publishTime": null,
        "updateTime": "2023-07-21",
        "category": "深度学习",
        "collection": null,
        "viewsCount": 8,
        "content": null,
        "sortId": null
    },
    {
        "id": -503136255,
        "title": "Numpy使用简述",
        "description": "Numpy一种开源的数值计算扩展",
        "imagePath": null,
        "publishTime": null,
        "updateTime": "2023-07-17",
        "category": "深度学习",
        "collection": null,
        "viewsCount": 34,
        "content": null,
        "sortId": null
    }
]
```



#### 14. 根据id删除博客

- 名称：/blog/deleteById
- 描述：根据id删除博客
- 请求方式：GET
- 请求参数：

| 字段 | 说明   | 类型 | 是否必须 | 备注 |
| ---- | ------ | ---- | -------- | ---- |
| id   | 博客Id | INT  | 是       |      |

- 请求参数示例

```json
{
    "id": 123456
}
```

- 响应示例：

```json
{
    1
}
```



#### 15. 更新博客

- 名称：/blog/updateBlog
- 描述：更新博客
- 请求方式：POST
- 请求参数：

| 字段 | 说明     | 类型 | 是否必须 | 备注 |
| ---- | -------- | ---- | -------- | ---- |
| blog | 博客实体 | Blog | 是       |      |

- 请求参数示例

```json
{
    略
}
```

- 响应示例：

```json
{
    略
}
```



#### 16. 上传博客

- 名称：/blog/uploadBlog
- 描述：上传博客
- 请求方式：POST
- 请求参数：

| 字段 | 说明     | 类型 | 是否必须 | 备注 |
| ---- | -------- | ---- | -------- | ---- |
| blog | 博客实体 | Blog | 是       |      |

- 请求参数示例

```json
{
    略
}
```

- 响应示例：

```json
{
    略
}
```



#### 17. 获取网站最后更新日期

- 名称：/blog/getLastUpdateTime
- 描述：获取网站最后更新日期
- 请求方式：GET
- 请求参数：无
- 响应示例：

```json
{
    2023-07-21
}
```



#### 18. 模糊搜索

- 名称：/blog/search
- 描述：模糊搜索
- 请求方式：GET
- 请求参数：

| 字段     | 说明   | 类型   | 是否必须 | 备注 |
| -------- | ------ | ------ | -------- | ---- |
| keywrods | 关键词 | String | 是       |      |

- 请求参数示例

```json
{
    "keywords": "深度学习"
}
```

- 响应示例：

```json
[
    {
        "id": 1651974146,
        "title": "图像分类之KNN算法",
        "description": "通过KNN算法实现对图像进行分类",
        "imagePath": "https://img1.baidu.com/it/u=2156029344,4282783762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800https://img1.baidu.com/it/u=2156029344,4282783762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "publishTime": "2023-07-21",
        "updateTime": "2023-07-21",
        "category": "深度学习",
        "collection": "《深度学习与图像识别原理与实践》",
        "viewsCount": 8,
        "content": "谨，但实践中常在比较小的数据集上使用，在<span style=\"color:#f47466\">深度学习</span>中很少使用。\n\n<img src=\"https://cdn.jsdelivr.net/gh/guoxxxxxxx/Pic-Go@main/img/202307211531437.png\" alt=\"image-20230721153152357\" style=\"zoom:50%;\" />",
        "sortId": 2
    }
]
```
