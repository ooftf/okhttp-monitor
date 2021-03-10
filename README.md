# Monitor
发送网络请求时，能够查看和修改请求和响应内容
----------------------
[ ![Download](https://api.bintray.com/packages/ooftf/maven/okhttp-monitor/images/download.svg) ](https://bintray.com/ooftf/maven/okhttp-monitor/_latestVersion)
## 运行效果图
### 请求
<img src="/art/request.png" alt="图片替换文本" width="360" height="800" align="bottom" />

### 响应
<img src="/art/response.png" alt="图片替换文本" width="360" height="800" align="bottom" />

## 集成文档

### Gradle
```
// Project
allprojects {
    repositories {
        maven {
            url "https://dl.bintray.com/ooftf/maven"
        }
    }
}
// APP
dependencies {
    implementation 'com.ooftf:okhttp-monitor:1.1.2'
}

```
### 代码
```
// Application中初始化
Monitor.init()
// 添加拦截器
okHttpBuild.applyMonitor()
// 由于默认是不开启任何请求，需要进入设置页面打开总开关，之后网络请求会出现在可拦截列表中
Monitor.getNetMockIntent()
// 只查看日志页面
Monitor.getNetLogIntent()
```
# director
director集成了一整套调试系统，包含一些入口UI和 okhttp-monitor  dokit  
如果集成了director就不用集成okHttp-monitor了  
[集成文档](/director/README.md)