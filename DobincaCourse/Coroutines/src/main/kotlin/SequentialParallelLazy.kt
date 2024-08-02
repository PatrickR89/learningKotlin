import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

fun main() {
    val start  = System.currentTimeMillis()
    val parentJob = CoroutineScope(Dispatchers.Default).launch {
        val job3 = async(start = CoroutineStart.LAZY) {
            val result3 = getData3(Thread.currentThread().name)
            println(result3)
            return@async result3
        }
        // job3.join() -> serial execution when launch {}
        val job1 = async(start = CoroutineStart.LAZY) {
            val result1 = getData(Thread.currentThread().name)
            println(result1)
            return@async result1
        }
        // job1.join() -> serial execution
        val job2 = async(start = CoroutineStart.LAZY) {
            val result2 = getData2(Thread.currentThread().name)
            println(result2)
            return@async result2
        }
        // job2.join() -> serial execution
        println("Resulting final data \n" + job1.await() + "\n" + job2.await() + "\n" + job3.await())
    }

    runBlocking {
        parentJob.join()
    }

    println("Execution time: ${System.currentTimeMillis() - start}")
}

private suspend fun setTextOnMainThread(input: String) {
    withContext(Main) {
    // returns execution to the main thread
    }
}