# kotlinCommon

## 简介

`kotlinCommon` 是一个 Android 开发最佳实践示例项目，整合了我在实际业务开发中的经验积累。项目采用
Kotlin 语言，展示了 MVP 和 MVVM 架构的实践方案，集成了主流的 Jetpack 组件，旨在为 Android
开发者提供清晰的代码示例和解决方案参考。

## 项目亮点

- 🏗️ MVP/MVVM 架构实践示例
- 🎨 Jetpack Compose 现代 UI 开发范例
- 🌐 网络请求模块的统一封装方案
- 🔄 协程与 Flow 在实际业务中的应用
- 📱 常用 UI 组件封装示例
- 🛠️ 实用工具类和扩展函数
- 🔌 原生与 H5 混合开发方案
- 📦 模块化设计最佳实践

## 源码导读

### 基础架构 `/base`

基础组件封装，包含：

- `BaseActivity`：Activity 基类，统一处理生命周期、权限等
- `BaseFragment`：Fragment 基类，提供通用功能
- `BaseViewModel`：ViewModel 基类，处理协程和数据加载

### 网络请求 `/api`

网络层封装示例：

- 统一的接口定义方式
- 错误处理机制
- 请求拦截器使用
- 缓存策略实现

### UI 组件 `/widget` & `/dialog`

常用自定义组件：

- 通用对话框封装
- 自定义 View 实现
- 加载状态视图
- 列表刷新组件

### Compose 实践 `/compose_theme`

Compose 主题定制：

- Material Design 3 适配
- 自定义组件封装
- 主题切换实现

### 扩展工具 `/ext`

常用扩展函数：

- View 扩展
- Context 扩展
- String 处理
- 时间日期工具

### 适配器 `/adapter`

RecyclerView 相关：

- 通用适配器封装
- 多类型列表实现
- 列表动画示例

### 混合开发 `/hybrid`

原生与 H5 交互：

- WebView 封装
- JS Bridge 实现
- 通信机制示例

### 功能模块

- `/service`：后台服务示例
- `/receiver`：广播使用示例
- `/task`：任务调度实现
- `/manager`：各类管理器示例

## 如何使用本项目

1. **学习特定功能**
   - 直接查看对应模块的源码
   - 阅读相关注释和文档
   - 参考实现方式并应用到自己的项目

2. **运行示例**
   - Clone 项目到本地
   - 使用 Android Studio 打开
   - 运行 app 模块查看效果

## 项目环境

- Android Studio Arctic Fox 或更高版本
- Kotlin 1.5.0+
- Minimum SDK: 21
- Target SDK: 33+

## 技术栈

### 核心组件

- Kotlin 协程
- Jetpack (Compose, ViewModel, LiveData, Room)
- Retrofit + OkHttp
- Coil 图片加载
- ARouter 路由框架

## 问题反馈

如果您在查看源码过程中有任何问题或建议：

- [提交 Issue](https://github.com/yuzhiqiang1993/kotlinCommon/issues)

## 项目统计

- Kotlin: 69.2%
- Java: 29.0%
- 其他: 1.8%

## License

本项目遵循 [MIT 许可证](LICENSE)。
