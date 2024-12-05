package com.kitakkun.suspendkontext.compiler.k2.extension

import com.kitakkun.suspendkontext.compiler.k2.SuspendKontextPredicate
import com.kitakkun.suspendkontext.compiler.k2.checkers.AnnotationUseSiteChecker
import com.kitakkun.suspendkontext.compiler.k2.checkers.CustomContextDefinitionChecker
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionChecker
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirAnnotationCallChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.extensions.FirDeclarationPredicateRegistrar

class SuspendKontextFirCheckersExtension(session: FirSession) : FirAdditionalCheckersExtension(session) {
    override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {
        override val functionCheckers: Set<FirFunctionChecker> = setOf(AnnotationUseSiteChecker)
    }
    override val expressionCheckers: ExpressionCheckers = object : ExpressionCheckers() {
        override val annotationCallCheckers: Set<FirAnnotationCallChecker> = setOf(CustomContextDefinitionChecker)
    }

    override fun FirDeclarationPredicateRegistrar.registerPredicates() {
        register(SuspendKontextPredicate)
    }
}
