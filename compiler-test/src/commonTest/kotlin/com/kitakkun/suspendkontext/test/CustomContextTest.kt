package com.kitakkun.suspendkontext.test

import com.kitakkun.suspendkontext.core.annotation.CustomContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@CustomContext(dispatcher = SingletonDelegatedDispatcher::class)
annotation class SingletonContext

@CustomContext(dispatcher = MyDispatcher::class)
annotation class MyContext

object SingletonDelegatedDispatcher : CoroutineDispatcher() {
    private val coroutineDispatcher = Dispatchers.Default

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        coroutineDispatcher.dispatch(context, block)
    }
}

class MyDispatcher : CoroutineDispatcher() {
    private val coroutineDispatcher = Dispatchers.Default

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        coroutineDispatcher.dispatch(context, block)
    }
}

class CustomContextTest {
    @Test
    fun objectContext() {
        @SingletonContext
        suspend fun function() {
            assertEquals(coroutineContext[ContinuationInterceptor], SingletonDelegatedDispatcher)
        }

        runBlocking {
            function()
        }
    }

    @Test
    fun customContext() {
        @MyContext
        suspend fun function() {
            assertIs<MyDispatcher>(coroutineContext[ContinuationInterceptor])
        }

        runBlocking {
            function()
        }
    }
}
