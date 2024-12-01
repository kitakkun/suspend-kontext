package com.kitakkun.suspendkontext.compiler.backend

import org.jetbrains.kotlin.javac.resolve.classId

object SuspendKontextConsts {
    val ioContextAnnotationClassId = classId("com.kitakkun.suspendkontext.core.annotation", "IOContext")
}