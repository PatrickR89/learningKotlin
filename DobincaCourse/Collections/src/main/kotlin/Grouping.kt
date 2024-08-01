fun main() {
    val numbers = listOf("one", "two", "three", "four")
    println(numbers.groupBy { it.first().uppercase() })
    println(numbers.groupBy(keySelector = {it.first()}, valueTransform = {it.uppercase()}))
}