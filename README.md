# CRM Lead System

## 项目简介

本项目是一个基于 SpringBoot 的简化版电销线索管理系统，模拟了电销 SaaS 场景下线索从录入、公海池领取到后续跟进的核心流程。

当前项目主要实现了：

- 用户登录
- 线索新增
- 线索列表查询
- 公海线索领取
- 跟进记录新增

## 技术栈

- SpringBoot
- MyBatis
- MySQL
- Redis
- Maven
- AOP
- Interceptor

## 功能说明

### 1. 用户登录

- 调用登录接口后校验账号密码
- 登录成功后生成 token
- token 保存到 Redis，用于后续登录态校验

### 2. 线索管理

- 支持新增线索
- 新增线索默认进入公海池，状态为 `NEW`
- 支持查询线索列表

### 3. 公海线索领取

- 销售可以领取公海线索
- 领取成功后线索状态更新为 `CLAIMED`
- 使用 Redis 分布式锁雏形避免并发场景下重复领取

### 4. 跟进记录管理

- 支持新增跟进记录
- 新增跟进记录后同步更新线索状态和跟进时间
- 在跟进记录新增场景中使用 `@Transactional` 保证数据一致性

### 5. AOP 操作日志

- 使用自定义注解 `@OperationLog`
- 对登录、新增线索、领取线索、新增跟进记录等核心操作进行统一日志记录

### 6. 拦截器登录校验

- 通过 `LoginInterceptor` 拦截业务请求
- 从请求头获取 token
- 去 Redis 校验 token 是否有效

## 数据库表

项目当前使用 3 张核心表：

- `sys_user`：系统用户表
- `crm_lead`：客户线索表
- `follow_record`：跟进记录表

## 核心接口

### 登录接口

`POST /user/login`

请求示例：

```json
{
  "username": "admin",
  "password": "123456"
}
```

### 新增线索

`POST /lead/add`

### 查询线索列表

`GET /lead/list`

### 领取线索

`POST /lead/claim/{id}?userId=2`

### 新增跟进记录

`POST /follow/add`

## 启动说明

### 1. 环境要求

- JDK 17
- MySQL 8
- Redis

### 2. 数据库准备

先在 MySQL 中创建数据库：

```sql
CREATE DATABASE crm_lead DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

然后执行项目中使用的建表 SQL 和初始化测试数据。

### 3. 配置说明

项目中的数据库和 Redis 配置支持环境变量方式：

- `MYSQL_USERNAME`
- `MYSQL_PASSWORD`
- `REDIS_HOST`
- `REDIS_PORT`

如果未显式设置，则使用默认配置。

### 4. 启动项目

运行启动类：

- `com.crm.lead.LeadApplication`

启动成功后默认访问地址：

- `http://localhost:8080`

## 项目亮点

- 使用 MVC 分层结构组织代码
- 使用依赖注入降低模块耦合
- 使用事务保证多表更新一致性
- 使用 AOP 实现统一操作日志
- 使用拦截器实现登录态校验
- 使用 Redis 实现 token 管理
- 在线索领取场景中使用 Redis 分布式锁雏形
- 在线索领取成功处预留 RocketMQ 异步处理接入点

## 说明

当前项目为入职前练习项目，重点在于通过一个完整的小型业务系统串联 SpringBoot、MySQL、Redis、AOP、事务、拦截器等核心知识点。
