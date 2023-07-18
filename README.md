# ✨FlashPipi's Blog 开发文档

## 数据库设计

- 用户信息表( user )

| 字段      | 类型         | 说明                                             |
| --------- | ------------ | ------------------------------------------------ |
| id        | INT          | 用户ID，自增，主键                               |
| name      | VARCHAR(10)  | 用户姓名                                         |
| sex       | VARCHAR(1)   | 用户性别                                         |
| phone     | CHAR(11)     | 手机号                                           |
| email     | VARCHAR(50)  | 邮箱                                             |
| is_delete | INT          | 伪删除用户信息，默认为0， 1代表删除，0代表未删除 |
| password  | VHARCHAR(20) | 用户密码，不明文存储，存储的内容为加密后的内容   |
| avatar    | VARCHAR(500) | 用户头像                                         |



- 博客信息表( blog )

| 字段        | 类型         | 说明                                                         |
| ----------- | ------------ | ------------------------------------------------------------ |
| id          | INT          | 主键，自增                                                   |
| title       | VARCHAR(20)  | 博客标题                                                     |
| description | VARCHAR(50)  | 博客的简要描述                                               |
| image_path  | VARCHAR(500) | 博客的背景图片路径                                           |
| pubTime     | DATE         | 发布时间                                                     |
| updateTime  | DATE         | 更新时间                                                     |
| category    | VARCHAR(10)  | 分类                                                         |
| tags        | VARCHAR(100) | 标签                                                         |
| views_count | INT          | 博客的阅读数量                                               |
| content     | LONGTEXT     | 文章内容                                                     |
| is_delete   | INT          | 伪删除用户信息，默认为0， 1代表删除，0代表未删除             |
| uuid        | CHAR(128)    | 用于比对数据是否更新，若未更新则不再向服务器请求数据，用于浏览器本地缓存。 |



- 网站其他信息( information )

| 字段            | 类型         | 说明             |
| --------------- | ------------ | ---------------- |
| id              | INT          | 主键             |
| background_path | VARCHAR(500) | 网站背景图片地址 |
| website         | VARCHAR(500) | 网站域名         |
| updateTime      | DATE         | 最后一次更新时间 |
| article_count   | INT          | 网站文章总数     |
| category_count  | INT          | 网站文章分类总数 |
| views_count     | INT          | 本网站总访问量   |
| notice          | LONGTEXT     | 网站公告栏内容   |



## 接口设计



### 1. 获取网站作者信息

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



### 2. 用户注册

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



### 3. 用户登录

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



### 4. 修改用户密码

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



### 5. 修改用户信息

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



### 6. 获取验证码

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



