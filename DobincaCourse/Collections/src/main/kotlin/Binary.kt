fun main() {
    val list = mutableListOf<Int>()
    list[list.binarySearch(24)] // -> builtin binary search for increasing array
}

private fun searchElement(searchedElement: Int, numbers: MutableList<Int>): Int {
    var low: Int = 0
    var high = numbers.size - 1

    while (low <= high) {
        val mid = (low + high) / 2
        val cmp = numbers[mid].compareTo(searchedElement)

        if(cmp < 0) {
            low = mid + 1
        } else if (cmp > 0) {
            low = mid - 1
        } else {
            return numbers[mid]
        }
    }

    return -1
}