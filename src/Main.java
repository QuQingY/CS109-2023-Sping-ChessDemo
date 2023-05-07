import controller.GameController;
import model.Chessboard;
import view.ChessGamePanel;
import view.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyFrame myFrame = new MyFrame();
            myFrame.setBounds(300,200,970,700);
            myFrame.setVisible(true);
            myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            ChessGamePanel mainPanel = new ChessGamePanel(1100, 810);

            GameController gameController = new GameController(myFrame.getMainPanel().getChessboardComponent(), new Chessboard());
//            myFrame.getMainPanel().setVisible(true);
        });
    }
}
