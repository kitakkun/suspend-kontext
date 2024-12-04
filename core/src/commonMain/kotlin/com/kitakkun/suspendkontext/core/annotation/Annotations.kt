package com.kitakkun.suspendkontext.core.annotation

import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

/**
 * Indicates that a function is a candidate for compiler modification via meta-annotation logic.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
private annotation class SuspendKontext

/**
 * Ensures that the annotated suspending function is executed in the Dispatchers.IO context by default.
 *
 * Example usage:
 * ```kotlin
 * @IOContext
 * suspend fun loadData(): String {
 *     delay(100)
 *     return "loaded"
 * }
 * ```
 * This is equivalent to:
 * ```kotlin
 * suspend fun loadData(): String {
 *     return withContext(Dispatchers.IO) {
 *         delay(100)
 *         "loaded"
 *     }
 * }
 * ```
 * The compiler plugin will automatically apply this transformation at the IR level,
 * ensuring consistency and reducing boilerplate.
 */
@Target(AnnotationTarget.FUNCTION)
@SuspendKontext
annotation class IOContext

/**
 * Ensures that the annotated suspending function is executed in the Dispatchers.Main context by default.
 *
 * For more information and behavior details, see [IOContext].
 */
@Target(AnnotationTarget.FUNCTION)
@SuspendKontext
annotation class MainContext

/**
 * Ensures that the annotated suspending function is executed in the Dispatchers.Default context by default.
 *
 * For more information and behavior details, see [IOContext].
 */
@Target(AnnotationTarget.FUNCTION)
@SuspendKontext
annotation class DefaultContext

/**
 * Ensures that the annotated suspending function is executed in the Dispatchers.Unconfined context by default.
 *
 * For more information and behavior details, see [IOContext].
 */
@Target(AnnotationTarget.FUNCTION)
@SuspendKontext
annotation class UnconfinedContext

/**
 * Enables the creation of custom coroutine context annotations by specifying an implementation class.
 *
 * The `dispatcher` must:
 * - Be a subclass of [CoroutineDispatcher].
 * - Be an `object` or a `class` with an empty (no-argument) constructor.
 *
 * Example:
 * ```kotlin
 * object MyCustomDispatcher : CoroutineDispatcher() { ... }
 *
 * @CustomContext(MyCustomDispatcher::class)
 * @Target(AnnotationTarget.FUNCTION)
 * annotation class MyContext
 *
 * @MyContext
 * suspend fun performTask() {
 *     // Executes in MyCustomDispatcher
 * }
 * ```
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
@SuspendKontext
annotation class CustomContext(val dispatcher: KClass<*>)
