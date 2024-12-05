package com.kitakkun.suspendkontext.compiler.k2.checkers

import com.kitakkun.suspendkontext.compiler.k2.SuspendKontextPredicate
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionChecker
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.toAnnotationClass
import org.jetbrains.kotlin.fir.extensions.predicateBasedProvider

object MultipleContextApplicationChecker : FirFunctionChecker(MppCheckerKind.Common) {
    override fun check(declaration: FirFunction, context: CheckerContext, reporter: DiagnosticReporter) {
        val appliedContexts = declaration.annotations.mapNotNull { it.toAnnotationClass(context.session) }.filter {
            context.session.predicateBasedProvider.matches(SuspendKontextPredicate, it)
        }

        if (appliedContexts.size > 1) {
            reporter.reportOn(
                source = declaration.source,
                factory = SuspendKontextErrors.MULTIPLE_CONTEXT_APPLICATION,
                context = context,
            )
        }
    }
}
