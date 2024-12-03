package com.kitakkun.suspendkontext.core.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS)
private annotation class SuspendKontext

@Target(AnnotationTarget.FUNCTION)
@SuspendKontext
annotation class IOContext

@Target(AnnotationTarget.FUNCTION)
@SuspendKontext
annotation class MainContext

@Target(AnnotationTarget.FUNCTION)
@SuspendKontext
annotation class DefaultContext

@Target(AnnotationTarget.FUNCTION)
@SuspendKontext
annotation class UnconfinedContext
