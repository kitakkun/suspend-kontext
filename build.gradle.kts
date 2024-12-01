plugins {
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.mavenPublish) apply false
    alias(libs.plugins.buildconfig) apply false
    alias(libs.plugins.suspendKontextCompilerModule) apply false
    alias(libs.plugins.suspendKontextPublish) apply false
}

allprojects {
    group = "com.kitakkun.suspendkontext"
    version = rootProject.libs.versions.suspendKontext.get()
}
