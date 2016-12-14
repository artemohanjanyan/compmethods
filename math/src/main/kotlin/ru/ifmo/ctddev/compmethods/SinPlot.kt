package ru.ifmo.ctddev.compmethods

import de.erichseifert.gral.data.DataTable
import de.erichseifert.gral.plots.XYPlot
import de.erichseifert.gral.plots.colors.SingleColor
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D
import de.erichseifert.gral.ui.InteractivePanel
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JButton
import javax.swing.JFrame

val PLOT_FEATURES_COLOR = Color(0.0f, 0.3f, 1.0f)

val NEWTON_FEATURES_COLOR = Color(1.0f, 0.7f, 0.0f)

val MY_FUNC = object : (Complex) -> Complex {
    override fun invoke(x: Complex) = x * x * x - 1.0.toComplex()
}

val MY_FUNC_LAMBDA = object : (Complex) -> Complex {
    override fun invoke(x: Complex) = 1.0.toComplex() / x / x / 3.0
}

val EPS = 1E-06

class SinPlot() : JFrame() {

    private val defaultWidth = 6.0

    private var plot: XYPlot
    private var data: DataTable
    private val interactivePanel: InteractivePanel

    init {
        data = DataTable(Double::class.javaObjectType, Double::class.javaObjectType)
        data.add(-defaultWidth / 2, -defaultWidth / 2)
        data.add(defaultWidth / 2, defaultWidth / 2)

        plot = XYPlot(data)
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
    }

    fun redraw() {
        plot.remove(data)
        data.clear()

        val xAxis = plot.getAxis(XYPlot.AXIS_X)

        val xStepN = 200
        val minX = xAxis.min.toDouble()
        val maxX = xAxis.max.toDouble()
        val stepX = (maxX - minX) / xStepN
        val yStepN = 200
        val minY = YAxis.min.toDouble()
        val maxY = yAxis.max.toDouble()
        val stepY = (maxY - minY) / YStepN

        var x = minX
        while (x <= maxX) {
            var y = minY
            while (y <= )
            x += stepX
        }
        val seq = IterativeComputationSequence(MY_START_POINT, MY_FUNC, MY_FUNC_LAMBDA, EPS)

        for ((r, i) in seq) {
            newtonData.add(r, i)
        }

        plot.add(data)
        plot.setLineRenderers(data, DefaultLineRenderer2D())
        plot.getPointRenderers(data)[0].color = SingleColor(PLOT_FEATURES_COLOR)
        plot.getLineRenderers(data)[0].color = PLOT_FEATURES_COLOR
    }
}

fun main(args: Array<String>) {
    val frame = SinPlot()
    frame.isVisible = true
}