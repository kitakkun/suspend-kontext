package com.kitakkun.suspendkontext.test

import com.kitakkun.suspendkontext.core.annotation.IOContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext
import kotlin.test.Test

class TestHoge {
    @Test
    fun test() {
        @IOContext
        suspend fun sample() {
            println(coroutineContext)
        }

        runBlocking {
            sample()
        }
    }
}