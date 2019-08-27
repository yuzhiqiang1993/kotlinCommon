
#数据类型不混淆
-keep class com.yzq.data_constants.data.** { *; }

#gson
-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep interface com.google.gson.**{*;}

