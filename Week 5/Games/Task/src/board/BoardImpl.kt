package board

import board.Direction.*
import java.lang.IllegalArgumentException

open class SquareBoardImpl (final override val width: Int) : SquareBoard {

    private val cells = IntRange(1, width)
        .flatMap { i -> IntRange(1, width).map { j -> Cell(i,j) } }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
        if (i > width || j > width || i < 1 || j < 1) null
        else cells.first { it == Cell(i,j) }

    override fun getCell(i: Int, j: Int): Cell =
        getCellOrNull(i,j) ?: throw IllegalArgumentException(
            "Coordinate [$i,$j] out of bounds for this board.")

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
        jRange.getRange().map { index -> getCell(i,index) }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
        iRange.getRange().map { index -> getCell(index,j) }

    private fun IntProgression.getRange(): IntProgression =
        when {
            last > width -> first..width
            first > width -> width downTo last
            else -> this
        }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(i-1,j)
            DOWN -> getCellOrNull(i+1,j)
            RIGHT -> getCellOrNull(i,j+1)
            LEFT -> getCellOrNull(i,j-1)
        }
    }
}

class GameBoardImpl<T> (width: Int) : SquareBoardImpl(width), GameBoard<T> {

    private var mapCells = getAllCells()
        .asSequence()
        .map { cell -> Pair<Cell, T?>(cell, null) }
        .toMap()
        .toMutableMap()

    override fun get(cell: Cell): T? {
        require(cell in mapCells.keys) { "$cell is out of range for the board." }
        return mapCells[cell]
    }

    override fun set(cell: Cell, value: T?) {
        require(cell in mapCells.keys) { "$cell is out of range for the board." }
        mapCells[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
        mapCells.keys.filter { predicate(mapCells[it]) }

    override fun find(predicate: (T?) -> Boolean): Cell? =
        mapCells.keys.find { predicate(mapCells[it]) }

    override fun any(predicate: (T?) -> Boolean): Boolean =
        mapCells.keys.any { predicate(mapCells[it]) }

    override fun all(predicate: (T?) -> Boolean): Boolean =
        mapCells.keys.all { predicate(mapCells[it]) }
}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)