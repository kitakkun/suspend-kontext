package com.kitakkun.suspendkontext.compiler.k2.checkers

import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.diagnostics.error0
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory

object SuspendKontextErrors {
    val NON_SUSPEND_FUNCTION by error0<PsiElement>()
    val NON_DISPATCHER_CLASS by error0<PsiElement>()

    init {
        RootDiagnosticRendererFactory.registerFactory(SuspendKontextErrorMessages)
    }
}
