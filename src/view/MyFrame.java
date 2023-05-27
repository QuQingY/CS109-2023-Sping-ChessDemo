package view;

import model.PieceInfo;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.UUID;

public class MyFrame extends JFrame {
//    private final int WIDTH;
//    private final int HEIGHT;
    private CardLayout cardLayout;

    private UserInfo currentUser;
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
        firstPanel.add(addRankingList());

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
        UserInfo userInfo = new UserInfo();

        firmButton.addActionListener(e -> {
            if(Label.equals("register")){
                userInfo.setUsername(textField.getText());
                userInfo.setPassword(passwordField.getPassword());
                cardLayout.show(this.getContentPane(),"first");

                File file = new File("./users.sav/");
                UserInfo[] users = new UserInfo[100];
                if (!file.exists()){

                    users[0] = userInfo;
                    try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                        os.writeObject(users);
                    }catch (IOException g){
                        g.printStackTrace();
                    }
                }else {
                    try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
                        users = (UserInfo[]) is.readObject();
                    }catch (IOException | ClassNotFoundException g){
                        g.printStackTrace();
                    }
                    for (int i = 0; i < users.length; i++){
                        if (users[i] != null && users[i].getUsername().equals(userInfo.getUsername())){
                            JOptionPane.showMessageDialog(this, "User already registered.");
                            break;
                        }
                        if (users[i] == null){
                            users[i] = userInfo;
                            break;
                        }
                    }
                    try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                        os.writeObject(users);
                    }catch (IOException g){
                        g.printStackTrace();
                    }
                }

            }else if(Label.equals("login")){
                char[] input = passwordField.getPassword();
                File file = new File("./users.sav/");
                UserInfo[] users = new UserInfo[100];
                try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
                    users = (UserInfo[]) is.readObject();
                }catch (IOException | ClassNotFoundException g){
                    g.printStackTrace();
                }
                for (int i = 0; i < users.length; i++){
                    if (textField.getText().equals(users[i].getUsername())){
                        if (users[i].isPasswordCorrect(input)){
                            currentUser = users[i];
                            System.out.println(currentUser.getUsername());
                            cardLayout.show(this.getContentPane(),"main");
                            System.out.println("Yes!");
                            storeCurrentUser();
                            break;
                        }
                        else {
                            System.out.println("something wrong in your input?");
                            break;
                        }
                    }
                }
/*                if(textField.getText().equals(userInfo.getUsername())&&
                      userInfo.isPasswordCorrect(input) ){
                    cardLayout.show(this.getContentPane(),"main");
                    System.out.println("Yes!");
                }else {
                   System.out.println("something wrong in your input?");
//                    for (char a:
//                         UserInfo.getPassword()) {
//                        System.out.print(a+"");
//                    }
                }*/
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

    public JTable addRankingList(){
        File file = new File("./users.sav/");
        UserInfo[] users = new UserInfo[100];
        try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
            users = (UserInfo[]) is.readObject();
        }catch (IOException | ClassNotFoundException g){
            g.printStackTrace();
        }
        for (int i = 0; i < users.length -1 ; i++){
            for (int j = 0; j < users.length -i -1 ; j++){
                if (users[j] != null && users[j+1] != null){
                    if (users[j].getScore() < users[j+1].getScore()){
                        UserInfo temp = users[j];
                        users[j] = users[j+1];
                        users[j+1] = temp;
                    }
                }
                else {
                    break;
                }
            }
        }


        String[] colNames = {"Player","Score"};
        String[][] topUsers = new String[10][2];
        for (int i = 0; i < 10 ; i++){
            if (users[i] != null){
                topUsers[i][0] = users[i].getUsername();
                topUsers[i][1] =  String.valueOf(users[i].getScore());
            }
            else {
                break;
            }
        }
        JTable Rank = new JTable(topUsers,colNames);
        Rank.setSize(20,400);
        return Rank;
    }

    public void storeCurrentUser(){
        File file = new File("./currentUser.sav/");
        UserInfo[] userInfos = new UserInfo[1];
        if(file.exists()){
            try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
                userInfos = (UserInfo[]) is.readObject();
            }catch (IOException | ClassNotFoundException g){
                g.printStackTrace();
            }
            userInfos[0] = currentUser;
            System.out.println(userInfos[0].getUsername());
            try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                os.writeObject(userInfos);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else {
            userInfos[0] = currentUser;
            System.out.println(userInfos[0].getUsername());
            try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                os.writeObject(userInfos);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
