package view.game;

import model.ChessMapMatrix;
import javax.swing.*;
import java.awt.*;

public class ChessBoardPanel extends JPanel {
    private ChessGridComponent[][] grids;
    private PlayerPiece[] players;

    public ChessBoardPanel(int width, int height) {
        super();
        this.setSize(width, height);
        this.setLayout(null);
        initBoard(); // 初始化棋盘
    }

    public void initBoard() {
        // 初始化棋盘
        ChessMapMatrix matrix = ChessMapMatrix.createRandomBoard();
        grids = new ChessGridComponent[10][10];

        // 移除现有的棋盘组件
        this.removeAll();

        // 初始化格子
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int pos = row * 10 + col + 1;
                grids[row][col] = new ChessGridComponent(matrix.getSpecialEffect(pos), 50);
                grids[row][col].setLocation(col * 50, row * 50);
                this.add(grids[row][col]);
            }
        }

        // 初始化玩家
        players = new PlayerPiece[2];
        players[0] = new KingPiece(1, Color.RED, 0, 0);
        players[1] = new QueenPiece(2, Color.BLUE, 0, 0);
        grids[0][0].addPlayerPiece(players[0]);
        grids[0][0].addPlayerPiece(players[1]);

        this.repaint();
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
        this.repaint();
    }

    public void resetBoard() {
        // 将所有玩家移动到起点
        for (PlayerPiece player : players) {
            player.setPosition(0, 0);
            grids[0][0].addPlayerPiece(player);
        }
        this.repaint();
    }
}