fun main() {

}

fun getData() {} // by default public
private fun fetchData() {}

internal class SomePlayer(name: String): Player(name) // internal restricts to module

open class User {
    var firstName: String = ""
    var lastName: String = ""
    private var age: Int = 1 // not accessible outside class, not even in inheriting classes
    protected var children: String = "" // not accessible outside class or outside inheriting classes
}
