package com.kitakkun.suspendkontext.compiler.k2.checkers

import com.kitakkun.suspendkontext.compiler.k2.SuspendKontextPredicate
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionChecker
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.utils.isSuspend
import org.jetbrains.kotlin.fir.extensions.predicateBasedProvider

object AnnotationUseSiteChecker : FirFunctionChecker(MppCheckerKind.Common) {
    override fun check(declaration: FirFunction, context: CheckerContext, reporter: DiagnosticReporter) {
        if (!context.session.predicateBasedProvider.matches(SuspendKontextPredicate, declaration)) return

        if (!declaration.isSuspend) {
            reporter.reportOn(
                source = declaration.source,
                factory = SuspendKontextErrors.NON_SUSPEND_FUNCTION,
                context = context,
            )
        }
    }
}