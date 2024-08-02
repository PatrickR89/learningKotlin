import kotlinx.coroutines.*
import kotlin.Exception

val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
    println("Error in a child: ${throwable.message}")
}
// CoroutineExceptionHandler handles all possible exceptions in all throwable
// functions which are called withing CoroutineScope, which is launched with the handler.
// If any of the children fails, coroutine fails (if supervisorScope not used).

fun main() {
    val parentJob = CoroutineScope(Dispatchers.Default).launch(handler) {
        // supervisorScope handles each task individually,
        // ensuring that if one of the child tasks fails, other can be
        // successfully executed, and the error does not propagate.
        supervisorScope {
            val job3 = launch {
                val result3 = getData3(Thread.currentThread().name)
                println(result3)
            }
            val job1 = launch {
                val result1 =getData(Thread.currentThread().name)
                println(result1)
            }

            val job2 = launch {
                val result2 = getData2(Thread.currentThread().name)
                println(result2)
            }
        }
    }

    runBlocking {
        parentJob.join()
    }
}

suspend fun getData3(threadName: String): String {
    println("Getting data3. Thread: $threadName")
//    throw Exception("Data 3 failed")
    // if instead of Exception only CancellationException is thrown,
    // it does not propagate to other child tasks within coroutineScope
    delay(2000)
    println("Data3 fetched, returning")
    return "Data3"
}