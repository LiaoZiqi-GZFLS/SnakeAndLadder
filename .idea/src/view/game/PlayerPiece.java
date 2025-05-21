package view.game;

import javax.swing.*;
import java.awt.*;

public class PlayerPiece extends JComponent {
    private final Color pieceColor;
    private int row;
    private int col;

    public PlayerPiece(int playerId, Color color, int row, int col) {
        this.pieceColor = color;
        this.row = row;
        this.col = col;
        setSize(20, 20); // 棋子大小
        // 根据玩家ID设置偏移量，防止两个棋子在同一个位置时重合
        int xOffset = (playerId == 1) ? 5 : 25;
        int yOffset = (playerId == 1) ? 5 : 25;
        setLocation(xOffset, yOffset);
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() { return row; }
    public int getCol() { return col; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制外框
        g.setColor(pieceColor.darker());
        g.fillOval(0, 0, getWidth(), getHeight());
        // 绘制主体颜色
        g.setColor(pieceColor);
        g.fillOval(2, 2, getWidth()-4, getHeight()-4);
    }
}