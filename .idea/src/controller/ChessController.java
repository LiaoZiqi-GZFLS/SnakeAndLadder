package controller;

import model.ChessMapMatrix;
import view.game.ChessBoardPanel;
import javax.swing.*;

public class ChessController {
    private int currentPlayer = 1;
    private int[] positions = {1, 1}; // 玩家当前位置
    private ChessBoardPanel board;
    private JButton p1Btn;
    private JButton p2Btn;
    private JLabel statusLabel;
    private final ChessMapMatrix chessmapMatrix;

    public ChessController(ChessBoardPanel board, JButton p1Btn, JButton p2Btn, JLabel statusLabel) {
        this.chessmapMatrix = ChessMapMatrix.createDefaultBoard(); // 初始化地图
        this.board = board;
        this.p1Btn = p1Btn;
        this.p2Btn = p2Btn;
        this.statusLabel = statusLabel;
    }

    public void restartGame() {
        System.out.println("Do restart game here");
    }

    //todo: add other methods such as loadGame, saveGame...

    public void handlePlayerMove(int player) {
        int baseSteps = 3;
        int currentPos = positions[player - 1];
        int tentativeNewPos = currentPos + baseSteps;

        // 限制临时位置不超过100
        tentativeNewPos = Math.min(tentativeNewPos, 100);

        // 设置特殊格子效果
        int effect = chessmapMatrix.getSpecialEffect(tentativeNewPos);
        if (effect == 1) {
            tentativeNewPos += 2; // +2步效果
        }
        //todo: add special effect here

        int finalNewPos = Math.min(tentativeNewPos, 100);
        positions[player - 1] = finalNewPos;

        // 更新棋子位置
        board.movePlayer(player, finalNewPos);

        // 切换玩家
        currentPlayer = 3 - currentPlayer;
        p1Btn.setEnabled(currentPlayer == 1);
        p2Btn.setEnabled(currentPlayer == 2);
        statusLabel.setText("Current: Player " + currentPlayer);
    }
}
