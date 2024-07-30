class User(name: String, var lastName: String, var age: Int) {
    var name: String

    init {
        if (name.lowercase().startsWith('a')) {
            this.name = name
        } else {
            this.name = "User"
        }
    }
}