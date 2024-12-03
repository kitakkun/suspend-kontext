package com.kitakkun.suspendkontext.gradle

import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class SuspendKontextCompilerPlugin : KotlinCompilerPluginSupportPlugin {
    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> = kotlinCompilation.target.project.provider { emptyList() }
    override fun getCompilerPluginId(): String = SuspendKontextConst.COMPILER_PLUGIN_ID
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = kotlinCompilation.target.project.extensions.getByType(SuspendKontextExtension::class.java).enabled
    override fun getPluginArtifact() = SubpluginArtifact(
        groupId = SuspendKontextConst.GROUP_ID,
        artifactId = "compiler-cli",
        version = SuspendKontextConst.kotlinPrefixedVersion,
    )
}
