plugins {
    alias(libs.plugins.suspendKontextCompilerModule)
    alias(libs.plugins.suspendKontextPublish)
}

dependencies {
    compileOnly(libs.kotlin.compiler.embeddable)
    implementation(projects.compiler.backend)
    implementation(projects.compiler.k2)
    implementation(projects.compiler.common)
}

suspendKontextPublication {
    artifactId = "compiler-cli"
}
