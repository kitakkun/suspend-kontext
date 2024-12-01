plugins {
    alias(libs.plugins.suspendKontextCompilerModule)
    alias(libs.plugins.suspendKontextPublish)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add(
            "-opt-in=org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI"
        )
    }
}

dependencies {
    compileOnly(libs.kotlin.compiler.embeddable)
}

suspendKontextPublication {
    artifactId = "compiler-backend"
}
