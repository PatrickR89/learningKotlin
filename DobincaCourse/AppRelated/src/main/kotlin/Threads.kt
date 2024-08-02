import kotlin.concurrent.thread

fun main() {

    println("Hello world! 1")
    println("Hello world! 2")
    println("Hello world! 3")
    println("Hello world! 4")
    println("Hello world! 5")
    println("Hello world! 6")
    println("Hello world! 7")
    thread {
        Thread.sleep(500)
        println("Hello world! 8")
        println("Hello world! 9")
    }
    println("Hello world! 10")
    println("Hello world! 11")
    println("Hello world! 12")
}

