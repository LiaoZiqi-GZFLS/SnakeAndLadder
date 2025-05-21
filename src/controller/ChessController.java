package controller;

import model.ChessMapMatrix;
import view.game.ChessBoardPanel;
import view.game.GlowPanel;
import view.login.UserFileManager;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Objects;
import java.util.Random;

public class ChessController {
    private static final int BOARD_SIZE = 100; // 棋盘大小
    private int currentPlayer = 1;
    private int[] positions = {1, 1}; // 玩家当前位置
    private final ChessBoardPanel board;
    private final JButton p1Btn;
    private final JButton p2Btn;
    private final JLabel statusLabel;
    private final JLabel gifLabel;
    private final JPanel gifPanel; // 主面板，用于添加GIF面板
    private final ChessMapMatrix chessmapMatrix;
    private final Random random = new Random();

    public ChessController(ChessBoardPanel board, JButton p1Btn, JButton p2Btn, JLabel statusLabel, JPanel gifPanel, GlowPanel glowPanel) {
        //this.chessmapMatrix = ChessMapMatrix.createDefaultBoard(); // 初始化地图
        this.chessmapMatrix = ChessMapMatrix.createRandomBoard();
        this.board = board;
        this.p1Btn = p1Btn;
        this.p2Btn = p2Btn;
        this.statusLabel = statusLabel;
        this.gifPanel = gifPanel;
        this.gifLabel = new JLabel();

        // 初始化GIF标签
        ImageIcon gifIcon = new ImageIcon("gif/1.mp4.gif"); // 默认GIF路径
        this.gifLabel.setIcon(gifIcon);
        this.gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.gifLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        this.gifPanel.setLayout(new BorderLayout());
        this.gifPanel.add(this.gifLabel);

        // 创建光晕面板
        glowPanel = new GlowPanel(300); // 设置光晕直径为200

        // 添加光晕面板到GIF面板的底部
        //glowPanel.setLocation(0, 0); // 设置光晕面板位置
        //gifPanel.add(glowPanel);

    }

    public void resetGame() {
        currentPlayer = 1;
        positions[0] = 1;
        positions[1] = 1;
        board.resetBoard();
        p1Btn.setEnabled(true);
        p2Btn.setEnabled(false);
        statusLabel.setText("Current Player: 1");

        // 重置GIF标签
        ImageIcon gifIcon = new ImageIcon("gif/1.mp4.gif");
        gifLabel.setIcon(gifIcon);
    }

    public void restartGame() {
        currentPlayer = 1;
        positions[0] = 1;
        positions[1] = 1;
        board.resetBoard();
        board.initBoard(); // 添加此行代码来初始化棋盘
        p1Btn.setEnabled(true);
        p2Btn.setEnabled(false);
        statusLabel.setText("Current Player: 1");
        // 重置GIF标签
        ImageIcon gifIcon = new ImageIcon("gif/1.mp4.gif");
        gifLabel.setIcon(gifIcon);
    }

    public void handlePlayerMove(int player) {
        if (player != currentPlayer) {
            return; // 不是当前玩家的回合，直接返回
        }

        int baseSteps = random.nextInt(6) + 1;

        // 更新GIF标签
        ImageIcon gifIcon = new ImageIcon("gif/" + baseSteps + ".mp4.gif"); // 替换为实际GIF路径
        gifLabel.setIcon(gifIcon);
        // 手动设置GIF标签的大小和位置
        this.gifLabel.repaint();
        this.gifPanel.repaint(); // 强制刷新GIF面板
        //System.out.println(baseSteps);

        int currentPos = positions[player - 1];
        int tentativeNewPos = currentPos + baseSteps;

        // 限制临时位置不超过棋盘大小
        tentativeNewPos = tentativeNewPos <= BOARD_SIZE ? tentativeNewPos : 2 * BOARD_SIZE - tentativeNewPos;

        // 设置特殊格子效果
        int effect = chessmapMatrix.getSpecialEffect(tentativeNewPos);
        if(Objects.equals(UserFileManager.readUsernameFromFile("mode.txt", true), "1")){
            if (effect == 1) {
                tentativeNewPos += 2; // +2步效果
                tentativeNewPos = (tentativeNewPos>BOARD_SIZE)?(BOARD_SIZE-tentativeNewPos%BOARD_SIZE):tentativeNewPos; // 再次检查是否超过棋盘大小
            }
            if(effect == 2){
                tentativeNewPos -= 1;
                tentativeNewPos = (tentativeNewPos>BOARD_SIZE)?(BOARD_SIZE-tentativeNewPos%BOARD_SIZE):tentativeNewPos;
            }
        }
        if(Objects.equals(UserFileManager.readUsernameFromFile("mode.txt", true), "2")){
            tentativeNewPos += effect;
            tentativeNewPos = (tentativeNewPos>BOARD_SIZE)?(BOARD_SIZE-tentativeNewPos%BOARD_SIZE):tentativeNewPos;
        }


        int finalNewPos = tentativeNewPos;
        positions[player - 1] = finalNewPos;

        // 更新棋子位置
        board.movePlayer(player, finalNewPos);

        // 切换玩家
        currentPlayer = 3 - currentPlayer;
        p1Btn.setEnabled(currentPlayer == 1);
        p2Btn.setEnabled(currentPlayer == 2);
        statusLabel.setText("Current Player:  " + currentPlayer);

        // 胜利条件检查
        if (finalNewPos == BOARD_SIZE) {
            String msg = "玩家" + player + "胜利!";
            JOptionPane.showMessageDialog(null, msg, "游戏结束", JOptionPane.INFORMATION_MESSAGE);
            // 询问是否重新开始游戏
            int choice = JOptionPane.showConfirmDialog(null, "是否重新开始游戏?", "提示", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                resetGame();
            }
        }
    }

    public void saveGame(String filePath) {
        // 实现保存游戏状态的逻辑
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            GameSaveData saveData = new GameSaveData(currentPlayer, positions);
            oos.writeObject(saveData);
            JOptionPane.showMessageDialog(null, "游戏保存成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "保存失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void loadGame(String filePath) {
        // 实现加载游戏状态的逻辑
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            GameSaveData saveData = (GameSaveData) ois.readObject();
            currentPlayer = saveData.getCurrentPlayer();
            positions = saveData.getPositions();
            board.resetBoard();
            board.movePlayer(1, positions[0]);
            board.movePlayer(2, positions[1]);
            p1Btn.setEnabled(currentPlayer == 1);
            p2Btn.setEnabled(currentPlayer == 2);
            statusLabel.setText("Current Player: " + currentPlayer);
            JOptionPane.showMessageDialog(null, "游戏加载成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "加载失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // 内部类，用于保存游戏状态
    private static class GameSaveData implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int currentPlayer;
        private final int[] positions;

        public GameSaveData(int currentPlayer, int[] positions) {
            this.currentPlayer = currentPlayer;
            this.positions = positions.clone(); // 复制数组以避免引用问题
        }

        public int getCurrentPlayer() {
            return currentPlayer;
        }

        public int[] getPositions() {
            return positions.clone(); // 返回数组复制，确保数据安全
        }
    }
}