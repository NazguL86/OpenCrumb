import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidLibrary {
        namespace = "com.opencrumb.shared"
        compileSdk = 36
        minSdk = 24

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

        withSourcesJar(publish = true)
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true

            // Export Compose Resources to framework
            export(compose.components.resources)
        }

        // Copy Compose Resources to framework after linking
        val linkTaskName = "linkDebugFramework${iosTarget.name.replaceFirstChar { it.uppercase() }}"
        tasks.named(linkTaskName).configure {
            doLast {
                val frameworkDir = outputs.files.singleFile
                val resourcesDir =
                    file("build/generated/compose/resourceGenerator/assembledResources/${iosTarget.name}Main/composeResources")
                val targetDir = file("$frameworkDir/compose-resources")

                if (resourcesDir.exists()) {
                    copy {
                        from(resourcesDir)
                        into(targetDir)
                    }
                    println("Copied Compose Resources to $targetDir")
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            api(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlinx.serialization.json)
            api(libs.kotlinx.coroutines.core)
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.coroutines.android)
        }
    }
}

// Copy Compose Resources to app assets
val copyComposeResourcesToAppAssets =
    tasks.register("copyComposeResourcesToAppAssets") {
        val resourcesDir = file("build/generated/compose/resourceGenerator/preparedResources/commonMain/composeResources")
        val appAssetsDir = file("../app/src/main/assets/composeResources/opencrumb.shared.generated.resources")

        doLast {
            if (resourcesDir.exists()) {
                copy {
                    from(resourcesDir)
                    into(appAssetsDir)
                }
                println("Copied Compose Resources to app assets")
            }
        }
    }

tasks.matching { it.name.startsWith("prepareComposeResources") }.configureEach {
    finalizedBy(copyComposeResourcesToAppAssets)
}
