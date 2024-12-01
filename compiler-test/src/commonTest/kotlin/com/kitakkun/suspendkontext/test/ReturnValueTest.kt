package com.kitakkun.suspendkontext.test

import com.kitakkun.suspendkontext.core.annotation.DefaultContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class ReturnValueTest {
    @Test
    fun test() {
        @DefaultContext
        suspend fun function(): String {
            delay(1)
            return "Hello, World!"
        }

        runBlocking {
            val value = function()
            assertEquals("Hello, World!", value)
        }
    }
}