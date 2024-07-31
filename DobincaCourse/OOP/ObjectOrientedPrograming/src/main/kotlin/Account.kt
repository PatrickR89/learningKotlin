class Account(val accountName: String) {
    private var balance = 0
    private var transactions = mutableListOf<Int>()

    fun deposit(amount: Int) {
        if (amount > 0) {
            transactions.add(amount)
            balance += amount
            println("$amount added to balance. New balance: ${this.balance}")
        } else {
            println("Invalid amount entered. Amount must be larger than 0")
        }
    }

    fun withdraw(amount: Int) {
        if (-amount > 0) {
            println("Cannot withdraw negative value.")
            return
        }

        if (amount > balance) {
            println("Amount not available, max to withdraw: ${this.balance}")
            return
        }

        transactions.add(-amount)
        balance -= amount

        println("$amount removed from balance. New balance: ${this.balance}")
    }

    fun recalculateBalance(): Int {
        this.balance = 0
        this.transactions.forEach {
            this.balance += it
        }

        return balance
    }
}