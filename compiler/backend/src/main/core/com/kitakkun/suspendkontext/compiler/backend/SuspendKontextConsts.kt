package com.kitakkun.suspendkontext.compiler.backend

import org.jetbrains.kotlin.javac.resolve.classId

object SuspendKontextConsts {
    val ioContextAnnotationClassId = classId("com.kitakkun.suspendkontext.core.annotation", "IOContext")
    val defaultContextAnnotationClassId = classId("com.kitakkun.suspendkontext.core.annotation", "DefaultContext")
    val mainContextAnnotationClassId = classId("com.kitakkun.suspendkontext.core.annotation", "MainContext")
    val unconfinedContextAnnotationClassId = classId("com.kitakkun.suspendkontext.core.annotation", "UnconfinedContext")
    val customContextAnnotationClassId = classId("com.kitakkun.suspendkontext.core.annotation", "CustomContext")
}
