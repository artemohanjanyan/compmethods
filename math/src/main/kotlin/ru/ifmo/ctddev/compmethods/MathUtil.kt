package ru.ifmo.ctddev.compmethods

data class Complex(val real: Double, val imaginary: Double) {

    operator fun plus(c: Complex) =
            Complex(real + c.real, imaginary + c.imaginary)

    operator fun minus(c: Complex) =
            Complex(real - c.real, imaginary - c.imaginary)

    operator fun times(d : Double) =
            Complex(real * d, imaginary * d)

    operator fun times(c: Complex) =
            Complex(real * c.real - imaginary * c.imaginary, real * c.imaginary + imaginary * c.real)

    operator fun div(d : Double) =
            Complex(real / d, imaginary / d)

    operator fun div(c : Complex) =
            this * c.conjugate / (c.real.square() + c.imaginary.square())

    val conjugate : Complex
        get() = Complex(real, -imaginary)

    val abs : Double
        get() = Math.sqrt(real.square() + imaginary.square())
}

fun Double.square() = this * this

fun Double.toComplex() = Complex(this, 0.0)

fun Double.toComplexImaginary() = Complex(0.0, this)