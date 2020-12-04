## 项目级 build.gradle
  classpath 'com.didichuxing.doraemonkit:dokitx-plugin:3.3.5'
## app下的build.gradle
  apply plugin: 'com.didi.dokit'
  dokitExt {
      //通用设置
      comm {
          //地图经纬度开关
          gpsSwitch true
          //网络开关
          networkSwitch true
          //大图开关
          bigImgSwitch true
          //webView js 抓包
          webViewSwitch true
      }

      slowMethod {
          //0:默认模式 打印函数调用栈 需添加指定入口  默认为application onCreate 和attachBaseContext
          //1:普通模式 运行时打印某个函数的耗时 全局业务代码函数插入
          strategy 0
          //函数功能开关
          methodSwitch true

          //调用栈模式配置
          stackMethod {
              //默认值为 5ms 小于该值的函数在调用栈中不显示
              thresholdTime 10
              //调用栈函数入口
              // enterMethods = ["com.ooftf.master.m.entrance.ui.MainActivity"]
              //黑名单 粒度最小到类 暂不支持到方法
              //methodBlacklist = ["org.bouncycastle.cert.AttributeCertificateHolder"]
          }
          //普通模式配置
          normalMethod {
              //默认值为 500ms 小于该值的函数在运行时不会在控制台中被打印
              thresholdTime 500
              //需要针对函数插装的包名
              //packageNames = ["com.master", "com.ooftf"]
              //不需要针对函数插装的包名&类名
              //methodBlacklist = ["com.didichuxing.doraemondemo.dokit"]
              //methodBlacklist = ["org.bouncycastle.cert.AttributeCertificateHolder"]
          }
      }
  }


## gradle.properties
  # dokit全局配置
  # 插件开关
  DOKIT_PLUGIN_SWITCH=true
  # DOKIT读取三方库会和booster冲突 如果你的项目中也集成了booster 建议将开关改成false
  DOKIT_THIRD_LIB_SWITCH=true
  # 插件日志
  DOKIT_LOG_SWITCH=true
  # 自定义Webview的全限定名 主要是作用于h5 js抓包和数据mock
  DOKIT_WEBVIEW_CLASS_NAME=com/didichuxing/doraemonkit/widget/webview/MyWebView
  # dokit 慢函数开关
  DOKIT_METHOD_SWITCH=true
  # dokit 函数调用栈层级
  DOKIT_METHOD_STACK_LEVEL=4
  # 0:默认模式 打印函数调用栈 需添加指定入口  默认为application onCreate 和attachBaseContext
  # 1:普通模式 运行时打印某个函数的耗时 全局业务代码函数插入
  DOKIT_METHOD_STRATEGY=0

### 需要的地方
  implementation 'com.ooftf:director:1.0.2'
   debugImplementation 'com.didichuxing.doraemonkit:dokitx:3.3.5'
   releaseImplementation 'com.didichuxing.doraemonkit:dokitx-no-op:3.3.5'
### 初始化
  Director.init("b6a6080607d1a37310565aca1998e0e9",BuildConfig.DEBUG)
### okhttp添加拦截器
   Director.getReviseInterceptor()，Director.getLogViewInterceptor()
### 设置点击开启入口
Director.setDebugEntranceView(v)