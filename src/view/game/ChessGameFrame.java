package view.game;

import controller.ChessController;
import view.login.IndexFrame;
import view.login.LoginFrame;
import view.login.UserFileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGameFrame extends JFrame {
    private ChessController controller;
    private final ChessBoardPanel chessPanel;
    private final JButton player1Btn;
    private final JButton player2Btn;
    private final JButton restartBtn;
    private final JButton resetBtn;
    private final JButton loadBtn;
    private final JButton saveBtn;
    private final JButton exitBtn;
    private final JLabel statusLabel;
    private final JLabel gifLabel; // 新增GIF标签
    private final JPanel gifPanel;
    private final GlowPanel glowPanel; // 光晕面板
    // 在类的成员变量中添加用户名标签和Logout按钮
    private final JLabel usernameLabel;
    private final JButton logoutBtn;

    public ChessGameFrame(int width, int height) {
        setTitle("2025 CS110 Project Demo");
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        Image icon = Toolkit.getDefaultToolkit().getImage("favicon.png");
        this.setIconImage(icon);

        // 设置背景图片
        setContentPane(createBackgroundPanel());

        // 使用 BorderLayout 作为主布局管理器
        setLayout(new BorderLayout(10, 10));

        // 棋盘面板
        chessPanel = new ChessBoardPanel(500, 500);
        chessPanel.setPreferredSize(new Dimension(500, 500));
        chessPanel.setOpaque(false); // 设置棋盘面板为透明

        // 控制面板
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        controlPanel.setOpaque(false); // 设置控制面板为透明

        // 创建并添加按钮和标签
        player1Btn = createStyledButton("Player 1");
        player2Btn = createStyledButton("Player 2");
        player2Btn.setEnabled(false);
        restartBtn = createStyledButton("Restart");
        resetBtn = createStyledButton("Reset");
        saveBtn = createStyledButton("Save");
        loadBtn = createStyledButton("Load");
        exitBtn = createStyledButton("Exit");
        statusLabel = createStyledLabel("Current Player: 1");

        controlPanel.add(player1Btn);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(player2Btn);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(resetBtn);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(restartBtn);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(exitBtn);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(saveBtn);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(loadBtn);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(statusLabel);

        // 添加到主面板
        add(chessPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.LINE_END);

        // 创建一个占位面板，用于在左侧占据空间
        JPanel placeholderPanel = new JPanel();
        placeholderPanel.setOpaque(false); // 确保背景透明
        placeholderPanel.setPreferredSize(new Dimension(50, 50));

        // 将占位面板添加到 BorderLayout.WEST 位置
        add(placeholderPanel, BorderLayout.WEST);

        // 初始化控制器并添加事件监听器
        player1Btn.addActionListener(e -> {
            controller.handlePlayerMove(1);
            player2Btn.requestFocusInWindow(); // 切换焦点到 Player 2 按钮
        });
        player2Btn.addActionListener(e -> {
            controller.handlePlayerMove(2);
            player1Btn.requestFocusInWindow(); // 切换焦点到 Player 1 按钮
        });
        resetBtn.addActionListener(e -> {
            controller.resetGame();
            player1Btn.requestFocusInWindow(); // 切换焦点到 Player 1 按钮
        });
        restartBtn.addActionListener(e -> {
            controller.restartGame();
            player1Btn.requestFocusInWindow(); // 切换焦点到 Player 1 按钮
        });
        exitBtn.addActionListener(e -> {
            System.exit(0);
        });
        saveBtn.addActionListener(e -> { // 添加保存按钮的事件监听器
            String path = JOptionPane.showInputDialog(this, "Input file path to save:");
            if (path != null && !path.isEmpty()) {
                controller.saveGame(path);
            }
        });
        loadBtn.addActionListener(e -> {
            String path = JOptionPane.showInputDialog(this, "Input file path to load:");
            if (path != null && !path.isEmpty()) {
                controller.loadGame(path);
            }
            chessPanel.requestFocusInWindow(); // 启用键盘监听
        });

        // 设置初始焦点给 Player 1 按钮
        player1Btn.requestFocusInWindow();

        setFocusTraversalPolicy(new FocusTraversalPolicy() {
            @Override
            public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
                return null;
            }

            @Override
            public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
                return null;
            }

            @Override
            public Component getDefaultComponent(Container focusCycleRoot) {
                return player1Btn;
            }

            @Override
            public Component getLastComponent(Container focusCycleRoot) {
                return loadBtn;
            }

            @Override
            public Component getFirstComponent(Container focusCycleRoot) {
                return player1Btn;
            }
        });

        // 添加键盘快捷键支持
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                //System.out.println("Keypress:"+e.getKeyCode());
                // 按回车键自动摇骰子
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // 检查当前是否有玩家按钮被选中
                    // 获取当前拥有焦点的组件
                    Component focusedComponent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                    if (focusedComponent instanceof JButton) {
                        ((JButton) focusedComponent).doClick();
                    }
                }
                // Ctrl+S 保存游戏
                else if (e.getKeyCode() == java.awt.event.KeyEvent.VK_S && e.isControlDown()) {
                    String path = JOptionPane.showInputDialog(ChessGameFrame.this, "Input file path to save:");
                    if (path != null && !path.isEmpty()) {
                        controller.saveGame(path);
                    }
                }
                // Ctrl+L 加载游戏
                else if (e.getKeyCode() == java.awt.event.KeyEvent.VK_L && e.isControlDown()) {
                    String path = JOptionPane.showInputDialog(ChessGameFrame.this, "Input file path to load:");
                    if (path != null && !path.isEmpty()) {
                        controller.loadGame(path);
                    }
                }
                // Ctrl+R 重置游戏
                else if (e.getKeyCode() == java.awt.event.KeyEvent.VK_R && e.isControlDown()) {
                    controller.resetGame();
                }
                // Ctrl+E 重新开始游戏
                else if (e.getKeyCode() == java.awt.event.KeyEvent.VK_E && e.isControlDown()) {
                    controller.restartGame();
                }
            }
        });

        setFocusable(true);
        setFocusTraversalPolicy(new FocusTraversalPolicy() {
            @Override
            public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
                return null;
            }

            @Override
            public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
                return null;
            }

            @Override
            public Component getDefaultComponent(Container focusCycleRoot) {
                return player1Btn;
            }

            @Override
            public Component getLastComponent(Container focusCycleRoot) {
                return loadBtn;
            }

            @Override
            public Component getFirstComponent(Container focusCycleRoot) {
                return player1Btn;
            }
        });

        setFocusable(true);

        // 使用 KeyboardFocusManager 监听全局键盘事件
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                // 按回车键自动摇骰子
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    //System.out.println("Key pressed: " + e.getKeyCode()); // 添加日志输出以确认按键事件是否被触发

                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        // 获取当前拥有焦点的组件
                        Component focusedComponent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                        if (focusedComponent instanceof JButton) {
                            ((JButton) focusedComponent).doClick();
                        }
                    }
                    // Ctrl+S 保存游戏
                    else if (e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) {
                        String path = JOptionPane.showInputDialog(ChessGameFrame.this, "Input file path to save:");
                        if (path != null && !path.isEmpty()) {
                            controller.saveGame(path);
                        }
                    }
                    // Ctrl+L 加载游戏
                    else if (e.getKeyCode() == KeyEvent.VK_L && e.isControlDown()) {
                        String path = JOptionPane.showInputDialog(ChessGameFrame.this, "Input file path to load:");
                        if (path != null && !path.isEmpty()) {
                            controller.loadGame(path);
                        }
                    }
                    // Ctrl+R 重置游戏
                    else if (e.getKeyCode() == KeyEvent.VK_R && e.isControlDown()) {
                        controller.resetGame();
                    }
                    // Ctrl+E 重新开始游戏
                    else if (e.getKeyCode() == KeyEvent.VK_E && e.isControlDown()) {
                        controller.restartGame();
                    }
                }
                return false; // 返回 false 表示事件可以继续传递给其他监听器
            }
        });

        // 创建光晕面板
        glowPanel = new GlowPanel(100); // 设置光晕直径为200

        // 创建GIF标签和面板
        gifPanel = new JPanel();
        gifPanel.setOpaque(false);
        gifPanel.setLayout(new BorderLayout());

        // 添加光晕面板到GIF面板的底部
        glowPanel.setLocation(0, 0); // 设置光晕面板位置
        glowPanel.setOpaque(false);

        // 添加GIF标签到GIF面板的中心
        gifLabel = new JLabel();
        ImageIcon gifIcon = new ImageIcon("gif/1.mp4.gif"); // 替换为实际GIF路径
        gifLabel.setIcon(gifIcon);
        gifLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gifLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        gifPanel.add(gifLabel,BorderLayout.SOUTH);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setOpaque(false);
        int triLen = 140;
        TransparentTriangle transparentTriangle1 = new TransparentTriangle(triLen,triLen,false);
        TransparentTriangle transparentTriangle2 = new TransparentTriangle(triLen,triLen);
        transparentTriangle1.setOpaque(false);
        transparentTriangle2.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(gifPanel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(transparentTriangle1,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(transparentTriangle2,gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        //mainPanel.add(glowPanel,gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(mainPanel,BorderLayout.EAST);

        // 添加到主面板的右下角
        //add(gifPanel, BorderLayout.SOUTH);
        //add(mainPanel, BorderLayout.SOUTH);
        add(buttonPanel,BorderLayout.SOUTH);

        // 添加用户名和Logout按钮到左上角
        JPanel userPanel = new JPanel();
        userPanel.setOpaque(false);
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        usernameLabel = new JLabel("Username: " + getLoggedInUsername()); // 获取当前登录用户名
        usernameLabel.setFont(new Font("SimHei", Font.BOLD, 14));
        usernameLabel.setForeground(Color.WHITE);

        logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        logoutBtn.setPreferredSize(new Dimension(100, 30));
        logoutBtn.setBackground(new Color(0, 0, 0, 150)); // 半透明黑色背景
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setOpaque(true);

        logoutBtn.addActionListener(e -> {
            // Logout逻辑
            int choice = JOptionPane.showConfirmDialog(null, "确定要退出登录吗?", "提示", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                // 隐藏当前窗口
                //setVisible(false);
                dispose();
                // 打开登录窗口
                LoginFrame loginFrame = new LoginFrame(280, 280);
                loginFrame.setVisible(true);
                loginFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        IndexFrame indexFrame = new IndexFrame(1080,720);
                        indexFrame.setVisible(true);
                    }
                });
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //原本显示
                System.out.println("Logout");
                UserFileManager.updateUsername("NaN","username.txt");
                usernameLabel.setText("Username: " + getLoggedInUsername());
                repaint();
            }
        });

        userPanel.add(usernameLabel);
        userPanel.add(logoutBtn);

        // 添加到主面板的左上角
        add(userPanel, BorderLayout.NORTH);

        controller = new ChessController(chessPanel, player1Btn, player2Btn, statusLabel, gifPanel, glowPanel);

        pack();
    }

    private String getLoggedInUsername() {
        return UserFileManager.readUsernameFromFile("username.txt");
    }

    private void addMouseListenerToButton(JButton button) {
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 鼠标点击事件
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // 鼠标按下事件
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // 鼠标释放事件
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // 鼠标进入按钮区域时刷新界面
                ChessGameFrame.this.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // 鼠标离开按钮区域时刷新界面
                ChessGameFrame.this.repaint();
            }
        });
    }

    private JPanel createBackgroundPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图片
                Image backgroundImage = new ImageIcon("bkg.gif").getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setOpaque(true); // 确保背景面板不透明
        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));
        // 设置半透明黑色背景
        button.setBackground(new Color(0, 0, 0, 150)); // 150 是 alpha 值，范围 0-255
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // 设置按钮的透明度
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}