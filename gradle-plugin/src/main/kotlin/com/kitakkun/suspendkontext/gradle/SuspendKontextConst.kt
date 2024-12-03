package com.kitakkun.suspendkontext.gradle

import com.kitakkun.suspendkontext.gradle_plugin.BuildConfig
import org.jetbrains.kotlin.gradle.utils.loadPropertyFromResources

object SuspendKontextConst {
    const val GROUP_ID = "com.kitakkun.suspendkontext"
    const val COMPILER_PLUGIN_ID = "suspend-kontext-compiler-plugin"
    const val CORE_LIBRARY_DEPENDENCY_NOTATION = "$GROUP_ID:core:${BuildConfig.VERSION}"

    val kotlinVersion by lazy { loadKotlinVersion() }
    val kotlinPrefixedVersion by lazy { "$kotlinVersion-${BuildConfig.VERSION}" }

    private fun loadKotlinVersion(): String = object {}.loadPropertyFromResources("project.properties", "project.version")
}
