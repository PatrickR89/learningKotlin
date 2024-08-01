fun main() {
    val numbers = mutableListOf(6, 2, 4, 5, 7, 10, 33, 11, 56)
    val sum = numbers.sum()
    val count = numbers.count()

    numbers.sorted().forEach {
        println(it)
    }

    val laptops = listOf<Laptop>(Laptop("Apple", 2023, 20, 1999))
    laptops.sortedBy { it.price }
    laptops.sortedWith(compareBy { it.price })
}

data class Laptop(val brand: String, val year: Int, val ram: Int, val price: Int): Comparable<Laptop> {
    override fun compareTo(other: Laptop): Int {
        if (this.price > other.price) return 1
        else if (this.price < other.price) return -1
        else return 0
        // returns 1 if this > other
        // returns -1 if this < other
        // returns 0 if this == other
    }
}
// comparable required in order to enable searching and comparing within collections
// not implemented by default

// used with .sortedWith
// shorter version: .sortedWith(comparedBy { it.ram })
// shortest version: .sortBy { it.ram }
class RamComparator: Comparator<Laptop> {
    override fun compare(o1: Laptop, o2: Laptop): Int {
        if (o1.ram > o2.ram) return 1
        else if (o1.ram < o2.ram) return -1
        else return 0
    }
}