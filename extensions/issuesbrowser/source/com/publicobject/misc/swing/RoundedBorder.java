/* Glazed Lists                                                 (c) 2003-2005 */
/* http://publicobject.com/glazedlists/                      publicobject.com,*/
/*                                                     O'Dell Engineering Ltd.*/
package com.publicobject.misc.swing;

import javax.swing.border.Border;
import javax.swing.*;
import java.awt.*;

/**
 * A border with cute rounded edges.
 * 
 * @author <a href="mailto:jesse@swank.ca">Jesse Wilson</a>
 */
public class RoundedBorder implements Border {

    private Color background;
    private Color outline;
    private Color foreground;
    private int width;
    private int stroke;

    public RoundedBorder(Color background, Color outline, Color foreground, int width, int stroke) {
        this.background = background;
        this.outline = outline;
        this.foreground = foreground;
        this.width = width;
        this.stroke = stroke;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.addRenderingHints(Icons.RENDERING_HINTS);
        int diameter = this.width * 2;

        // fill background
        g2d.setColor(background);
        g2d.fillRect(0, 0, this.width, this.width);
        g2d.fillRect(width - this.width, 0, this.width, this.width);
        g2d.fillRect(0, height - this.width, this.width, this.width);
        g2d.fillRect(width - this.width, height - this.width, this.width, this.width);

        // fill foreground
        g2d.setColor(foreground);
        g2d.fillArc(0, 0, diameter, diameter, 90, 90);
        g2d.fillArc(width - diameter - stroke, 0, diameter, diameter, 0, 90);
        g2d.fillArc(0, height - diameter - stroke, diameter, diameter, 180, 90);
        g2d.fillArc(width - diameter - stroke, height - diameter - stroke, diameter, diameter, 270, 90);

        // prepare the arc lines
        if(stroke > 0) {
            g2d.setColor(outline);
            g2d.setStroke(new BasicStroke(stroke));

            // top
            g2d.drawArc(0, 0, diameter, diameter, 90, 90);
            g2d.drawArc(width - diameter - stroke, 0, diameter, diameter, 0, 90);
            g2d.drawLine(this.width, 0, width - this.width, 0);

            // sides
            g2d.drawLine(0, this.width, 0, height - this.width);
            g2d.drawLine(width - stroke, this.width, width - stroke, height - this.width);

            // bottom
            g2d.drawArc(0, height - diameter - stroke, diameter, diameter, 180, 90);
            g2d.drawArc(width - diameter - stroke, height - diameter - stroke, diameter, diameter, 270, 90);
            g2d.drawLine(this.width, height - stroke, width - this.width, height - stroke);
        }
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(width, width, width, width);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new TestRunnable());
    }

    private static class TestRunnable implements Runnable { 
        public void run() {
            JPanel panel = new JPanel(new FlowLayout());
            panel.setBackground(Color.BLUE);

            for(int p = 2; p < 20; p++) {
                for(int s = 0; s <= 3; s++) {
                    JPanel cell = new JPanel();
                    cell.add(new JLabel("<html>WIDTH: " + p + "<br>STROKE: " + s));
                    cell.setBorder(new RoundedBorder(Color.BLUE, Color.BLACK, Color.WHITE, p, s));
                    cell.setBackground(Color.WHITE);
                    panel.add(cell);
                }
            }

            JFrame frame = new JFrame();
            frame.getContentPane().add(panel);
            frame.setSize(640, 480);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
    }
}