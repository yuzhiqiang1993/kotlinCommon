
#防止release版本binding委托找不到成员方法
-keepclassmembers class ** implements androidx.viewbinding.ViewBinding {
    public static ** bind(***);
    public static ** inflate(***);
    public static ** inflate(**,**);
}

