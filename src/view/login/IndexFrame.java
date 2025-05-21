package view.login;

import view.FrameUtil;
import view.game.ChessGameFrame;
import view.game.SelectFrame;
import view.login.UserFileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class IndexFrame extends JFrame {
    private JButton submitBtn;
    private JButton loginBtn;
    private JButton registerBtn;


    public IndexFrame(int width, int height) {
        this.setTitle("Index Frame");
        this.setSize(width, height);

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
        ImageIcon imageIcon = new ImageIcon("bkg.gif");
        JLabel backgroundLabel = new JLabel(imageIcon);
        getLayeredPane().add(backgroundLabel, Integer.valueOf(Integer.MIN_VALUE));

        // 调整位置和大小以适应图像尺寸
        backgroundLabel.setBounds(0, 0, 2534, 1080);


        JLabel Label1 = new JLabel(" ");
        JLabel Label2 = new JLabel("Snakes and Ladders");
        Label1.setFont(new Font("Arial", Font.BOLD, 24));
        Label2.setFont(new Font("Arial", Font.BOLD, 24));
        Label1.setForeground(Color.WHITE);
        Label2.setForeground(Color.WHITE);


        submitBtn = new JButton("Guest Confirm");
        loginBtn = new JButton("Login");
        registerBtn = new JButton("Register");

        // 设置按钮大小
        submitBtn.setPreferredSize(new Dimension(100, 40));
        loginBtn.setPreferredSize(new Dimension(100, 40));
        registerBtn.setPreferredSize(new Dimension(120, 40)); // 增加宽度

        // 设置按钮和标签样式
        setComponentStyle(submitBtn);
        setComponentStyle(loginBtn);
        setComponentStyle(registerBtn);
        setComponentStyle(Label1);
        setComponentStyle(Label2);

        // 添加组件到面板
        addComponent(panel, Label2, gbc, 0, 0, 2, 1);
        addComponent(panel, Label1, gbc, 0, 1, 2, 1);
        addComponent(panel, loginBtn, gbc, 0, 2, 1, 1);
        addComponent(panel, registerBtn, gbc, 1, 2, 1, 1);
        addComponent(panel, submitBtn, gbc, 0, 3, 2, 1);

        submitBtn.addActionListener(e -> {
            UserFileManager.updateUsername("Guest","username.txt");
            /*ChessGameFrame gameFrame = new ChessGameFrame(1000, 800);
            gameFrame.setVisible(true);
            // 添加 Window Listener
            gameFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    IndexFrame indexFrame = new IndexFrame(1080,720);
                    indexFrame.setVisible(true);
                }
            });
            gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 处理窗口关闭时不退出应用*/
            SelectFrame selectFrame = new SelectFrame(280,280);
            selectFrame.setVisible(true);
            selectFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    IndexFrame indexFrame = new IndexFrame(1080,720);
                    indexFrame.setVisible(true);
                }
            });
            selectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.dispose();

        });
        loginBtn.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame(280,280);
            loginFrame.setVisible(true);
            // 添加 Window Listener
            loginFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    IndexFrame indexFrame = new IndexFrame(1080,720);
                    indexFrame.setVisible(true);
                }
            });
            loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 处理窗口关闭时不退出应用
            this.dispose();
        });
        registerBtn.addActionListener(e ->{
            RegisterFrame registerFrame=new RegisterFrame(280,280);
            registerFrame.setVisible(true);
            // 添加 Window Listener
            registerFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    IndexFrame indexFrame = new IndexFrame(1080,720);
                    indexFrame.setVisible(true);
                }
            });
            registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 处理窗口关闭时不退出应用
            this.dispose();
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setComponentStyle(JComponent component) {
        component.setFont(new Font("Arial", Font.BOLD, 18));
        component.setForeground(Color.WHITE);
        component.setOpaque(true);
        component.setBackground(new Color(0, 0, 0, 150));
        component.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

    public ArrayList<String> readFile(){
        String filePath ="register.txt";
        File file =new File(filePath);
        if(file.exists()) {
            System.out.println("文件已经存在");
        }else {
            try {
                file.createNewFile();
                System.out.println("文件创建成功");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            Scanner in =new Scanner(file);
            while (in.hasNextLine()){
                arrayList.add(in.nextLine());
            }
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        return arrayList;
    }
}