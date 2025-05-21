package view.game;

import model.ChessMapMatrix;
import javax.swing.*;
import java.awt.*;

public class ChessBoardPanel extends JPanel {
    private final ChessGridComponent[][] grids;
    private final PlayerPiece[] players;

    public ChessBoardPanel(int width, int height) {
        setSize(width, height);
        setLayout(null);

        // 初始化棋盘
        ChessMapMatrix matrix = ChessMapMatrix.createDefaultBoard();
        grids = new ChessGridComponent[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int pos = row * 10 + col + 1;
                grids[row][col] = new ChessGridComponent(matrix.getSpecialEffect(pos), 50);
                grids[row][col].setLocation(col * 50, row * 50);
                add(grids[row][col]);
            }
        }

        // 初始化玩家
        players = new PlayerPiece[2];
        players[0] = new PlayerPiece(1, Color.RED, 0, 0);
        players[1] = new PlayerPiece(2, Color.BLUE, 0, 0);

        grids[0][0].addPlayerPiece(players[0]);
        grids[0][0].addPlayerPiece(players[1]);
    }

    public void movePlayer(int playerId, int newPos) {
        PlayerPiece piece = players[playerId - 1];
        int oldRow = piece.getRow();
        int oldCol = piece.getCol();
        int newRow = (newPos - 1) / 10;
        int newCol = (newPos - 1) % 10;

        // 从旧位置移除
        ChessGridComponent oldGrid = grids[oldRow][oldCol];
        oldGrid.removePlayerPiece(piece);

        // 添加到新位置
        ChessGridComponent newGrid = grids[newRow][newCol];
        newGrid.addPlayerPiece(piece);
        piece.setPosition(newRow, newCol); // 更新行列

        repaint();
    }
}