fun main() {
    val numbers = listOf("one", "two", "three", "four", "five", "six")
    val rangeSlice = numbers.slice(1..3)
    val rageStepSlice = numbers.slice(0..4 step 2)
    val specificIndexSlice = numbers.slice(setOf(1, 3, 0))

    // .take(size) - takes specified number of elements
    // .takeLas(size) - takes last elements with specified size
    // .drop -> opposite to take, drops elements from array
    // .chunk(size) -> creates matrix
    // .chunk(size) { map elements }
    // .windowed -> similar to .chunk but with more flexibility

    // .elementAt(index)
    // .first { it -> conditions }
    // .isEmpty
}