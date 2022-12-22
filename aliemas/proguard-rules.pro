#keep crashreporter (异常上报)
-keep class com.alibaba.motu.crashreporter.**{ *;}
-keep class com.uc.crashsdk.**{*;}
-keep interface com.ut.mini.crashhandler.*{*;}
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod


#阿里性能分析
-keep class com.taobao.monitor.impl.**{*;}
-keep class com.taobao.monitor.adapter.**{*;}
-keep class com.taobao.application.common.**{*;}
-keep class com.facebook.drawee.generic.RootDrawable{*;}
-keep class com.facebook.drawee.drawable.FadeDrawable{*;}
-keep class androidx.fragment.app.Fragment{*;}
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

#远程日志
#-keep class com.taobao.tao.log.**{*;}
-keep public class * extends com.taobao.android.tlog.protocol.model.request.base.FileInfo{*;}
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

#移动数据分析
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

#推送
-keepclasseswithmembernames class ** {
    native <methods>;
}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-keep class com.ut.** {*;}
-keep class com.ta.** {*;}
-keep class anet.**{*;}
-keep class anetwork.**{*;}
-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.**{*;}
-keep class android.os.**{*;}
-keep class org.json.**{*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-dontwarn anet.**
-dontwarn org.android.spdy.**
-dontwarn org.android.agoo.**
-dontwarn anetwork.**
-dontwarn com.ut.**
-dontwarn com.ta.**