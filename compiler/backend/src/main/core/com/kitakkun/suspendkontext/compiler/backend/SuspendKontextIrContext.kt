package com.kitakkun.suspendkontext.compiler.backend

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.javac.resolve.classId
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

class SuspendKontextIrContext(
    baseContext: IrPluginContext,
    val messageCollector: MessageCollector,
) : IrPluginContext by baseContext {
    val withContextFunction by lazy {
        referenceFunctions(CallableId(FqName("kotlinx.coroutines"), Name.identifier("withContext"))).single()
    }

    val dispatchers by lazy { referenceClass(classId("kotlinx.coroutines", "Dispatchers"))!! }
    val coroutineScopeClass by lazy { referenceClass(classId("kotlinx.coroutines", "CoroutineScope"))!! }
}