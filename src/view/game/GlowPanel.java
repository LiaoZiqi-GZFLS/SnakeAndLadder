package view.game;

import javax.swing.*;
import java.awt.*;

public class GlowPanel extends JPanel {
    private int diameter = 100; // 光晕直径

    public GlowPanel(int diameter) {
        this.diameter = diameter;
        setOpaque(false); // 设置为非不透明，以便背景可见
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 创建白色渐变的圆形光晕
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(0, 0, 255, 128),
                diameter / 2, diameter / 2, new Color(255, 255, 255, 0)
        );
        g2d.setPaint(gradient);

        // 绘制圆形
        g2d.fillOval(0, 0, diameter, diameter);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(diameter, diameter);
    }
}
