package ru.ifmo.ctddev.compmethods

import de.erichseifert.gral.plots.XYPlot
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D
import de.erichseifert.gral.ui.InteractivePanel
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Rectangle
import javax.swing.JFrame

class ChaosDetail(val r: Double) : JFrame() {

    val MAX_ITERATIONS = 10000
    val SHOWN_ITERATIONS = 100

    private val xStep = 0.05

    private val plot: XYPlot
    private val interactivePanel: InteractivePanel

    init {
        plot = XYPlot()
        fillData()
        plot.getAxis(XYPlot.AXIS_X).isAutoscaled = false
        plot.getAxis(XYPlot.AXIS_Y).isAutoscaled = false

        setSize(800, 600)
        defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        contentPane.layout = BorderLayout()
        interactivePanel = InteractivePanel(plot)
        contentPane.add(interactivePanel, BorderLayout.CENTER)
    }

    fun fillData() {
        val dataTables = (1 until (1 / xStep).toInt()).map {
            Pair(xStep * it, createDataTable())
        }

        dataTables.forEach {
            var x = it.first
            var iterations = 0
            while (iterations < MAX_ITERATIONS) {
                if (iterations % SHOWN_ITERATIONS == 0) {
                    it.second.add(iterations.toDouble(), x)
                }
                x = next(x, r)
                ++iterations
            }
            it.second.add(iterations.toDouble(), x)

            plot.add(it.second)
        }

        dataTables.forEach {
            val color = Color.getHSBColor(it.first.toFloat(), 1.0f, 1.0f)

            plot.getPointRenderers(it.second)[0].setColor(color)
            plot.getPointRenderers(it.second)[0].shape = Rectangle(0, 0, 1, 1)

            val lineRenderer = DefaultLineRenderer2D()
            lineRenderer.color = color
            plot.setLineRenderers(it.second, lineRenderer)
        }
    }

    private fun next(x: Double, r: Double): Double {
        return r * x * (1 - x)
    }
}
