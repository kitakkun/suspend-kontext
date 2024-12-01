package com.kitakkun.suspendkontext.test

import com.kitakkun.suspendkontext.core.annotation.DefaultContext
import com.kitakkun.suspendkontext.core.annotation.IOContext
import com.kitakkun.suspendkontext.core.annotation.MainContext
import com.kitakkun.suspendkontext.core.annotation.UnconfinedContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.coroutineContext
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ContextAssertionTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        if (platform() == "native" || platform() == "android") {
            Dispatchers.setMain(Dispatchers.Unconfined)
        }
    }

    @Test
    fun testIO() {
        @IOContext
        suspend fun function() {
            assertEquals(Dispatchers.IO, coroutineContext[ContinuationInterceptor])
        }

        runBlocking {
            function()
        }
    }

    @Test
    fun testDefault() {
        @DefaultContext
        suspend fun function() {
            assertEquals(Dispatchers.Default, coroutineContext[ContinuationInterceptor])
        }

        runBlocking {
            function()
        }
    }

    @Test
    fun testUnconfined() {
        @UnconfinedContext
        suspend fun function() {
            assertEquals(Dispatchers.Unconfined, coroutineContext[ContinuationInterceptor])
        }

        runBlocking {
            function()
        }
    }

    @Test
    fun testMain() {
        @MainContext
        suspend fun function() {
            assertEquals(Dispatchers.Main, coroutineContext[ContinuationInterceptor])
        }

        runBlocking {
            function()
        }
    }
}
