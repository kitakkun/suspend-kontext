package com.kitakkun.suspendkontext.compiler.backend

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid

class SuspendKontextIrGenerationExtension(
    private val messageCollector: MessageCollector,
): IrGenerationExtension {
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        val irContext = SuspendKontextIrContext(pluginContext, messageCollector)
        moduleFragment.transformChildrenVoid(CoroutineContextTransformer(irContext))
    }
}