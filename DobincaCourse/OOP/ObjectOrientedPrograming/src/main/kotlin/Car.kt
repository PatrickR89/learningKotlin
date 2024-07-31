class Car(
    name: String,
    val model: String,
    color: String,
    val doors: Int
) : Vehicle(name, color) {
    override fun move() {
        println("Car $name is moving.")
    }
}

// classes are by default defined as final and cannot be inherited
// they must be marked "open" in order to enable inheritance
open class Vehicle(val name: String, color: String) {
    open fun move() {
        println("$name Moving")
    }

    fun stop() {
        println("$name Stopping")
    }
}

// abstract classes similar to interface, but they can contain params

abstract class VehicleAbs() {
    abstract fun move()
    abstract fun stop()
}

class RacingCar(): VehicleAbs() {
    override fun move() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

}

interface Engine {
    val engine: String
    fun startEngine()

}

class FastCar(engine: String): Engine {
    override val engine: String = engine
    override fun startEngine() {
        TODO("Not yet implemented")
    }
}
