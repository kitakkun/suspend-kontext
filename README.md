# suspend-kontext

suspend-kontext is a Kotlin Compiler Plugin that allows you to specify the default coroutine context for suspend functions using annotations.

## Motivation

Switching coroutine contexts typically requires the use of `withContext`, like this:
```kotlin
suspend fun loadText(file: File): String {
    return withContext(Dispatchers.IO) {
        return@withContext file.readText()
    }
}
```
Combining `withContext` and `return` can make your code less readable and unnecessarily complex.

With suspend-kontext, you can simplify this code by using annotations:
```kotlin
@IOContext
suspend fun loadText(file: File): String {
    return file.readText()
}
```

This approach makes your code more concise and improves readability by explicitly defining the coroutine context at the function level.

> [!NOTE]
> This project is still in the conceptual stage. Stay tuned for updates if you're interested!

