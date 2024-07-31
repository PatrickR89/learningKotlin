fun main(args:Array<String>) {
//    val car = Car("Ford", "Mustang", "Red", 2 )
    val user = User("John", "McCay", 67)
    val secondUser = User("Molly")
    val thirdUser = User("Bobby", "Allay")
    println(user)

//    val result = Calculator.sum(2, 5)
//    println(result)
//
//    val dbInstance  = Database.getInstance()
//    val handlerOne = DbHandler
//    val handlerTwo = DbHandler
//
//    println(handlerTwo)
//    println(handlerOne)

    // lazy init for param
//    val userLazy by lazy {
//        User("Harry",  "Anderson", 87)
//    }
//    val user1 = User("Henry", "Krause", 66)
//    userLazy.age

    /*
    // iterating over enum cases!
    for (direction in Direction.values()) {
        println(direction)

        when(direction) {
            Direction.east -> {
                direction.printData()
            }
            else -> {
                continue
            }
        }
    }
     */

//    val listView = ListView(arrayOf("Some", "One", "Third", "Mack", "John", "Car", "Bike"))
//
//    listView.ListViewItems().displayItem(2)

    /*
    val account = Account("Some Account")

    account.deposit(150)
    account.withdraw(14)
    account.withdraw(75)
    val recalced = account.recalculateBalance()
    println("Recalculated: $recalced")

     */

//    val button = RoundButton("Open", "Portrait", 15)
//    button.draw()
//    val data = getData(Result.Success("Data fetched"))
//    val dataError = getData(Result.Error("Data not found"))

}

enum class Direction(var direction: String, var distance: Int) {
    north("north", 10),
    south("south", 20),
    east("east", 15),
    west("west", 40);

    fun printData() {
        println("Direction: $direction, distance: $distance")
    }
}

class ListView(val items: Array<String>) {
    inner class ListViewItems() {
        fun displayItem(position: Int) {
            if (position >= items.size || position < 0) {
                println("Invalid index selected.")
                return
            }
            println(items[position])
        }
    }
}

fun getData(result: Result) {
    when(result) {
        is Result.Error -> {
            result.showMessage()
        }

        is Result.Success -> {
            result.showMessage()
        }

        is Result.Progress -> {
            result.showMessage()
        }
    }
}

// sealed class useful in Kotlin instead of enum, as it is exhaustive
// compile time safe
sealed class Result(val message: String) {
    fun showMessage() {
        println("Message: $message")
    }
    class Success(message: String): Result(message)
    class Error(message: String): Result(message)
    class Progress(message: String): Result(message)
}