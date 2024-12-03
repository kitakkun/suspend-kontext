package com.kitakkun.suspendkontext.compiler.k2

import org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate

val SuspendKontextPredicate = DeclarationPredicate.create {
    metaAnnotated(
        SuspendKontextFirConsts.SuspendKontextAnnotationClassId.asSingleFqName(),
        includeItself = false,
    )
}