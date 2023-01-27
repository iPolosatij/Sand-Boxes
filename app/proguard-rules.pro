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
-keep class io.codetail.animation.arcanimator.** { *; }

#app
-keep public class com.digitallabstudio.sandboxes.App

# smack
-keepattributes Signature
-keep class org.jivesoftware.smack.** { *; }
-keep class org.jivesoftware.smackx.** { *; }
-keep interface org.jivesoftware.smack.** { *; }
-keep interface org.jivesoftware.smackx.** { *; }

-keep class com.digitallabstudio.sandboxes.api.http.HttpPublicRequestTools
-keep class space.dlsunity.arctour.util.glide.** { *; }

# webrtc
-keep class org.webrtc.** { *; }

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# Serializable
-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ZBarScanner
-keepclassmembers class net.sourceforge.zbar.ImageScanner { *; }
-keepclassmembers class net.sourceforge.zbar.Image { *; }
-keepclassmembers class net.sourceforge.zbar.Symbol { *; }
-keepclassmembers class net.sourceforge.zbar.SymbolSet { *; }

-dontwarn com.googlecode.mp4parser.authoring.tracks.mjpeg.**
-dontwarn com.afollestad.**
-dontwarn droidninja.filepicker.**

# PrettyTime
-keep class org.ocpsoft.prettytime.**

#PDF Viewer
-keep class com.shockwave.**