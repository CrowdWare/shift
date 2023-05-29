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

-dontwarn com.goterl.lazysodium.LazySodiumJava
-dontwarn com.goterl.lazysodium.SodiumJava
-dontwarn nl.tudelft.ipv8.keyvault.JavaCryptoProvider


-keep class at.crowdware.shift.ui.widgets.Country { *; }
-keep class at.crowdware.shift.logic.Transaction { *; }
-keep class at.crowdware.shift.logic.Account { *; }
-keep class at.crowdware.shift.ui.pages.Lmp { *; }



