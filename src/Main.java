import view.login.LoginFrame;
import view.login.IndexFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //LoginFrame loginFrame = new LoginFrame(280,280);
            //loginFrame.setVisible(true);
            IndexFrame indexFrame = new IndexFrame(1080,720);
            indexFrame.setVisible(true);
        });
    }
}
