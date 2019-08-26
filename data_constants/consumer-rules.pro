
#
#---------------------------------实体类---------------------------------
#--------(实体Model不能混淆，否则找不到对应的属性获取不到值)-----
#

#-dontwarn com.yzq.data_constants.data**
##对含有反射类的处理
#-keep com.yzq.data_constants.data.** { *; }



#gson
-dontwarn com.google.gson.**
-keep class com.google.gson.**{*;}
-keep interface com.google.gson.**{*;}

