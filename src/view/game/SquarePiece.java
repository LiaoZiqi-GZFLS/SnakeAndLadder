package view.game;

import java.awt.*;

public class SquarePiece extends PlayerPiece{

    public SquarePiece(int playerId, Color color, int row, int col) {
        super(playerId, color, row, col);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制外框
        g.setColor(pieceColor.darker());
        g.fillRect(0, 0, getWidth(), getHeight());
        // 绘制主体颜色
        g.setColor(pieceColor);
        g.fillRect(2, 2, getWidth()-4, getHeight()-4);
    }
}
