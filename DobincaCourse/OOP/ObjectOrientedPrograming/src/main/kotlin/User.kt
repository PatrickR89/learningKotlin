// data class overrides original functions in class: toString, equals and toHash

data class User( var firstName: String, var lastName: String = "LastName", var age: Int = 999) {
//    var name: String = firstName
//        get() {
//            return field // field identifier for param getter and setter
//        }
//        set(value) {
//            field = value
//        }
//
//    lateinit var favoriteMovie: String // avoid instant initialization, assign later -> not the best option, easy bug source
    // lateinit can be used with classes and types which are not primitive

//    init {
//        if (name.lowercase().startsWith('a')) {
//            this.name = name
//        } else {
//            this.name = "User"
//        }
//    }

    init {
        println("User $firstName created")
    }

    constructor(name: String) : this(name, "LastName", 0) {
        println("Second constructor used with name: $name.")
    }
    constructor(name: String, lastName: String) : this(name, lastName, 0) {
        println("Third constructor used with name: $name")
    }

//    override fun equals(other: Any?): Boolean {
//        if (this === other) {
//            return true
//        }
//
//        if (other is User) {
//            return this.firstName == other.firstName &&
//                    this.lastName == other.lastName &&
//                    this.age == other.age
//        }
//
//        return false
//    }
//
//    override fun hashCode(): Int {
//        return 0
//    }
//
//    override fun toString(): String {
//        return "User(firstname: $firstName, lastName: $lastName, age: $age)"
//    }
}