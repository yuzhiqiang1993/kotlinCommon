

#（可选）避免Log打印输出

-assumenosideeffects class com.blankj.utilcode.util.LogUtils {
       public static *** d(...);
       public static *** e(...);
       public static *** i(...);
       public static *** v(...);
       public static *** w(...);
}





#AndPermission
-dontwarn com.yanzhenjie.permission.**

#ARouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider


#gson
-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep interface com.google.gson.**{*;}

