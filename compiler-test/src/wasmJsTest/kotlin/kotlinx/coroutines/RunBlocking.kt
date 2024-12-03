package kotlinx.coroutines

import kotlin.coroutines.CoroutineContext

fun <T> runBlocking(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T {
}
