# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep line numbers for better crash reports
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.opencrumb.shared.data.model.**$$serializer { *; }
-keepclassmembers class com.opencrumb.shared.data.model.** {
    *** Companion;
}
-keepclasseswithmembers class com.opencrumb.shared.data.model.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Compose
-keep class androidx.compose.** { *; }
-keep class kotlin.Metadata { *; }

# Keep data classes used in the app
-keep class com.opencrumb.shared.data.model.** { *; }