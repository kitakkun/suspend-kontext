# suspend-kontext

[![Maven Central](https://img.shields.io/maven-central/v/com.kitakkun.suspendkontext/core)](https://central.sonatype.com/search?namespace=com.kitakkun.suspendkontext)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.0--2.1.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![License](https://img.shields.io/badge/license-Apache-blue.svg)](https://github.com/kitakkun/suspend-kontext/blob/master/LICENSE)
![Platform](https://img.shields.io/badge/platform-Android_JVM_iOS_macOS_watchOS_tvOS_Linux_Windows-blue)

suspend-kontext is a Kotlin Compiler Plugin that allows you to specify the default coroutine context for suspending functions using annotations.

## Motivation

Switching coroutine contexts typically requires the use of `withContext`, like this:
```kotlin
suspend fun loadText(file: File): String {
    return withContext(Dispatchers.IO) {
        return@withContext file.readText()
    }
}
```
Combining `withContext` and `return` can make your code less readable.

With suspend-kontext, you can simplify this code by using annotations:
```kotlin
@IOContext
suspend fun loadText(file: File): String {
    return file.readText()
}
```

This approach makes your code more concise and improves readability by explicitly defining the coroutine context at the function level.
You can use `@IOContext`, `@DefaultContext`, `@MainContext`, and `@UnconfinedContext` for now.

## Installation

```kotlin
repositories {
    mavenCentral()
}

plugins {
    id("com.kitakkun.suspend-kontext") version "<version>"
}
```
