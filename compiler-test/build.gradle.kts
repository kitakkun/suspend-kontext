plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()
    macosArm64()
    macosX64()

    sourceSets.commonMain.dependencies {
        implementation(projects.core)
        implementation(libs.kotlinx.coroutines.core)
    }
}

dependencies {
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