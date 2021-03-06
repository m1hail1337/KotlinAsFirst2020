@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.pow

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg val coeffs: Double) {

    private val coefList = coeffs.toMutableList()

    fun equalize(other: Polynom): List<Polynom> { // Функция для уравнивания длины двух полиномов с помощью добавления в начало 0.0
        when {
            other.coeffs.size == this.coeffs.size -> return listOf(this, other)
            this.coeffs.size > other.coeffs.size -> for (i in 0 until this.coeffs.size - other.coeffs.size) other.coefList.add(
                0,
                0.0
            )
            else -> for (i in 0 until other.coeffs.size - this.coeffs.size) this.coefList.add(0, 0.0)
        }
        return listOf(Polynom(*this.coefList.toDoubleArray()), Polynom(*other.coefList.toDoubleArray()))
    }


    private fun divisionHelper(
        other: Polynom,
        flagDiv: Boolean
    ): Polynom { // Функция для реализации деления и взятия остатка
        if (other == Polynom(0.0)) throw NumberFormatException("Division by zero")
        var diffSize = this.coeffs.size - other.coeffs.size
        val result = mutableListOf<Double>()
        var remains = this
        for (i in 0..diffSize) {
            val divisor = remains.coeffs[i] / other.coeffs[0]
            val helpList = other.coefList.map { it * divisor }.toMutableList()
            for (i in 0 until diffSize) helpList += 0.0
            remains -= Polynom(*helpList.toDoubleArray())
            result += divisor
            diffSize--
        }
        return if (flagDiv) Polynom(*result.toDoubleArray())
        else remains
    }

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = this.coefList.asReversed()[i]


    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        var result = 0.0
        var maxDegree = coeffs.size - 1
        for (coef in coeffs) {
            result += coef * x.pow(maxDegree)
            maxDegree--
        }
        return result
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int {
        var degree = this.coeffs.size - 1
        for (i in coeffs) if (i == 0.0) degree-- else return degree
        return 0
    }

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        this.equalize(other)
        return Polynom(*this.coefList.mapIndexed
        { index, element -> element + other.coefList[index] }
            .toDoubleArray())
    }

    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom = Polynom(*this.coefList.map
    { element -> element * -1 }
        .toDoubleArray())

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom {
        this.equalize(other)
        return Polynom(*this.coefList.mapIndexed
        { index, element -> element - other.coefList[index] }
            .toDoubleArray())
    }

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        this.equalize(other)
        val parts = mutableListOf<Polynom>()
        var maxDegree = this.coeffs.size - 1
        for (i in this.coeffs) {
            val newCoeffs = other.coefList.map { it * i }.toMutableList()
            for (i in 0 until maxDegree) newCoeffs += 0.0
            parts += Polynom(*newCoeffs.toDoubleArray())
            maxDegree--
        }
        var result = Polynom()
        parts.forEach {
            result += it
        }
        return result
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom = divisionHelper(other, true)

    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = divisionHelper(other, false)

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other is Polynom -> {
                val polynoms = this.equalize(other)
                polynoms[0].coefList.toDoubleArray() contentEquals polynoms[1].coefList.toDoubleArray()
            }
            else -> false
        }

    /**
     * Получение хеш-кода
     */
    override fun hashCode(): Int = 0


    /**
     * Представление полинома в виде строки типа 5.0х^2+2.0x-7.0
     */

    fun output(): String {     // Написал просто так, считаю полезным наличие данной функции
        val p = this
        if (p == Polynom(0.0)) return "Empty Polynom"
        var degree = this.coeffs.size - 1
        return buildString {
            for (i in p.coeffs.indices) {
                if (coeffs[i] != 0.0) {
                    if (coeffs[i] > 0.0 && i > 0) append("+${coeffs[i].toInt()}")
                    else append("${coeffs[i].toInt()}")
                    when {
                        degree > 1 -> append("x^$degree")
                        degree == 1 -> append("x")
                    }
                }
                degree--
            }
        }
    }
}