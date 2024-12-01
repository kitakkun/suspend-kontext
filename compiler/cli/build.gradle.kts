plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    compileOnly(libs.kotlin.compiler.embeddable)
    compileOnly(projects.compiler.backend)
    compileOnly(projects.compiler.k2)
    compileOnly(projects.compiler.common)
}
