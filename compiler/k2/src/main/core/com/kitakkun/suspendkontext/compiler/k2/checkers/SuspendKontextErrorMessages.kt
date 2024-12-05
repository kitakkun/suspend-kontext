package com.kitakkun.suspendkontext.compiler.k2.checkers

import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory

object SuspendKontextErrorMessages : BaseDiagnosticRendererFactory() {
    override val MAP: KtDiagnosticFactoryToRendererMap = KtDiagnosticFactoryToRendererMap("suspend-kontext").apply {
        put(
            factory = SuspendKontextErrors.NON_SUSPEND_FUNCTION,
            message = "Functions annotated with suspend-kontext annotations must be suspend."
        )
        put(
            factory = SuspendKontextErrors.NON_DISPATCHER_CLASS,
            message = "the dispatcher for CustomContext must be a super class of kotlinx.coroutines.CoroutineDispatcher.",
        )
    }
}
