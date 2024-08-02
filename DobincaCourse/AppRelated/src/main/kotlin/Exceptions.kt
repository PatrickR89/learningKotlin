fun main() {
    val a = 2
    val b = 0

    try {
        println(a / b)
    } catch (error: ArithmeticException) {
        println("No dividing by zero! Message: ${error.message}")
    } finally {
        println("Dividing by zero done.")
    }

    val result = try {
        a / b
    } catch (error: ArithmeticException) {
        println("No dividing by zero! Setting default to result. Message: ${error.message}")
        0
    } finally {
        println("Dividing by zero done. Result is set.")
    }

    println(result)
}