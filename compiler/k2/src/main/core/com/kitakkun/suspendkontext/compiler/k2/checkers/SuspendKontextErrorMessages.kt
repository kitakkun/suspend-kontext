package com.kitakkun.suspendkontext.compiler.k2.checkers

import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory

object SuspendKontextErrorMessages: BaseDiagnosticRendererFactory() {
    override val MAP: KtDiagnosticFactoryToRendererMap = KtDiagnosticFactoryToRendererMap("suspend-kontext").apply {
        put(
            factory = SuspendKontextErrors.NON_SUSPEND_FUNCTION,
            message = "Functions annotated with suspend-kontext annotations must be suspend."
        )
    }
}