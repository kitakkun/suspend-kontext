plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    compileOnly(libs.kotlin.compiler.embeddable)
}
