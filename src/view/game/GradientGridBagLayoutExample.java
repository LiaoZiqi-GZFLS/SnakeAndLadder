package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GradientGridBagLayoutExample {
    public static void main(String[] args) {
        // 创建主窗口
        JFrame frame = new JFrame("Gradient GridBagLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 创建使用 GridBagLayout 的面板
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false); // 设置面板为透明

        // 创建一个 JLabel 用于显示 GIF
        JLabel gifLabel = new JLabel();
        ImageIcon gifIcon = new ImageIcon("path/to/your/white.gif"); // 替换为你的GIF路径
        gifLabel.setIcon(gifIcon);
        gifLabel.setSize(140, 140); // 设置GIF图的大小为140x140像素

        // 创建一个面板用于渐变效果
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 创建从白色到透明的径向渐变
                RadialGradientPaint gradient = new RadialGradientPaint(
                        new Point(70, 70), // 渐变中心点（相对于GIF图的位置）
                        70, // 渐变半径
                        new float[]{0f, 1f},
                        new Color[]{new Color(255, 255, 255, 255), new Color(255, 255, 255, 0)}
                );
                g2d.setPaint(gradient);
                g2d.fill(new Rectangle(0, 0, 140, 140));
            }
        };
        gradientPanel.setOpaque(false);
        gradientPanel.setSize(140, 140);

        // 将GIF标签和渐变面板添加到面板中心
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1; // 放置在底部行
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.weighty = 1.0; // 允许底部组件占用多余空间

        // 创建一个叠加面板，用于将渐变效果和GIF图组合在一起
        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new OverlayLayout(overlayPanel));
        overlayPanel.setOpaque(false);
        overlayPanel.add(gradientPanel);
        overlayPanel.add(gifLabel);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(overlayPanel, gbc);

        // 将面板添加到 JFrame
        frame.add(panel);
        frame.setVisible(true);
    }
}