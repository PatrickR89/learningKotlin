import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
//    val clickListener = ClickListener()
//    val loginButton = Button("Login", 287163921, object : OnClickListener {
//        override fun onClick() {
//            TODO("Not yet implemented")
//        }
//    })

    val user = SimpleUser()
    with(user) {
        firstName = "Alex"
        lastName = "Dobinca"
    }

    println(user.firstName)
}

// object expression

class Button(val text: String, val id: Int, onClickListener: OnClickListener) {

}

class ClickListener(): OnClickListener {
    override fun onClick() {
        println("Clicked")
    }

}

interface OnClickListener {
    fun onClick()
}

// delegation

interface A {
    fun printOne()
}

interface B {
    fun printTwo()
}

open class FirstDelegate: A {
    override fun printOne() {
        TODO("Not yet implemented")
    }
}

open class SecondDelegate: B {
    override fun printTwo() {
        TODO("Not yet implemented")
    }
}

class SomeApp: A by FirstDelegate(), B by SecondDelegate() {
    override fun printOne() {
        TODO("Not yet implemented")
    }

    override fun printTwo() {
        TODO("Not yet implemented")
    }
}

class FormatDelegate: ReadWriteProperty<Any?, String> {
    private var formattedString: String = ""

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return formattedString
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        formattedString = value.lowercase()
    }
}

class SimpleUser {
    var firstName by FormatDelegate()
    var lastName by FormatDelegate()
}