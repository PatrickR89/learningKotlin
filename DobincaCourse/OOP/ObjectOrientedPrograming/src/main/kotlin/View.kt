open class View() {

    open fun draw() {
        println("Drawing view")
    }
}

open class SquareButton(val orientation: String, val text: String): View() {
    override fun draw() {
        super.draw()
        println("Drawing button. With name: $text")
    }
}

class RoundButton(
    text: String,
    orientation: String,
    private val cornerRadius: Int
): SquareButton(orientation, text) {
    override fun draw() {
        super.draw()
        println("Adding corner radius: $cornerRadius")
    }
}