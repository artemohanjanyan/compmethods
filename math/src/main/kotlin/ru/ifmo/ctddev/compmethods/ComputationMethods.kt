package ru.ifmo.ctddev.compmethods


class IterativeComputationSequence(start: Complex,
                                   val function: (Complex) -> Complex,
                                   val lambda: (Complex) -> Complex,
                                   val eps: Double)
    : Iterator<Complex> {

    private var cur = start
        get

    private var next = computeNext()

    override fun next(): Complex {
        cur = next
        next = computeNext()
        return cur
    }

    override fun hasNext() = (next - cur).abs > eps

    private fun computeNext() = cur - function(cur) * lambda(cur)
}

