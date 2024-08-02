import kotlinx.coroutines.*

fun main(args: Array<String>) {
    println("Hello World!")

    println("Program start: ${Thread.currentThread().name}")

//    thread {
//        println("Program task: ${Thread.currentThread().name}")
//        println("Some work started.")
//        Thread.sleep(2000) // pretend work
//        println("Some work finished.")
//    }

//    val job = CoroutineScope(Dispatchers.Default).launch {
//        println("Program task: ${Thread.currentThread().name}")
//        println("Some work started.")
//        delay(2000) // pretend work
//        println("Some work finished.")
//    } // by default app execution does not wait for coroutine finish
//    Thread.sleep(2500)

//    runBlocking {
//        job.join()
//    }
//
//    println("Main program ends.")
    // coroutine async/await
    // GlobalScope -> scope which is not bound to any view/activity
    val parentJob  = CoroutineScope(Dispatchers.Default).launch {
        val job1 = launch {
            try {
                // set withTimeout to ensure task does not take too long
                val result1 = withTimeout(2500) {
                    getData(Thread.currentThread().name)
                }
                println(result1)
            } catch (exception: CancellationException) {
                println("Exception caught.")
            } finally {
                println("Task closed")
            }

            if (!isActive) {
                return@launch
            }
//            return@async result1
        }
        // cancel accepts argument CancellationException
        //job1.cancel() // .cancelAndJoin()
        //job1.join() // block execution until first job is done
        val job2 = launch {
            val result2 = getData2(Thread.currentThread().name)
            println(result2)
            // only async {} returns result
//            return@async result2
        }

//        println("Resulting final data \n" + job1.await() + "\n" + job2.await())
    }

    runBlocking {
        parentJob.join()
    }

    parentJob.invokeOnCompletion {
        it?.let {
            println("Parent job failed.")
        } ?: println("Parent job success.")
    }
}

// suspend function is equivalent to async func
suspend fun getData(threadName: String): String {
    println("Getting data. Thread: $threadName")
    delay(2000)
    println("Data fetched, returning")
    return "Data"
}

suspend fun getData2(threadName: String): String {
    println("Getting data2. Thread: $threadName")
    delay(2000)
    println("Data2 fetched, returning")
    return "Data2"
}