plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.suspendKontextPublish)
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
}

android {
    namespace = "com.kitakkun.suspendkontext.core"
    compileSdk = 35
}

suspendKontextPublication {
    artifactId = "core"
}
