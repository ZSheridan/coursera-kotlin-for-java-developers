package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

class Rational (first: BigInteger, second: BigInteger) : Comparable<Rational> {

    private val numerator: BigInteger
    private val denominator: BigInteger

    init {
        require(second != ZERO) {"Denominator must be non-zero"}
        val gcd = first.gcd(second)
        val sign = second.signum().toBigInteger()
        numerator = sign * first / gcd
        denominator = sign * second / gcd
    }

    operator fun plus(other: Rational): Rational {
        val numerator =
            this.numerator * other.denominator +
                    other.numerator * this.denominator
        val denominator = this.denominator * other.denominator

        return Rational(numerator, denominator)
    }

    operator fun minus(other: Rational) = this + (-other)

    operator fun times(other: Rational): Rational {
        val numerator = this.numerator * other.numerator
        val denominator = this.denominator * other.denominator

        return Rational(numerator, denominator)
    }

    operator fun div(other: Rational): Rational {
        val numerator = this.numerator * other.denominator
        val denominator = this.denominator * other.numerator

        return Rational(numerator, denominator)
    }

    operator fun unaryMinus() = Rational(-numerator, denominator)

    override fun compareTo(other: Rational): Int {
        val numerator =
            this.numerator * other.denominator - other.numerator * this.denominator
        return numerator.signum()
    }

    override fun toString(): String {
        return if (this.denominator == ONE) {
            "${this.numerator}"
        } else {
            "${this.numerator}/${this.denominator}"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational

        if (numerator != other.numerator) return false
        if (denominator != other.denominator) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }

}

infix fun Int.divBy(denominator: Int) =
    Rational(this.toBigInteger(),denominator.toBigInteger())

infix fun Long.divBy(denominator: Long) =
    Rational(this.toBigInteger(),denominator.toBigInteger())

infix fun BigInteger.divBy(denominator: BigInteger) =
    Rational(this,denominator)

fun String.toRational(): Rational {
    fun String.toBigIntegerOrFail() =
        toBigIntegerOrNull() ?: throw IllegalArgumentException(
            "A rational in the format 'a/b' or 'a' was expected. Found " +
                    "'${this@toRational}' instead."
        )
    return if (contains('/')) {
        val (num, den) = split('/')
        Rational(num.toBigIntegerOrFail(), den.toBigIntegerOrFail())
    } else {
        Rational(toBigIntegerOrFail(), ONE)
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3
    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}