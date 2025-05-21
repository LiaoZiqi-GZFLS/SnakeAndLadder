package view.login;

import view.game.ChessGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class RegisterFrame extends JFrame {
    private JTextField username;
    private JPasswordField password; // 使用密码字段代替文本字段
    private JButton registerBtn;

    public RegisterFrame(int width, int height) {
        setTitle("Register Frame");
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

        // 创建标签和文本框
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);
        passLabel.setForeground(Color.WHITE);

        // 优化输入框样式
        username = new JTextField();
        password = new JPasswordField(); // 使用密码字段
        username.setPreferredSize(new Dimension(150, 30));
        password.setPreferredSize(new Dimension(150, 30));
        username.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        password.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // 设置输入框样式
        //setTextFieldStyle(username);
        //setTextFieldStyle(password);

        // 创建按钮
        registerBtn = new JButton("Register");

        // 设置按钮样式
        setButtonStyle(registerBtn);

        // 添加鼠标事件监听器
        addMouseListenerToButton(registerBtn);

        // 添加组件到面板
        addComponent(panel, userLabel, gbc, 0, 0, 1, 1);
        addComponent(panel, username, gbc, 1, 0, 1, 1);
        addComponent(panel, passLabel, gbc, 0, 1, 1, 1);
        addComponent(panel, password, gbc, 1, 1, 1, 1);
        addComponent(panel, registerBtn, gbc, 1, 2, 1, 1);

        // 添加事件监听器
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameText = username.getText();
                String passwordText = new String(password.getPassword());
                System.out.println("Username = " + usernameText);
                System.out.println("Password = " + passwordText);

                if (usernameText.isEmpty() || passwordText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "用户名或密码不能为空！", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (usernameText.contains(" ") || passwordText.contains(" ")) {
                    JOptionPane.showMessageDialog(null, "用户名或密码不能包含空格！", "提示", JOptionPane.ERROR_MESSAGE);
                    username.setText("");
                    password.setText("");
                    return;
                }

                boolean userExists = false;
                for (String line : readFile()) {
                    String[] infos = line.split(" ");
                    if (infos.length == 2) {
                        if (usernameText.equals(infos[0])) {
                            userExists = true;
                            break;
                        }
                    }
                }

                if (userExists) {
                    JOptionPane.showMessageDialog(null, "用户名已存在！", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                writeInfo(usernameText + " " + passwordText);

                LoginFrame loginFrame = new LoginFrame(width, height);
                loginFrame.setVisible(true);
                loginFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        IndexFrame indexFrame = new IndexFrame(1080, 720);
                        indexFrame.setVisible(true);
                    }
                });
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dispose();
            }
        });
    }

    // 设置按钮样式的方法
    private void setButtonStyle(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBackground(new Color(0, 0, 0, 150)); // 半透明黑色背景
        button.setPreferredSize(new Dimension(100, 30));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //button.setContentAreaFilled(false); // 不填充按钮内容区域
        button.setFocusPainted(false); // 不绘制焦点边框
    }

    // 设置输入框样式的方法
    private void setTextFieldStyle(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setForeground(Color.BLACK);
        textField.setOpaque(true);
        textField.setBackground(new Color(240, 240, 240)); // 浅灰色背景
        textField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        textField.setHorizontalAlignment(JTextField.LEFT);
    }

    // 为按钮添加鼠标事件监听器
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
                RegisterFrame.this.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // 鼠标离开按钮区域时刷新界面
                RegisterFrame.this.repaint();
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

    public void writeInfo(String info) {
        String filePath = "register.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(info);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readFile() {
        String filePath = "register.txt";
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("文件已经存在");
        } else {
            try {
                file.createNewFile();
                System.out.println("文件创建成功");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "文件创建失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                arrayList.add(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "文件读取失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
        return arrayList;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterFrame registerFrame = new RegisterFrame(300, 200);
            registerFrame.setVisible(true);
        });
    }
}