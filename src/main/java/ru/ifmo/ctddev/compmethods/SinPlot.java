package ru.ifmo.ctddev.compmethods;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

import javax.swing.*;
import java.awt.*;

public class SinPlot extends JFrame {
    public SinPlot() throws HeadlessException {
        // Init JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

        // Add data points
        //noinspection unchecked
        DataTable data = new DataTable(Double.class, Double.class);
        for (double x = -5.0; x <= 5.0; x+=0.25) {
            double y = 5.0*Math.sin(x);
            data.add(x, y);
        }

        // Add points to plot
        XYPlot plot = new XYPlot(data);
        getContentPane().add(new InteractivePanel(plot));

        // Make it look better
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderers(data, lines);
        Color color = new Color(0.0f, 0.3f, 1.0f);
        plot.getPointRenderers(data).get(0).setColor(color);
        plot.getLineRenderers(data).get(0).setColor(color);
    }

    public static void main(String[] args) {
        SinPlot frame = new SinPlot();
        frame.setVisible(true);

        // Call kotlin from Java
        System.out.println(new SomeClass().someFun());
    }
}
