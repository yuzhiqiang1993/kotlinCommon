#keep crashreporter
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
