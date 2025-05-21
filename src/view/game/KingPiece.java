package view.game;

import java.awt.*;

public class KingPiece extends PlayerPiece {

    public KingPiece(int playerId, Color color, int row, int col) {
        super(playerId, color, row, col);
        setSize(40, 40); // 设置棋子大小
        // 根据玩家ID设置偏移量，防止两个棋子在同一个位置时重合
        int xOffset = (playerId == 1) ? 0 : 15;
        int yOffset = (playerId == 1) ? 7 : 10;
        setLocation(xOffset, yOffset);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制王的底座
        g.setColor(pieceColor);
        g.fillRoundRect(5, 25, 30, 15, 5, 5);

        // 绘制王的顶部
        int[] xPoints = {5, 20, 35};
        int[] yPoints = {25, 5, 25};
        g.fillPolygon(xPoints, yPoints, 3);

        // 绘制王的十字架
        g.fillRect(15, 10, 10, 15);
        g.fillRect(10, 15, 20, 10);

        // 绘制王的装饰线条
        g.setColor(pieceColor.darker());
        g.drawLine(5, 25, 20, 5);
        g.drawLine(20, 5, 35, 25);

        // 绘制王的顶部圆点
        g.fillOval(18, 5, 8, 8);
    }
}