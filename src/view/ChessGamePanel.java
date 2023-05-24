package view;

import com.sun.tools.javac.Main;
import controller.GameController;

import Stream.Audio;

import model.PlayerColor;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGamePanel extends ImagePanel {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;

    private final int ONE_CHESS_SIZE;


    private UserInfo currentUser;

    private JLabel player = this.addPlayerLabel();

    private JLabel round = this.addRoundCounterLabel();
    private JLabel redtime = addRedTimeLable();
    private JLabel bluetime = addBLueTimeLabel();
    private ChessboardComponent chessboardComponent;


    public void switchPlayer(){
        if (chessboardComponent.getGameController().getCurrentPlayer() == PlayerColor.RED){
            player.setText("Red's turn");
        }
        else{
            player.setText("Blue's turn");
        }
    }

    public void addRounds(){
        round.setText("Round: "+chessboardComponent.getGameController().getRoundCounter());
    }





    public ChessGamePanel(int width, int height) {
//        setTitle("2023 CS109 Project Demo"); //设置标题
        super("D:\\JavaProject\\浅色1.jpg");
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;

        setSize(WIDTH, HEIGHT);
//        setLocationRelativeTo(null); // Center the window.
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addChangeBackgroundButton();
        addSaveButton();
        addLoadButton();
        addRestartButton();
        addAudioButton();
        addUndoButton();
        addPlayerLabel();
        addRoundCounterLabel();
        add(player);
        add(round);
        add(redtime);
        add(bluetime);
    }



    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }
    public ChessGamePanel getChessGamePanel(){
        return this;
    }



    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGHT / 5, HEIGHT / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */

    public JLabel addRedTimeLable(){
        JLabel redtime = new JLabel();
        redtime.setLocation(HEIGHT, HEIGHT / 10 +220 );
        redtime.setSize(200,50);
        redtime.setFont(new Font("Rockwell", Font.BOLD, 20));
        return redtime;
    }

    public JLabel addBLueTimeLabel(){
        JLabel bluetime = new JLabel();
        bluetime.setLocation(HEIGHT, HEIGHT / 10 + 580);
        bluetime.setSize(200,50);
        bluetime.setFont(new Font("Rockwell", Font.BOLD, 20));
        return bluetime;
    }

    public JLabel getBluetime() {
        return bluetime;
    }

    public JLabel getRedtime() {
        return redtime;
    }

    public  JLabel addPlayerLabel() {
        String currentPlayer = "Blue's turn";
        JLabel l = new JLabel(currentPlayer);
        l.setLocation(HEIGHT + 240, HEIGHT / 10);
        l.setSize(200, 60);
        l.setFont(new Font("Rockwell", Font.BOLD, 20));

        return l;

    }





    public JLabel addRoundCounterLabel() {
        String currentRound= "Round: 1" ;
        JLabel l = new JLabel(currentRound);
        l.setLocation(HEIGHT, HEIGHT / 10);
        l.setSize(200, 60);
        l.setFont(new Font("Rockwell", Font.BOLD, 20));

        return l;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addChangeBackgroundButton() {
        JButton button = new JButton("更换背景");
//        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.addActionListener(e -> {
            ImageIcon imageIcon1 = new ImageIcon("D:\\JavaProject\\浅色1.jpg");
            ImageIcon imageIcon2 = new ImageIcon("D:\\JavaProject\\粉.jpg");
            ImageIcon imageIcon3 = new ImageIcon("D:\\JavaProject\\花.jpg");
            if(getImageIcon().getImage().equals(imageIcon1.getImage())){
                setImage("D:\\JavaProject\\粉.jpg");
            } else if (getImageIcon().getImage().equals(imageIcon2.getImage())) {
                setImage("D:\\JavaProject\\花.jpg");
            } else if (getImageIcon().getImage().equals(imageIcon3.getImage())) {
                setImage("D:\\JavaProject\\浅色1.jpg");
            }
            this.repaint();
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("楷体", Font.BOLD, 20));
        add(button);
    }

    private void addSaveButton(){
        JButton button = new JButton("Save");
        button.setLocation(HEIGHT,HEIGHT/10 + 240);
        button.setSize(200,60);
        button.setFont(new Font("Rockwell",Font.BOLD,20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Saving current game...");
            chessboardComponent.getGameController().save();
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGHT, HEIGHT / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            chessboardComponent.getGameController().load(path);
        });
    }

    private void addRestartButton(){
        JButton button = new JButton("Restart");
        button.setLocation(HEIGHT, HEIGHT / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Game Restarted");
            chessboardComponent.getGameController().restart();
        });
    }

    public void addAudioButton(){
        JButton audioButton = new JButton("BGM");
        audioButton.setLocation(HEIGHT, HEIGHT / 10 + 560);
        audioButton.setSize(100,30);
        audioButton.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(audioButton);

        audioButton.addActionListener(e -> {

            if(Audio.bgmAudio==null||(!Audio.bgmAudio.isAlive())){
                Audio.playAudio("D:\\JavaProject\\bgMusic.wav");
            } else if (Audio.bgmAudio.isAlive()) {
                System.out.println("Here");
                Audio.bgmAudio.stop();
            }

        });
    }

    public void addUndoButton(){
        JButton undoButton = new JButton("Undo");
        undoButton.setLocation(HEIGHT+250, HEIGHT / 10 +120 );
        undoButton.setSize(100,30);
        undoButton.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(undoButton);

        undoButton.addActionListener(e -> {
            chessboardComponent.getGameController().undo();
        });
    }

    public UserInfo getCurrentUser(){
        return currentUser;
    }

    public UserInfo readCurrentUser(){
        File file = new File("./users.sav/");
        UserInfo[] userInfo = new UserInfo[1];
        try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
            userInfo = (UserInfo[]) is.readObject();
        }catch (IOException | ClassNotFoundException g){
            g.printStackTrace();
        }
        currentUser = userInfo[0];


        return currentUser;
    }



}
