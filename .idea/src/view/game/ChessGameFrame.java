package view.game;

import javax.swing.*;
import java.awt.*;

import controller.ChessController;
import view.FrameUtil;

public class ChessGameFrame extends JFrame {
    private ChessController controller;
    private ChessBoardPanel chessPanel;
    private JButton player1Btn;
    private JButton player2Btn;
    private JButton restartBtn;
    private JButton loadBtn;
    private JLabel statusLabel;

    public ChessGameFrame(int width, int height) {
        this.setTitle("2025 CS110 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        chessPanel = new ChessBoardPanel(500, 500);
        chessPanel.setLocation(50, 50);
        this.add(chessPanel);

        this.player1Btn = FrameUtil.createButton(this, "Player 1", new Point(600, 100), 120, 40);
        this.player2Btn = FrameUtil.createButton(this, "Player 2", new Point(600, 200), 120, 40);
        this.player2Btn.setEnabled(false);
        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(600, 300), 120, 40);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(600, 400), 120, 40);
        this.statusLabel = FrameUtil.createJLabel(this, new Point(600, 50), 150, 30, "Current: Player 1");

        this.controller = new ChessController(chessPanel, player1Btn, player2Btn, statusLabel);
        this.player1Btn.addActionListener(e -> this.controller.handlePlayerMove(1));
        this.player2Btn.addActionListener(e -> this.controller.handlePlayerMove(2));
        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            chessPanel.requestFocusInWindow();//enable key listener
        });
        this.loadBtn.addActionListener(e -> {
            String string = JOptionPane.showInputDialog(this, "Input path:");
            System.out.println(string);
            chessPanel.requestFocusInWindow();//enable key listener
        });

        //todo: add other button here

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}