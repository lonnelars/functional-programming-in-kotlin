import kotlin.collections.List

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments at Run/Debug configuration
    println("Program arguments: ${args.joinToString()}")
}

class Cafe {
    fun buyCoffee(cc: CreditCard): Pair<Coffee, Charge> = TODO()

    fun buyCoffees(cc: CreditCard, n: Int): Pair<List<Coffee>, Charge> {
        val purchases: List<Pair<Coffee, Charge>> = List(n) { buyCoffee(cc) }

        val (coffees, charges) = purchases.unzip()

        listOf(1,2,3).dropWhile { it < 2 }

        return Pair(coffees, charges.reduce { c1, c2 -> c1.combine(c2) })
    }
}

data class Coffee(val price: Float)

data class Charge(val cc: CreditCard, val amount: Float) {
    fun combine(other: Charge): Charge =
        if (cc == other.cc)
            Charge(cc, amount + other.amount)
        else throw Exception("Cannot combine charges to different cards")
}

fun List<Charge>.coalesce(): List<Charge> = this.groupBy { it.cc }.values
    .map { it.reduce { a, b -> a.combine(b) } }

class CreditCard
