# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers
# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose
# 保留Annotation不混淆 这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*,InnerClasses
# 避免混淆泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 忽略警告
-ignorewarnings
# 设置是否允许改变作用域
-allowaccessmodification
# 把混淆类中的方法名也混淆了
-useuniqueclassmembernames

# apk 包内所有 class 的内部结构
-dump class_files.txt

# 未混淆的类和成员
-printseeds seeds_txt

# 列出从apk中删除的代码
-printusage unused.txt

# 混淆前后的映射
-printmapping mapping.txt

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep public class * extends android.view.view
-keep public class com.android.vending.licensing.ILicensingService

-keepattributes *Annotation*

-keep class java.util.**{*;}
-keep class java.io.**{*;}



-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


-keepclasseswithmembernames class * {
    native <methods>;
}

-keepattributes *JavascriptInterface*

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}


#确保方法不被混淆
-keepclassmembers class * extends android.webkit.WebChromeClient{
 public void openFileChooser(...);
 public void onShowFileChooser(...);
 public void onProgressChanged(...);
 }


 -keep class * implements android.os.Parcelable {
   public static final android.os.Parcelable$Creator *;
 }

 -keep class * implements java.io.Serializable {
     public *;
 }

 -keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
 }

 -keepclassmembers public class * extends android.view.View {
    void set*(***);
    *** get*();
 }

 # We want to keep methods in Activity that could be used in the XML attribute onClick
 -keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
 }

 # For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }

 -keep class * implements android.os.Parcelable {
   public static final android.os.Parcelable$Creator *;
 }

 -keepclassmembers class **.R$* {
     *;
 }

 -dontwarn android.support.v4.**
 -keep class android.support.v4.** { *; }
 -keep interface android.support.v4.** { *; }
 -keep public class * extends android.support.v4.**
 -keep public class * extends android.app.Fragment
 -keep public class * extends android.support.v4.app.FragmentActivity
 -keep public class android.support.v4.app.FragmentTransaction

 -keep class android.content.** { *; }
 -keep interface android.support.v4.** { *; }
 -keep class android.os.** {*;}
 -keep class android.view.** {*;}
 -keep class android.widget.** {*;}
 -keep class android.database.** {*;}
 -keep class android.graphics.** {*;}
 -keep class android.provider.** {*;}
 -keep class android.util.** {*;}

 -keep public class * implements com.bumptech.glide.module.GlideModule
 -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
   **[] $VALUES;
   public *;
 }

 # for DexGuard only
 -keepresourcexmlelements manifest/application/meta-data@value=GlideModule