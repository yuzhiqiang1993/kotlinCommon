-keepclassmembers class * extends com.facebook.react.bridge.ReactModuleWithSpec {
    *;
}
-keepclassmembers class * extends com.facebook.react.ReactPackage {
   *;
}
-keepclassmembers class * extends com.facebook.react.bridge.JavaScriptModule {
   *;
}
-keepclassmembers class * extends com.facebook.react.bridge.NativeModule {
   *;
}
-keepclassmembers class * extends com.facebook.react.bridge.ReactContextBaseJavaModule {
   *;
}
-keep class com.facebook.react.** { *; }
-keep class com.facebook.jni.** { *; }
-keep class com.facebook.jscexecutor.** { *; }
-dontwarn com.facebook.react.**
-dontwarn com.facebook.jni.**