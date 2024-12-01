package com.kitakkun.suspendkontext.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinSingleTargetExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class SuspendKontextGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // access to initialize kotlinVersion (will be used later)
            SuspendKontextConst.kotlinVersion

            plugins.apply(SuspendKontextCompilerPlugin::class.java)
            extensions.create("suspendKontext", SuspendKontextExtension::class.java)

            when(kotlinExtension) {
                is KotlinMultiplatformExtension -> {
                    dependencies.commonMainImplementation(SuspendKontextConst.CORE_LIBRARY_DEPENDENCY_NOTATION)
                }
                is KotlinSingleTargetExtension<*> -> {
                    dependencies.implementation(SuspendKontextConst.CORE_LIBRARY_DEPENDENCY_NOTATION)
                }
            }
        }
    }
}

private fun DependencyHandler.implementation(notation: String) {
    add("implementation", notation)
}

private fun DependencyHandler.commonMainImplementation(notation: String) {
    add("commonMainImplementation", notation)
}
