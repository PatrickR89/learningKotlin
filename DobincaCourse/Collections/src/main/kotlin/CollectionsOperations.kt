fun main() {
    val numbers = setOf(1, 2, 3, 4)
    println(numbers.map { it * 10 })

    // Zipping

    val colors = listOf<String>("red", "yellow", "grey")
    val animals = listOf<String>("fox", "bird", "bear")

    println(colors zip animals) // creates collection of tuples by matching indices

    val numPairs = listOf("one" to 1, "two" to 2) // creates two key array and value array
    println(numPairs.unzip())

    val numberList = listOf("one", "two", "three")
    println(numberList.associateWith { it.length })
    println(numberList.associateBy { it.first().uppercase() }) // creates dict

    val numSet = listOf(setOf(1,2,3), setOf(4,5,6), setOf(7,8,9))
    val flattened = numSet.flatten()
    val stringRepresented = numberList.joinToString() // joinTo adds string representation to a predefined text
    println(numberList.joinToString(separator = "|"))
    println(numberList.joinToString(limit = 2, truncated = "..."))

    // partition
    val (match, rest) = numberList.partition { it.length >= 3 }

    // + and -  in collections -> + adds a collection to another collection
    // - compares operands and removes from first elemnts which match second collection
}

// mapIndexedNotNull -> useful!!!