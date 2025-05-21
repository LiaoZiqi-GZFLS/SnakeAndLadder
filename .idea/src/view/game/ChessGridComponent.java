package view.game;

import javax.swing.*;
import java.awt.*;

public class ChessGridComponent extends JComponent {
    private final int value; // 0=普通，1=特殊

    public ChessGridComponent(int value, int gridSize) {
        this.value = value;
        setSize(gridSize, gridSize);
        setLayout(null);
    }

    public void addPlayerPiece(PlayerPiece piece) {
        add(piece);
    }

    public void removePlayerPiece(PlayerPiece piece) {
        remove(piece);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 普通格子默认
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 特殊格子标记
        if (value == 1) {
            g.setColor(new Color(255, 200, 200));
            g.fillRect(2, 2, getWidth()-4, getHeight()-4);
            g.setColor(Color.RED);
            g.drawString("+2", getWidth()-25, getHeight()-8);
        }

        //todo: paint other effect here

        // 边框和编号
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString(String.valueOf(getPositionNumber()), 2, 12);
    }
    private int getPositionNumber() {
        // 根据父容器在棋盘中的位置计算行列
        int row = getY() / 50; // 假设每个格子50像素
        int col = getX() / 50;
        return row * 10 + col + 1;
    }
}
