package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean =
    with(permutation) {
        mapIndexed { a, i ->
            mapIndexed { b, j -> if (b > a) Pair(i,j) else Pair(0, 0) } }
            .flatten().count { (i,j) -> i > j } % 2 == 0
    }