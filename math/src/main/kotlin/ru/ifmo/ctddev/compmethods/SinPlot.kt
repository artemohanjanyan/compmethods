package ru.ifmo.ctddev.compmethods

import de.erichseifert.gral.data.DataTable
import de.erichseifert.gral.plots.XYPlot
import de.erichseifert.gral.plots.colors.SingleColor
import de.erichseifert.gral.ui.InteractivePanel
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JButton
import javax.swing.JFrame

val MY_FUNC = object : (Complex) -> Complex {
    override fun invoke(x: Complex) = x * x * x - 1.0.toComplex()
}

val MY_FUNC_LAMBDA = object : (Complex) -> Complex {
    override fun invoke(x: Complex) = 1.0.toComplex() / x / x / 3.0
}

val EPS = 1E-06

fun createDataTable() = DataTable(Double::class.javaObjectType, Double::class.javaObjectType)

class SinPlot() : JFrame() {

    private val defaultWidth = 6.0

    private var plot: XYPlot
    private val data = arrayOf(createDataTable(), createDataTable(), createDataTable())
    private val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE)
    private val points = arrayOf(
            Complex(1.0, 0.0),
            Complex(-0.5, Math.sqrt(3.0) / 2),
            Complex(-0.5, -Math.sqrt(3.0) / 2))
    private val interactivePanel: InteractivePanel

    init {
        data[0].add(-defaultWidth / 2, -defaultWidth / 2)
        data[0].add(defaultWidth / 2, defaultWidth / 2)

        plot = XYPlot(data[0], data[1], data[2])
        plot.getAxis(XYPlot.AXIS_X).isAutoscaled = false
        plot.getAxis(XYPlot.AXIS_Y).isAutoscaled = false

        setSize(800, 600)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.layout = BorderLayout()
        interactivePanel = InteractivePanel(plot)
        contentPane.add(interactivePanel, BorderLayout.CENTER)
        val drawButton = JButton("Draw")
        contentPane.add(drawButton, BorderLayout.PAGE_START)
        drawButton.addActionListener {
            redraw()
            interactivePanel.repaint()
        }

        redraw()

        // TODO add click listener
//        val seq = IterativeComputationSequence(MY_START_POINT, MY_FUNC, MY_FUNC_LAMBDA, EPS)
//
//        for ((r, i) in seq) {
//            newtonData.add(r, i)
//        }
    }

    fun redraw() {
        data.forEach {
            plot.remove(it)
            it.clear()
        }

        val height = if (plot.bounds.height == 0.0) bounds.height.toDouble() else plot.bounds.height
        val width = if (plot.bounds.width == 0.0) bounds.width.toDouble() else plot.bounds.width
        println("$height $width")

        val xAxis = plot.getAxis(XYPlot.AXIS_X)
        val yAxis = plot.getAxis(XYPlot.AXIS_Y)

        val density = 7.0

        val xStepN = (width / density).toInt()
        val minX = xAxis.min.toDouble()
        val maxX = xAxis.max.toDouble()
        val stepX = (maxX - minX) / xStepN
        val yStepN = (height / density).toInt()
        val minY = yAxis.min.toDouble()
        val maxY = yAxis.max.toDouble()
        val stepY = (maxY - minY) / yStepN

        var x = minX
        while (x <= maxX) {
            var y = minY
            while (y <= maxY) {
                val seq = IterativeComputationSequence(Complex(x, y), MY_FUNC, MY_FUNC_LAMBDA, EPS)
                val root = seq.computeRoot()
                data.zip(points).find {
                    it.second.equals(root, EPS)
                }?.first?.add(x, y)
                y += stepY
            }
            x += stepX
        }

        data.zip(colors).forEach {
            plot.add(it.first)
//            plot.setLineRenderers(it.first, DefaultLineRenderer2D())
            plot.getPointRenderers(it.first)[0].color = SingleColor(it.second)
//            plot.getLineRenderers(it.first)[0].color = it.second
        }
    }
}

fun main(args: Array<String>) {
    val frame = SinPlot()
    frame.isVisible = true
}