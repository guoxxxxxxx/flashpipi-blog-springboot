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

### 1. **获取网站作者信息**

- 名称：/user/getAuthorInfo
- 描述：获取网站作者信息
- 请求方式：GET
- 请求参数：无
- 响应示例：

``` json
{
    "id": 1,
    "name": "闪光皮皮",
    "sex": "男",
    "phone": "18888888888",
    "email": "guo_x0315@163.com",
    "isDelete": 0,
    "password": "123456",
    "avatar": "https://github.com/guoxxxxxxx/Pic-Go/blob/main/img/avator.jpg"
}
```





