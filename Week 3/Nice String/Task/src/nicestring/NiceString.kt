package nicestring

fun String.isNice(): Boolean {
    var count = if (listOf("bu","ba","be").none { it in this }) 1 else 0
    count += if (count { it in "aeiou" } >= 3) 1 else 0
    count += if (zipWithNext().any { it.first == it.second }) 1 else 0
    return count>=2
}