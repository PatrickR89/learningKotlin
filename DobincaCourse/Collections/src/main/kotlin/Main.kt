// collections have mutable and immutable versions

fun main(args: Array<String>) {
    val names = listOf<String>("Name 1", "Name 2", "Name 3") // immutable
    val mutableNames = mutableListOf<String>("Some Name", "Another name")
    mutableNames.add("Name 4")
    mutableNames.removeAt(0)

    val namesSet = mutableSetOf<String>("Name 1", "Name 2", "Name 1") // stores only unique values; when classes used in Set, use data class
    val mappedUser = mutableMapOf<Int,String>(1 to "Maria", 2 to "Alex", 3 to "John") // Dictionary;
    mappedUser[12] = "Barack"
}