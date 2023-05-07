package view;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
//    private final int WIDTH;
//    private final int HEIGHT;
    private CardLayout cardLayout;
    ChessGamePanel mainPanel = new ChessGamePanel(1100, 810);

    public MyFrame(){
//        this.WIDTH = width;
//        this.HEIGHT = height;
        this.cardLayout= new CardLayout();
        this.setLayout(cardLayout);

        this.add(mainPanel,"main");
        drawFirstPanel();
        cardLayout.show(this.getContentPane(),"first");
        drawLoginPanel();
        drawRegisterPanel();

//        ChessGamePanel mainPanel = new ChessGamePanel(1100, 810);
    }

    public void drawFirstPanel(){
        JPanel firstPanel = new ImagePanel("D:\\JavaProject\\forest.jpg");
        firstPanel.setLayout(new BoxLayout(firstPanel,BoxLayout.Y_AXIS));

        JLabel title = makeTitle("Welcome to the Jungle");
        JButton loginButton = makeButton("登录");
        JButton registerButton = makeButton("注册");

        firstPanel.add(title);
        firstPanel.add(Box.createRigidArea(new Dimension(10,40)));
        firstPanel.add(loginButton);
        loginButton.addActionListener(e -> {cardLayout.show(this.getContentPane(),"login");});
        firstPanel.add(Box.createRigidArea(new Dimension(10,40)));
        firstPanel.add(registerButton);
        registerButton.addActionListener(e -> {cardLayout.show(this.getContentPane(),"register");});
        this.add(firstPanel,"first");

    }

    public void drawLoginPanel(){
        JPanel loginPanel = makeUserPanel("login","Please Log in");
        this.add(loginPanel,"login");
    }

    public void drawRegisterPanel(){
        JPanel registerPanel = makeUserPanel("register","Please Register an Account");
        this.add(registerPanel,"register");
    }



    public JButton makeButton(String name){
        JButton button1 = new JButton(name);
        button1.setSize(new Dimension(310,89));
        button1.setFont(new Font("楷体",Font.BOLD,25));
        button1.setForeground(Color.BLACK);
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button1;
    }

    public JLabel makeLabel(String name,int size){
        JLabel label1 = new JLabel(name);
        label1.setSize(new Dimension(180,63));
        label1.setFont(new Font("楷体",Font.BOLD,size));
        label1.setForeground(Color.RED);
        return label1;
    }

    public JLabel makeTitle(String name){
        JLabel title = new JLabel(name);
        title.setFont(new Font("Times New Roman",Font.BOLD,40));
        title.setForeground(Color.BLACK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        return title;
    }

    public JPanel makeUserPanel(String Label,String name){

        JPanel loginPanel = new ImagePanel("D:\\JavaProject\\forest.jpg");
        loginPanel.setLayout(new BoxLayout(loginPanel,BoxLayout.Y_AXIS));
        JLabel title = makeTitle(name);
        JPanel userPanel = new JPanel(new FlowLayout());
        userPanel.setOpaque(false);
        JPanel passPanel = new JPanel(new FlowLayout());
        passPanel.setOpaque(false);
        JButton returnButton = makeButton("返回");
        JButton firmButton = makeButton("确认");
        returnButton.addActionListener(e -> {cardLayout.show(this.getContentPane(),"first");});


        loginPanel.add(title);
        loginPanel.add(Box.createRigidArea(new Dimension(20,40)));
        loginPanel.add(userPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(10,10)));
        loginPanel.add(passPanel);
        loginPanel.add(firmButton);
        loginPanel.add(Box.createRigidArea(new Dimension(10,40)));
        loginPanel.add(returnButton);



        JLabel username = makeLabel("用户名",20);
        JLabel password = makeLabel("密码",20);
        JTextField textField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        firmButton.addActionListener(e -> {
            if(Label.equals("register")){
                UserInfo.setUsername(textField.getText());
                UserInfo.setPassword(passwordField.getPassword());
                cardLayout.show(this.getContentPane(),"first");
            }else{
                char[] input = passwordField.getPassword();
                if(textField.getText().equals(UserInfo.getUsername())&&
                      UserInfo.isPasswordCorrect(input) ){
                    cardLayout.show(this.getContentPane(),"main");
                    System.out.println("Yes!");
                }else {
                    System.out.println("something wrong in your input?");
//                    for (char a:
//                         UserInfo.getPassword()) {
//                        System.out.print(a+"");
//                    }
                }
            }
        });


        userPanel.add(username);
        userPanel.add(textField);
        passPanel.add(password);
        passPanel.add(passwordField);

        return loginPanel;
    }

    public ChessGamePanel getMainPanel() {
        return mainPanel;
    }
}