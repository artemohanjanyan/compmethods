package ru.ifmo.ctddev.compmethods

import de.erichseifert.gral.plots.XYPlot
import de.erichseifert.gral.ui.InteractivePanel
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Rectangle
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton

import javax.swing.JFrame

class Chaos() : JFrame() {

    val MAX_ITERATIONS = 10000

    private val defaultWidth = 6.0

    private val xStep = 0.01

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
        plot.getPointRenderers(data)[0].shape = Rectangle(0, 0, 1, 1)

        setSize(800, 600)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.layout = BorderLayout()
        interactivePanel = InteractivePanel(plot)
        contentPane.add(interactivePanel, BorderLayout.CENTER)

        val drawButton = JButton("Draw")
        contentPane.add(drawButton, BorderLayout.PAGE_START)
        drawButton.addActionListener {
            fillData()
            interactivePanel.repaint()
        }

        interactivePanel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                val x = (e?.x ?: return)

                val xAxis = plot.getAxis(XYPlot.AXIS_X)
                val xAxisRenderer = plot.getAxisRenderer(XYPlot.AXIS_X)
                val plotX = xAxisRenderer.viewToWorld(xAxis, x.toDouble(), true).toDouble()

                val frame = ChaosDetail(plotX)
                frame.isVisible = true
            }
        })

        fillData()
    }

    fun fillData() {
        data.clear()

        val width = if (plot.bounds.width == 0.0) bounds.width.toDouble() else plot.bounds.width
        val density = 1.0
        val stepN = (width / density).toInt()
        val xAxis = plot.getAxis(XYPlot.AXIS_X)
        val minR = xAxis.min.toDouble()
        val rStep = (xAxis.max.toDouble() - minR) / stepN
        for (ri in 0..stepN) {
            ri.trace("it ")
            val r = ri * rStep + minR
            var startX = xStep
            while (startX < 1) {
                data.add(r, compute(startX, r))
                startX += xStep
            }
        }
    }

    private fun compute(startX: Double, r: Double): Double {
        var x = startX
        var iterations = 0
        while (iterations < MAX_ITERATIONS) {
            x = next(x, r)
            ++iterations
        }
        return x
    }

    private fun next(x: Double, r: Double): Double {
        return r * x * (1 - x)
    }
}

fun <R> R.trace(debug : String) : R {
    println("$debug : $this")
    return this
}

fun main(args: Array<String>) {
    val frame = Chaos()
    frame.isVisible = true
    frame.fillData()
}