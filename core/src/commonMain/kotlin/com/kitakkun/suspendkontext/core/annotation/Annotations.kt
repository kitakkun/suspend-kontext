package com.kitakkun.suspendkontext.core.annotation

@Target(AnnotationTarget.FUNCTION)
annotation class IOContext

@Target(AnnotationTarget.FUNCTION)
annotation class MainContext

@Target(AnnotationTarget.FUNCTION)
annotation class DefaultContext

@Target(AnnotationTarget.FUNCTION)
annotation class UnconfinedContext
