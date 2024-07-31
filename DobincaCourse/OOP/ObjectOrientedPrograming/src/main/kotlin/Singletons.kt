// class as singleton
class Database private constructor() {
    companion object {
        private  var instance: Database? = null

        fun getInstance(): Database? {
            instance ?: {
                instance = Database()
            }

            return instance
        }
    }
}

// singleton object, when object is created there is only one instance of it
// specific to Kotlin
object DbHandler {
    init {
        println("DbHandler created")
    }
}