plugins {
    alias(libs.plugins.suspendKontextCompilerModule)
    alias(libs.plugins.suspendKontextPublish)
}

dependencies {
    compileOnly(libs.kotlin.compiler.embeddable)
}

suspendKontextPublication {
    artifactId = "compiler-common"
}
