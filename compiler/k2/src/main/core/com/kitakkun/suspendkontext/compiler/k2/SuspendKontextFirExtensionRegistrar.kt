package com.kitakkun.suspendkontext.compiler.k2

import com.kitakkun.suspendkontext.compiler.k2.api.VersionSpecificApi
import com.kitakkun.suspendkontext.compiler.k2.api.VersionSpecificApiImpl
import com.kitakkun.suspendkontext.compiler.k2.extension.SuspendKontextFirCheckersExtension
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

class SuspendKontextFirExtensionRegistrar : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        VersionSpecificApi.INSTANCE = VersionSpecificApiImpl()
        +::SuspendKontextFirCheckersExtension
    }
}
