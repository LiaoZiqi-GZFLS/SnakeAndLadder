package view.game;

import view.FrameUtil;
import view.login.IndexFrame;
import view.login.LoginFrame;
import view.login.UserFileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectFrame extends JFrame {
    private JButton mode1Btn;
    private JButton mode2Btn;
    private JButton mode3Btn;

    public SelectFrame(int width, int height) {
        setTitle("Select Frame");
        setSize(width, height);
        setResizable(false); // 禁止窗口缩放
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Image icon = Toolkit.getDefaultToolkit().getImage("favicon.png");
        this.setIconImage(icon);

        // 创建透明的 JPanel
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        setContentPane(panel);

        // 使用 GridBagLayout 布局管理器
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // 加载并设置背景图片
        ImageIcon imageIcon = new ImageIcon("bkg.png");
        JLabel backgroundLabel = new JLabel(imageIcon);
        getLayeredPane().add(backgroundLabel, new Integer(Integer.MIN_VALUE));
        backgroundLabel.setBounds(0, 0, width, height);

        // 创建按钮
        mode1Btn = new JButton("Mode 1");
        mode2Btn = new JButton("Mode 2");
        mode3Btn = new JButton("Mode 3");

        // 设置按钮样式
        setButtonStyle(mode1Btn);
        setButtonStyle(mode2Btn);
        setButtonStyle(mode3Btn);

        // 添加鼠标事件监听器
        addMouseListenerToButton(mode1Btn);
        addMouseListenerToButton(mode2Btn);
        addMouseListenerToButton(mode3Btn);

        // 添加组件到面板
        addComponent(panel, mode1Btn, gbc, 0, 0, 1, 1);
        addComponent(panel, mode2Btn, gbc, 0, 1, 1, 1);
        addComponent(panel, mode3Btn, gbc, 0, 2, 1, 1);

        // 添加事件监听器
        mode1Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 模式一的逻辑
                UserFileManager.updateUsername("1","mode.txt");
                ChessGameFrame gameFrame = new ChessGameFrame(1000, 800);
                // 添加 Window Listener
                gameFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        IndexFrame indexFrame = new IndexFrame(1080,720);
                        indexFrame.setVisible(true);
                    }
                });
                gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 处理窗口关闭时不退出应用
                gameFrame.setVisible(true);
                dispose();
            }
        });

        mode2Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 模式二的逻辑
                UserFileManager.updateUsername("2","mode.txt");
                ChessGameFrame gameFrame = new ChessGameFrame(1000, 800);
                // 添加 Window Listener
                gameFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        IndexFrame indexFrame = new IndexFrame(1080,720);
                        indexFrame.setVisible(true);
                    }
                });
                gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 处理窗口关闭时不退出应用
                gameFrame.setVisible(true);
                dispose();
            }
        });

        mode3Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 模式三的逻辑
                UserFileManager.updateUsername("3","mode.txt");
                ChessGameFrame gameFrame = new ChessGameFrame(1000, 800);
                // 添加 Window Listener
                gameFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        IndexFrame indexFrame = new IndexFrame(1080,720);
                        indexFrame.setVisible(true);
                    }
                });
                gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 处理窗口关闭时不退出应用
                gameFrame.setVisible(true);
                dispose();
            }
        });
    }

    // 设置按钮样式的方法
    private void setButtonStyle(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBackground(new Color(0, 0, 0, 150)); // 半透明黑色背景
        button.setPreferredSize(new Dimension(200, 50));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false); // 不绘制焦点边框
    }

    // 为按钮添加鼠标事件监听器
    private void addMouseListenerToButton(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // 鼠标进入按钮区域时刷新界面
                SelectFrame.this.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // 鼠标离开按钮区域时刷新界面
                SelectFrame.this.repaint();
            }
        });
    }

    private void addComponent(Container container, Component component, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        container.add(component, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SelectFrame selectFrame = new SelectFrame(400, 300);
            selectFrame.setVisible(true);
        });
    }
}