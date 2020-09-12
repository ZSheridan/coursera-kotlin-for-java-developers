package games.gameOfFifteen

import board.*
import games.game.Game
import javax.swing.JPanel

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
/*
 * All those 'width' and 'board.width' could be replaced
 * with the actual numbers, since this is the GameOfFifteen.
 * I just chose not to.
 */

fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        board.addValues(initializer)
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean =
        with(board.getAllCells()) {
            all { board[it] == null || board[it] == indexOf(it) + 1 }
        }

    override fun processMove(direction: Direction) {
        with(board) {
            find { it == null }?.let { nullCell ->
                nullCell.getNeighbour(direction.reversed())?.let { neighbour ->
                        set(nullCell, get(neighbour))
                        set(neighbour, null)
                }
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

}

fun GameBoard<Int?>.addValues(initializer: GameOfFifteenInitializer) {
    for (i in 1..width) {
        for (j in 1..width) {
            if (i * j != width * width) {
                this[getCell(i, j)] = initializer.initialPermutation[(i - 1) * width + j - 1]
            }
        }
    }
}
