import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
            
            // Export Compose Resources to framework
            export(compose.components.resources)
        }
        
        // Copy Compose Resources to framework after linking
        val linkTaskName = "linkDebugFramework${iosTarget.name.capitalize()}"
        tasks.named(linkTaskName).configure {
            doLast {
                val frameworkDir = outputs.files.singleFile
                val resourcesDir = file("build/generated/compose/resourceGenerator/assembledResources/${iosTarget.name}Main/composeResources")
                val targetDir = file("${frameworkDir}/compose-resources")
                
                if (resourcesDir.exists()) {
                    copy {
                        from(resourcesDir)
                        into(targetDir)
                    }
                    println("Copied Compose Resources to ${targetDir}")
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            api(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlinx.serialization.json)
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "com.opencrumb.shared"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
    
    sourceSets {
        getByName("main") {
            assets.srcDirs("src/commonMain/resources")
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
