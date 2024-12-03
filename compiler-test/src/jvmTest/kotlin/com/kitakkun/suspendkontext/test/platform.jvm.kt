package com.kitakkun.suspendkontext.test

import kotlinx.coroutines.runBlocking

actual fun platform(): String = "jvm"

fun a () = runBlocking {  }