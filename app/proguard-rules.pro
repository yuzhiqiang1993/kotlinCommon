#libraryjars  声明lib jar文件
#dontwarn 不提示警告 dontwarn是一个和keep可以说是形影不离,尤其是处理引入的library时.
#引入的library可能存在一些无法找到的引用和其他问题,在build时可能会发出警告,
#如果我们不进行处理,通常会导致build中止.
#因此为了保证build继续,我们需要使用dontwarn处理这些我们无法解决的library的警告.
#dontnote:指定不去输出打印该类产生的错误或遗漏
#keep 保留类和类中的成员，防止被混淆或移除
#keepnames 保留类和类中的成员，防止被混淆，成员没有被引用会被移除
#keepclassmembers 只保留类中的成员，防止被混淆或移除
#keepclassmembernames 只保留类中的成员，防止被混淆，成员没有引用会被移除
#keepclasseswithmembers 保留类和类中的成员，防止被混淆或移除，保留指明的成员
#keepclasseswithmembernames 保留类和类中的成员，防止被混淆，保留指明的成员，成员没有引用会被移除


#----------------------基本指令区-----------------
# 设置混淆的压缩比率 0 ~ 7
-optimizationpasses 5
#将所有类移动到默认（根）包中,默认是不开启的
-repackageclasses
# 混淆后类名都为小写   Aa aA
#-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 不优化输入的类文件
#-dontoptimize
# 不做预校验的操作
-dontpreverify
# 混淆时不记录日志
-verbose
# 保留代码行号，方便异常信息的追踪
-keepattributes SourceFile,LineNumberTable
# 混淆采用的算法.
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# seeds.txt文件列出未混淆的类和成员
-printseeds seeds.txt
# usage.txt文件列出从apk中删除的代码
-printusage unused.txt
# mapping文件列出混淆前后的映射
-printmapping mapping.txt
# 忽略警告 不加的话某些情况下打包会报错中断
-ignorewarnings


#----------------------Android通用-----------------

# 避免混淆Android基本组件，下面是兼容性比较高的规则
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View


# 保留support下的所有类及其内部类
-keep class android.support.** {*;}
-keep interface android.support.** {*;}
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
-dontwarn android.support.**

# 保留androidx下的所有类及其内部类
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-keep class com.google.android.material.** {*;}
-dontwarn androidx.**
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**

# 保持Activity中与View相关方法不被混淆
-keepclassmembers class * extends android.app.Activity{
        public void *(android.view.View);
}

# 避免混淆所有native的方法,涉及到C、C++
-keepclasseswithmembernames class * {
        native <methods>;
}

# 避免混淆自定义控件类的get/set方法和构造函数
-keep public class * extends android.view.View{
        *** get*();
        void set*(***);
        public <init>(android.content.Context);
        public <init>(android.content.Context,android.util.AttributeSet);
        public <init>(android.content.Context,android.util.AttributeSet,int);
}
-keepclasseswithmembers class * {
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 避免混淆枚举类
-keepclassmembers enum * {
        public static **[] values();
        public static ** valueOf(java.lang.String);
}

# 避免混淆序列化类
# 不混淆Parcelable和它的实现子类，还有Creator成员变量
-keep class * implements android.os.Parcelable {
        public static final android.os.Parcelable$Creator *;
}

# 不混淆Serializable和它的实现子类、其成员变量
-keep public class * implements java.io.Serializable {*;}
-keepclassmembers class * implements java.io.Serializable {
        static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
}

# 资源ID不被混淆
-keep class **.R$* {*;}

# 回调函数事件不能混淆
-keepclassmembers class * {
        void *(**On*Event);
        void *(**On*Listener);
}

# Webview 相关不混淆
-keepclassmembers class * extends android.webkit.WebViewClient {
        public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
        public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
        public void *(android.webkit.WebView, java.lang.String);
 }

# 使用GSON、fastjson等框架时，所写的JSON对象类不混淆，否则无法将JSON解析成对应的对象
-keepclassmembers class * {
         public <init>(org.json.JSONObject);
}

#不混淆泛型
-keepattributes Signature

#避免混淆注解类
-keepattributes *Annotation*

#避免混淆内部类
-keepattributes InnerClasses

# 移除日志打印
-assumenosideeffects class android.util.Log {
        public static *** d(...);
        public static *** e(...);
        public static *** i(...);
        public static *** v(...);
        public static *** println(...);
        public static *** w(...);
        public static *** wtf(...);
}


#kotlin 相关

-dontwarn kotlin.**
-keep class kotlin.** { *; }
-keep interface kotlin.** { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-keepclasseswithmembers @kotlin.Metadata class * { *; }
-keepclassmembers class **.WhenMappings {
    <fields>;
}


-keep class kotlinx.** { *; }
-keep interface kotlinx.** { *; }
-dontwarn kotlinx.**


-keep class org.jetbrains.** { *; }
-keep interface org.jetbrains.** { *; }
-dontwarn org.jetbrains.**



#
##Cordova
#-keep class org.apache.cordova.**{*;}
#-keep interface org.apache.cordova.**{*;}
#-keep enum org.apache.cordova.**{*;}
#
#
##-keep class com.yzq.cordova_webcontainer.CordovaWebContainer { *; }
#-keep class com.yzq.cordova_webcontainer.CordovaWebContainer.**{*;}
#-keep interface com.yzq.cordova_webcontainer.CordovaWebContainer.**{*;}
#-keep enum com.yzq.cordova_webcontainer.CordovaWebContainer.**{*;}


-dontwarn com.yzq.baidu_location.BaiduLocationManager
-dontwarn com.yzq.tencent_location.TxLocationManager
