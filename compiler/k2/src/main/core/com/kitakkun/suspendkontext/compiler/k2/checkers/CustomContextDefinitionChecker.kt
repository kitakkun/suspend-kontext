package com.kitakkun.suspendkontext.compiler.k2.checkers

import com.kitakkun.suspendkontext.compiler.k2.SuspendKontextFirConsts
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirAnnotationCallChecker
import org.jetbrains.kotlin.fir.declarations.getKClassArgument
import org.jetbrains.kotlin.fir.declarations.toAnnotationClassId
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall
import org.jetbrains.kotlin.fir.resolve.defaultType
import org.jetbrains.kotlin.fir.resolve.providers.getRegularClassSymbolByClassId
import org.jetbrains.kotlin.fir.types.isSubtypeOf
import org.jetbrains.kotlin.fir.types.typeContext
import org.jetbrains.kotlin.name.Name

object CustomContextDefinitionChecker : FirAnnotationCallChecker(MppCheckerKind.Common) {
    override fun check(expression: FirAnnotationCall, context: CheckerContext, reporter: DiagnosticReporter) {
        val annotationClassId = expression.toAnnotationClassId(context.session) ?: return

        if (annotationClassId != SuspendKontextFirConsts.CustomContextAnnotationClassId) return

        val klass = expression.getKClassArgument(Name.identifier("dispatcher"), context.session) ?: return
        val coroutineDispatcherClass = context.session.getRegularClassSymbolByClassId(SuspendKontextFirConsts.CoroutineDispatcherClassId) ?: return

        if (!klass.isSubtypeOf(context = context.session.typeContext, type = coroutineDispatcherClass.defaultType())) {
            reporter.reportOn(
                source = expression.source,
                factory = SuspendKontextErrors.NON_DISPATCHER_CLASS,
                context = context,
            )
        }
    }
}
