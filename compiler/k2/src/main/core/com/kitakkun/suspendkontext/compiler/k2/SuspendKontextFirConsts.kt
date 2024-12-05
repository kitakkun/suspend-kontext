package com.kitakkun.suspendkontext.compiler.k2

import org.jetbrains.kotlin.javac.resolve.classId

object SuspendKontextFirConsts {
    val SuspendKontextAnnotationClassId = classId("com.kitakkun.suspendkontext.core.annotation", "SuspendKontext")
    val CustomContextAnnotationClassId = classId("com.kitakkun.suspendkontext.core.annotation", "CustomContext")
    val CoroutineDispatcherClassId = classId("kotlinx.coroutines", "CoroutineDispatcher")
}
