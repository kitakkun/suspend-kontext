plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    jvmToolchain(17)
    jvm()
    androidTarget()
    // linux
    linuxX64()
    linuxArm64()
    // windows
    mingwX64()
    // macos
    macosX64()
    macosArm64()
    // ios
    iosSimulatorArm64()
    iosX64()
    iosArm64()
    // watchos
    watchosSimulatorArm64()
    watchosArm32()
    watchosArm64()
    watchosX64()
    // tvos
    tvosSimulatorArm64()
    tvosX64()
    tvosArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core)
            implementation(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
        }
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

dependencies {
    commonTestImplementation(libs.kotlinx.coroutines.test)
    commonTestImplementation(libs.kotlin.test)
    kotlinCompilerPluginClasspath(projects.compiler.backend)
    kotlinCompilerPluginClasspath(projects.compiler.k2)
    kotlinCompilerPluginClasspath(projects.compiler.cli)
    kotlinCompilerPluginClasspath(projects.compiler.common)
    kotlinNativeCompilerPluginClasspath(projects.compiler.backend)
    kotlinNativeCompilerPluginClasspath(projects.compiler.k2)
    kotlinNativeCompilerPluginClasspath(projects.compiler.cli)
    kotlinNativeCompilerPluginClasspath(projects.compiler.common)
}

android {
    namespace = "com.kitakkun.suspendkontext.test"
    compileSdk = 35
}
