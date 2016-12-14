package ru.ifmo.ctddev.compmethods

import de.erichseifert.gral.data.DataTable
import de.erichseifert.gral.plots.XYPlot
import de.erichseifert.gral.plots.colors.SingleColor
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D
import de.erichseifert.gral.ui.InteractivePanel
import java.awt.Color
import java.util.*
import javax.swing.JFrame

val PLOT_FEATURES_COLOR = Color(0.0f, 0.3f, 1.0f)
val NEWTON_FEATURES_COLOR = Color(1.0f, 0.7f, 0.0f)


val MY_FUNC = object : (Complex) -> Complex {
    override fun invoke(x: Complex) = x * x * x - 1.0.toComplex()
}

val MY_FUNC_LAMBDA = object : (Complex) -> Complex {
    override fun invoke(x: Complex) = 1.0.toComplex() / x / x / 3.0
}

val RND = Random()

fun Random.nextDouble(max : Double) = (nextDouble() - 0.5) * max * 2

val MY_START_POINT = Complex(RND.nextDouble(10.0), RND.nextDouble(10.0))

val EPS = 1E-06

fun createData() = DataTable(Double::class.javaObjectType, Double::class.javaObjectType)

class SinPlot() : JFrame() {
    val data = createData()
    val newtonData = createData()
    val plot = XYPlot(data, newtonData)
    val panel = InteractivePanel(plot)

    init {
        // Create data points
        var x = -5.0
        while (x <= 5.0) {
            val y = 5.0 * Math.sin(x)
            data.add(x, y)
            x += 0.25
        }

        val seq = IterativeComputationSequence(MY_START_POINT, MY_FUNC, MY_FUNC_LAMBDA, EPS)

        for ((r, i) in seq) {
            newtonData.add(r, i)
        }

        // Create plot
        plot.getPointRenderers(data)[0].color = SingleColor(PLOT_FEATURES_COLOR)
        plot.getPointRenderers(newtonData)[0].color = SingleColor(NEWTON_FEATURES_COLOR)

        val dataLineRenderer = DefaultLineRenderer2D()
        dataLineRenderer.color = PLOT_FEATURES_COLOR
        plot.setLineRenderers(data, dataLineRenderer)

        val newtonLineRenderer = DefaultLineRenderer2D()
        newtonLineRenderer.color = NEWTON_FEATURES_COLOR
        plot.setLineRenderers(newtonData, newtonLineRenderer)

        // Init JFrame
        setSize(800, 600)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.add(panel)
    }
}

fun main(args: Array<String>) {
    val frame = SinPlot()
    frame.isVisible = true
}