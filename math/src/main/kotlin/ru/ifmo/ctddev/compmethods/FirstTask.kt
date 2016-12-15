package ru.ifmo.ctddev.compmethods

import de.erichseifert.gral.data.DataTable
import de.erichseifert.gral.plots.XYPlot
import de.erichseifert.gral.plots.colors.SingleColor
import de.erichseifert.gral.ui.InteractivePanel
import java.awt.BorderLayout
import java.awt.Color

import javax.swing.JFrame

class Plot() : JFrame() {

    val EPS = 1E-06
    val MAX_ITERATIONS = 1000000

    private val defaultWidth = 20.0
    private val stepCount = 1000
    private val START = -10.0

    private val data = createDataTable()
    private val plot : XYPlot
    private val interactivePanel : InteractivePanel


    init {
        data.add(-defaultWidth / 2, -defaultWidth / 2)
        data.add(defaultWidth / 2, defaultWidth / 2)

        plot = XYPlot(data)
        plot.getAxis(XYPlot.AXIS_X).isAutoscaled = false
        plot.getAxis(XYPlot.AXIS_Y).isAutoscaled = false
        plot.getPointRenderers(data)[0].setColor(Color.RED)

        setSize(800, 600)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.layout = BorderLayout()
        interactivePanel = InteractivePanel(plot)
        contentPane.add(interactivePanel, BorderLayout.CENTER)

        fillData()
    }

    fun fillData() {
        data.clear()
        val xAxis = plot.getAxis(XYPlot.AXIS_X)
        val minX =  xAxis.min.toDouble()
        val xStep = (xAxis.max.toDouble() - minX) / stepCount
        for (x in 0..stepCount) {
            var r = x * xStep + minX
            data.add(r, compute(r))
        }
    }

    private fun compute (r: Double): Double {
        val lambda = 1 / (r - 2 * r * START - START)
        var prev = START
        var x = next(prev, lambda, r)
        var iterations = 0
        while (Math.abs(prev - x) > EPS && iterations < MAX_ITERATIONS) {
            prev = x
            x = next(prev, lambda, r)
            iterations++
        }
        return x
    }

    private fun next(x : Double, lambda: Double, r: Double): Double {
        return x  -  lambda * (r * x * (1 - x) - x)
    }
}


fun <R> R.trace(debug : String) : R {
    println("$debug : $this")
    return this
}

fun main(args: Array<String>) {
    val frame = Plot()
    frame.isVisible = true
    frame.fillData()
}