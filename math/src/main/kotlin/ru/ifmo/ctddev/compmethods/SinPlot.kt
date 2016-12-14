package ru.ifmo.ctddev.compmethods

import de.erichseifert.gral.data.DataTable
import de.erichseifert.gral.plots.XYPlot
import de.erichseifert.gral.plots.colors.SingleColor
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D
import de.erichseifert.gral.ui.InteractivePanel
import java.awt.Color
import javax.swing.JFrame

val PLOT_FEATURES_COLOR = Color(0.0f, 0.3f, 1.0f)

class SinPlot() : JFrame() {
    init {
        // Create data points
        val data = DataTable(Double::class.javaObjectType, Double::class.javaObjectType)
        var x  = -5.0
        while (x <= 5.0) {
            val y = 5.0 * Math.sin(x)
            data.add(x, y)
            x += 0.25
        }

        // Create plot
        val plot = XYPlot(data)
        plot.setLineRenderers(data, DefaultLineRenderer2D())
        plot.getPointRenderers(data)[0].color = SingleColor(PLOT_FEATURES_COLOR)
        plot.getLineRenderers(data)[0].color = PLOT_FEATURES_COLOR

        // Init JFrame
        setSize(800, 600)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.add(InteractivePanel(plot))
    }
}

fun main(args: Array<String>) {
    val frame = SinPlot()
    frame.isVisible = true
}