fun main() {
    val firstLambda = {a: Int -> println("a = $a")} // Kotlin lambda == Swift closure

    add(1,5, firstLambda)
    add(1, 6) {
        println("New value = $it")
    } // same extraction to outside function call as in Swift
    
}

fun add(a: Int, b: Int, action: (Int) -> Unit) {
    println("a + b = ${ a + b }")
    action(a + b)
}