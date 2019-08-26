

-dontwarn com.yzq.data_constants.data**
#对含有反射类的处理
-keep com.yzq.data_constants.data.** { *; }



#gson
-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep interface com.google.gson.**{*;}

