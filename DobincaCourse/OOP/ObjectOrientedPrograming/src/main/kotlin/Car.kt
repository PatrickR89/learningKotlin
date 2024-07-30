class Car(
    name: String,
    val model: String,
    val color: String,
    val doors: Int
) {
    val name = name.trim()
//    var model = model
//    var color = color
//    var doors = doors

    fun move() {
        println("$name Moving")
    }

    fun stop() {
        println("$name Stopping")
    }
}