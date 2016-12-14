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
        val step = (maxX - minX) / xStepN

        var x = minX
        while (x <= maxX) {
            val y = Math.sin(x)
            data.add(x, y)
            x += step
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