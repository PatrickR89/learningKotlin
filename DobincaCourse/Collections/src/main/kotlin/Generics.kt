fun main() {

    val footballPlayers = mutableListOf<FootballPlayer>()
    for (i in 1..5) {
        val player = FootballPlayer("Player $i")
        footballPlayers.add(player)
    }

    val gamesPlayers: MutableList<GamesPlayer> = createPlayers {
        GamesPlayer("Player $it")
    }

    val footballTeam = Team<Player>("Dragons")
//    footballTeam.addPlayers(footballPlayers)

//    val gamesTeam = Team<CounterStrikePlayer>("Dada", games)
}

open class Player(val name: String)

class FootballPlayer(name: String): Player(name)
class BaseballPlayer(name: String): Player(name)
open class GamesPlayer(name: String): Player(name)
class CounterStrikePlayer(name: String): GamesPlayer(name)

// on generics keyword "out" includes all inheriting classes
// on generics keyword "in" includes all super classes


class Team<T: Player>(val name: String, val players: MutableList<T> = mutableListOf()) {

    fun addPlayer(player: T) {
        if (players.contains(player)) {
            println("Player ${player.name} is in the team! No adding.")
        } else {
            players.add(player)
            println("Player ${player.name} added to the team.")
        }
    }

    fun addPlayers(vararg newPlayers: T) {
        players.addAll(newPlayers)
    }
}

fun <T: Player> createPlayers(factory: (Int) -> T): MutableList<T> {
    val players = mutableListOf<T>()
    for (i in 1..5) {
        val player = factory(i)
        players.add(player)
    }

    return players
}

// T must be reified otherwise it is removed at compile time
// reified also pulls inline as required

inline fun <reified T> filterByType(list: List<Any>): List<T> {
    var filtered = mutableListOf<T>()

    list.forEach {
        if (it is T) {
            filtered.add(it)
        }
    }

    return filtered
}

// Where; 2 Upperbounds

interface Listener {
    fun listen()
}

class SoccerPlayer(name: String): Player(name), Listener {
    override fun listen() {

    }
}

class GenericTeam<T>(val name: String, val players: MutableList<T> = mutableListOf()) where T: Player, T: Listener {

    fun addPlayer(player: T) {
        if (players.contains(player)) {
            println("Player ${player.name} is in the team! No adding.")
        } else {
            players.add(player)
            println("Player ${player.name} added to the team.")
        }
    }

    fun addPlayers(vararg newPlayers: T) {
        players.addAll(newPlayers)
    }
}
