fun main() {
    val user = User()

    val result = with(user) {
        firstName = "Alex"
        lastName = "Dobinca"
        age = 23

        age
    } // scope functions enables access to object without first path part repetition, also returns value

    val newUser = User().apply {
        firstName = "John"
        lastName = "Wick"
        age = 50
    } // whole applied expression is returned value

    val doppelganger = Doppelganger("John", "wick", 99).also {
        println(it)
    } // also applies a completionHandler after the object was created

    val text: String? = null

    text?.let {
        // execute only if not null
        println(it)
        // last line return in closure
    }

    val runner: User? = null

    runner?.run {
        println(firstName)
    } // combo of with and let scope functions
}

class User() {
    var firstName = ""
    var lastName = ""
    var age = -1
}

data class Doppelganger(
    val firstName: String,
    val lastName: String,
    var age: Int
)