package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


class PolynomTest {

    @Test
    fun equalize() {
        assertEquals(
            listOf(Polynom(0.0, 3.0, 4.0, -8.0), Polynom(5.0, 2.0, 1.0, 7.0)),
            Polynom(3.0, 4.0, -8.0).equalize(Polynom(5.0, 2.0, 1.0, 7.0))
        )
        assertEquals(
            listOf(Polynom(4.0, 3.0, 4.0, -8.0), Polynom(5.0, 2.0, 1.0, 7.0)),
            Polynom(4.0, 3.0, 4.0, -8.0).equalize(Polynom(5.0, 2.0, 1.0, 7.0))
        )
        assertEquals(
            listOf(Polynom(4.0, 3.0, 4.0, -8.0), Polynom(0.0, 2.0, 1.0, 7.0)),
            Polynom(4.0, 3.0, 4.0, -8.0).equalize(Polynom(2.0, 1.0, 7.0))
        )
        assertEquals(
            listOf(Polynom(0.0, 1.0, 3.0, 2.0), Polynom(1.0, -2.0, -1.0, 4.0)),
            Polynom(1.0, 3.0, 2.0).equalize(Polynom(1.0, -2.0, -1.0, 4.0))
        )
        assertEquals(
            listOf(Polynom(1.0, -2.0, -1.0, 4.0), Polynom(0.0, 1.0, 3.0, 2.0)),
            Polynom(1.0, -2.0, -1.0, 4.0).equalize(Polynom(1.0, 3.0, 2.0))
        )
    }

    @Test
    fun coeff() {
        assertEquals(3.0, Polynom(1.0, 3.0, 2.0).coeff(1))
        assertEquals(2.0, Polynom(1.0, -3.0, -4.0, 2.0).coeff(0))
        assertEquals(-1.0, Polynom(1.0, -1.0, 2.0, 6.0).coeff(2))
        assertEquals(1.0, Polynom(1.0, 1.0, -5.0, -3.0, 10.0, 8.0).coeff(5))
    }

    @Test
    fun getValue() {
        assertEquals(42.0, Polynom(1.0, 3.0, 2.0).getValue(5.0), 1e-10)
        assertEquals(10.0, Polynom(1.0, 2.0, 2.0).getValue(2.0), 1e-10)
        assertEquals(34.0, Polynom(1.0, -1.0, 6.0, -2.0).getValue(3.0), 1e-10)
    }

    @Test
    fun degree() {
        assertEquals(2, Polynom(0.0, 2.0, 1.0, 7.0).degree())
        assertEquals(3, Polynom(4.0, 2.0, 1.0, 7.0).degree())
        assertEquals(0, Polynom(0.0, 0.0, 0.0, 7.0).degree())
        assertEquals(0, Polynom(0.0, 0.0, 0.0, 0.0).degree())
    }


    @Test
    fun plus() {
        assertEquals(Polynom(1.0, -1.0, 2.0, 6.0), Polynom(1.0, -2.0, -1.0, 4.0).plus(Polynom(1.0, 3.0, 2.0)))
        assertEquals(Polynom(1.0, -1.0, 2.0, 6.0), Polynom(1.0, 3.0, 2.0).plus(Polynom(1.0, -2.0, -1.0, 4.0)))
        assertEquals(Polynom(0.0), Polynom(0.0, 3.0, 4.0, -8.0).plus(Polynom(0.0, -3.0, -4.0, 8.0)))
        assertEquals(Polynom(-1.0, 2.0, 1.0, -4.0), Polynom(-1.0, 2.0, 1.0, -4.0).plus(Polynom(0.0)))
    }

    @Test
    fun unaryMinus() {
        assertEquals(Polynom(-1.0, 2.0, 1.0, -4.0), Polynom(1.0, -2.0, -1.0, 4.0).unaryMinus())
        assertEquals(Polynom(-5.0, -2.0, -1.0, -7.0), Polynom(5.0, 2.0, 1.0, 7.0).unaryMinus())
        assertEquals(Polynom(-0.0, 2.0, 1.0, 7.0), Polynom(0.0, -2.0, -1.0, -7.0).unaryMinus())
    }


    @Test
    fun minus() {
        assertEquals(Polynom(1.0, -3.0, -4.0, 2.0), Polynom(1.0, -2.0, -1.0, 4.0).minus(Polynom(1.0, 3.0, 2.0)))
        assertEquals(
            Polynom(0.0),
            Polynom(1.0, 1.0, -5.0, -3.0, 10.0, 8.0).minus(Polynom(1.0, 1.0, -5.0, -3.0, 10.0, 8.0))
        )
        assertEquals(Polynom(2.0, 0.0, 2.0, 0.0), Polynom(1.0, 2.0, 5.0, 0.0).minus(Polynom(-1.0, 2.0, 3.0, 0.0)))
    }

    @Test
    fun times() {
        assertEquals(
            Polynom(1.0, 1.0, -5.0, -3.0, 10.0, 8.0),
            Polynom(1.0, -2.0, -1.0, 4.0).times(Polynom(1.0, 3.0, 2.0))
        )
        assertEquals(
            Polynom(1.0, 1.0, -5.0, -3.0, 10.0, 8.0),
            Polynom(1.0, 3.0, 2.0).times(Polynom(1.0, -2.0, -1.0, 4.0))
        )
        assertEquals(Polynom(0.0), Polynom(1.0, 2.0, 5.0).times(Polynom(0.0)))
    }

    @Test
    fun div() {
        assertEquals(Polynom(1.0, -5.0), Polynom(1.0, -2.0, -1.0, 4.0).div(Polynom(1.0, 3.0, 2.0)))
        assertEquals(Polynom(1.0, 2.0, 5.0), Polynom(1.0, 2.0, 5.0, 0.0).div(Polynom(1.0, 0.0)))
        assertEquals(Polynom(1.0), Polynom(1.0, -1.0, 2.0, 6.0).div(Polynom(1.0, -1.0, 2.0, 6.0)))
    }

    @Test
    fun rem() {
        assertEquals(Polynom(12.0, 14.0), Polynom(1.0, -2.0, -1.0, 4.0).rem(Polynom(1.0, 3.0, 2.0)))
        assertEquals(Polynom(0.0), Polynom(3.0, 21.0, 9.0, 6.0).rem(Polynom(1.0, 7.0, 3.0, 2.0)))
        assertEquals(
            Polynom(-3.0, 10.0, 8.0),
            Polynom(1.0, 1.0, -5.0, -3.0, 10.0, 8.0).rem(Polynom(1.0, 1.0, -5.0, 0.0, 0.0, 0.0))
        )

    }

    @Test
    fun equals() {
        assertTrue(Polynom(1.0, 2.0, 3.0).equals(Polynom(1.0, 2.0, 3.0)))
        assertTrue(Polynom(0.0, 2.0, 3.0).equals(Polynom(2.0, 3.0)))
        assertFalse(Polynom(2.0, 0.0, 3.0).equals(Polynom(2.0, 3.0)))
        assertFalse(Polynom(-2.0, 3.0).equals(Polynom(2.0, 3.0)))
    }


    @Test
    fun output() {
        assertEquals("2x^2+3x-8", Polynom(2.0, 3.0, -8.0).output())
        assertEquals("2x^2-8", Polynom(2.0, 0.0, -8.0).output())
        assertEquals("Empty Polynom", Polynom(0.0, 0.0, 0.0, 0.0).output())
    }

}