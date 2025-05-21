package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class TransparentTriangle extends JPanel {
    private int width;
    private int height;
    private boolean direction = true;

    public TransparentTriangle(int width, int height) {
        this.width = width;
        this.height = height;
        setOpaque(false); // 设置面板为透明
    }
    public TransparentTriangle(int width, int height, boolean direction) {
        this.width = width;
        this.height = height;
        this.direction = direction;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 定义三角形的三个顶点
        Point2D p1 = new Point2D.Float(0, height - 1); // 左下顶点
        Point2D p2 = new Point2D.Float(width - 1, height - 1); // 右下顶点
        Point2D p3 = new Point2D.Float(width - 1, 0); // 右上顶点

        // 创建渐变
        GradientPaint gradient;
        if (direction) {
            gradient = new GradientPaint(
                    (float) p2.getX(), (float) p2.getY(), new Color(255, 255, 255, 255), // 右下顶点不透明
                    (float) p3.getX(), (float) p3.getY(), new Color(255, 255, 255, 0) // 右上顶点透明
            );
        }else {
            gradient = new GradientPaint(
                    (float) p2.getX(), (float) p2.getY(), new Color(255, 255, 255, 255), // 右下顶点不透明
                    (float) p1.getX(), (float) p1.getY(), new Color(255, 255, 255, 0) // 左下顶点透明
            );
        }


        // 绘制三角形
        g2d.setPaint(gradient);
        g2d.fillPolygon(new int[]{(int)p1.getX(), (int)p2.getX(), (int)p3.getX()},
                new int[]{(int)p1.getY(), (int)p2.getY(), (int)p3.getY()},
                3);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public static void main(String[] args) {
        // 创建主窗口
        JFrame frame = new JFrame("Transparent Triangle Grid Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // 创建使用 GridBagLayout 的面板
        JPanel gridBagPanel = new JPanel();
        gridBagPanel.setLayout(new GridBagLayout());
        gridBagPanel.setOpaque(false); // 设置面板为透明

        // 创建并添加多个 TransparentTriangle 面板到 GridBagLayout 面板
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                gbc.gridx = col;
                gbc.gridy = row;

                // 创建自定义透明三角形面板，指定宽度和高度
                TransparentTriangle trianglePanel = new TransparentTriangle(140, 140);

                gridBagPanel.add(trianglePanel, gbc);
            }
        }

        // 将 GridBagLayout 面板添加到 JFrame
        frame.add(gridBagPanel);

        // 显示窗口
        frame.setVisible(true);
    }
}