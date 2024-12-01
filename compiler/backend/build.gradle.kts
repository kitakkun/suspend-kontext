plugins {
    alias(libs.plugins.kotlinJvm)
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