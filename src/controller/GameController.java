package controller;


import listener.GameListener;
import model.*;
import view.*;
import view.Animal.*;

import Stream.Audio;

import view.ChessGamePanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private ChessGamePanel panel;
    private PlayerColor currentPlayer;
    private PlayerColor winner;

    private UserInfo currentUser;

    private List<Step> steps;

    private int roundCounter = 1;

    private int stepCounter = 1;

    public static int red=30;
    public static int blue=30;
    public static long countTime;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public int getRoundCounter(){
        return this.roundCounter;
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public GameController(ChessboardComponent view, Chessboard model,ChessGamePanel panel) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.panel = panel;

        this.steps=new ArrayList<>();
        steps.add(null);


        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
        timing();
    }

    public Chessboard getModel() {
        return model;
    }

    public List<Step> getSteps() {
        return steps;
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    public void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    public void timing(){
        countTime=0;
        panel.getRedtime().setText(String.format("红方时间：%d",red));
        panel.getBluetime().setText(String.format("蓝方时间：%d",blue));
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(panel.isVisible()){
                    countTime++;
                    if( (getCurrentPlayer()==PlayerColor.RED&&blue!=30) ||
                            (getCurrentPlayer()==PlayerColor.BLUE&&red!=30)){
                        blue=30;
                        red=30;
                        panel.getRedtime().setText(String.format("红方时间：%d",red));
                        panel.getRedtime().repaint();
                        panel.getBluetime().setText(String.format("蓝方时间：%d",blue));
                        panel.getBluetime().repaint();
                        countTime=0;
                    } else if (countTime>30) {
                        blue=30;
                        red=30;
                        panel.getRedtime().setText(String.format("红方时间：%d",red));
                        panel.getRedtime().repaint();
                        panel.getBluetime().setText(String.format("蓝方时间：%d",blue));
                        panel.getBluetime().repaint();
                        swapColor();
                        panel.switchPlayer();
                        if (currentPlayer == PlayerColor.BLUE){
                            roundCounter ++;
                        }
                        panel.addRounds();
                        countTime=0;
                    } else{
                        if(getCurrentPlayer()==PlayerColor.BLUE){
                            blue--;
                            panel.getBluetime().setText(String.format("蓝方时间：%d",blue));
                            panel.getBluetime().repaint();
                        }else if(getCurrentPlayer()==PlayerColor.RED){
                            red--;
                            panel.getRedtime().setText(String.format("红方时间：%d",red));
                            panel.getRedtime().repaint();
                        }
                    }


                }



            }
        },0,1000);
    }





    private void solveWin(){
        if (model.win(currentPlayer)){
            winner = currentPlayer;
            System.out.println("Winner is " + winner);

            if (winner == PlayerColor.BLUE){
                currentUser = panel.readCurrentUser();
                System.out.println(currentUser.getUsername());
                currentUser.setScore(panel.getCurrentUser().getScore() + 1);
                File file = new File("./users.sav");
                UserInfo[] users = new UserInfo[100];
                try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
                    users = (UserInfo[]) is.readObject();
                }catch (IOException | ClassNotFoundException g){
                    g.printStackTrace();
                }
                for (int i = 0; i < users.length; i++){
                    if (users[i] != null){
                        System.out.println(users[i].getUsername());
                    }
                }
                for (int i = 0; i < users.length; i++){
                    if ( users[i] != null){
                        if (users[i].getUsername().equals(currentUser.getUsername())){
                            System.out.println(users[i].getUsername());
                            users[i].setScore(currentUser.getScore());
                            System.out.println(users[i].getScore());
                            break;}
                    }
                }
                try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                    os.writeObject(users);
                }catch (IOException g){
                    g.printStackTrace();
                }
            }

            view.showWinningInterface(winner.toString());
        }
    }

    private void denWin(){
        winner = currentPlayer;
        System.out.println("Winner is " + winner);

        if (winner == PlayerColor.BLUE){
            currentUser = panel.readCurrentUser();
            System.out.println(currentUser.getUsername());
            currentUser.setScore(panel.getCurrentUser().getScore() + 1);
            File file = new File("./users.sav");
            UserInfo[] users = new UserInfo[100];
            try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
                users = (UserInfo[]) is.readObject();
            }catch (IOException | ClassNotFoundException g){
                g.printStackTrace();
            }
            for (int i = 0; i < users.length; i++){
                if (users[i] != null){
                    System.out.println(users[i].getUsername());
                }
            }
            for (int i = 0; i < users.length; i++){
                if ( users[i] != null){
                    if (users[i].getUsername().equals(currentUser.getUsername())){
                    System.out.println(users[i].getUsername());
                    users[i].setScore(currentUser.getScore());
                    System.out.println(users[i].getScore());
                    break;}
                }
            }
            try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                os.writeObject(users);
            }catch (IOException g){
                g.printStackTrace();
            }
        }

        view.showWinningInterface(winner.toString());
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            //进入或离开陷阱
            model.Trap(selectedPoint,point);
            //移动
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            recordStep(selectedPoint,point);
            selectedPoint = null;
            view.repaint();
            if (model.enterDen(point)){
                denWin();
            }

            swapColor();
            if (currentPlayer == PlayerColor.BLUE){
                roundCounter ++;
            }
            stepCounter ++;
            Audio.playVoice("D:\\JavaProject\\place.wav");
            panel.switchPlayer();
            panel.addRounds();
            // TODO: if the chess enter Dens or Traps and so on



        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
                view.showMove(point,component,view);

            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            view.showMove(point,component,view);
        }
        // TODO: Implement capture function
        else {
            if(model.isValidCapture(selectedPoint,point)){
                recordCaptureStep(selectedPoint,point,(ChessComponent) view.getGridComponentAt(point).getComponents()[0],model.getChessPieceAt(point));
                stepCounter++;
            }

            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            view.repaint();
            solveWin();

            swapColor();
            if (currentPlayer == PlayerColor.BLUE){
                roundCounter ++;
            }
        }


        panel.switchPlayer();
        panel.addRounds();

  }

    public void restart(){
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint chessboardPoint = new ChessboardPoint(i, j);
                view.getGridComponentAt(chessboardPoint).setInfluenced(false);
                if (view.getGridComponentAt(chessboardPoint).getComponents().length != 0) {
                    ChessComponent chess = (ChessComponent) view.getGridComponentAt(chessboardPoint).getComponents()[0];
                    chess.setInfluenced(false);
                }
                view.getGridComponentAt(chessboardPoint).repaint();
            }
        }//将上一局showmove的痕迹擦掉

        model.initPieces();
        view.initiateChessComponent(model);
        view.repaint();
        selectedPoint = null;
        winner = null;

        view.repaint();
        currentPlayer = PlayerColor.BLUE;
        roundCounter = 1;
        panel.switchPlayer();
        panel.addRounds();


    }




    public void save() {
        PieceInfo[][][] pieceInfo = new PieceInfo[3][][];
        pieceInfo[0] = new PieceInfo[1][1];
        pieceInfo[1] = new PieceInfo[1][1];
        pieceInfo[2] = new PieceInfo[9][7];
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 7; j++){
                pieceInfo[2][i][j] = new PieceInfo(model.rankStorage()[i][j],
                        model.playerAndNameStorage()[1][i][j],
                        model.playerAndNameStorage()[0][i][j]);
            }
        }
        pieceInfo[0][0][0] = new PieceInfo(roundCounter,"0","0");
        if (currentPlayer == PlayerColor.RED){
            pieceInfo[1][0][0] = new PieceInfo(2,"0","0");
        }if (currentPlayer == PlayerColor.BLUE){
            pieceInfo[1][0][0] = new PieceInfo(1,"0","0");
        }

        String Pathname = JOptionPane.showInputDialog(view,"Please enter the new pathname");
       File f = new File(Pathname);
        if(f.exists()){
            int result = JOptionPane.showConfirmDialog(view, "Do you want to replace the file?", "File already exists.", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {
                    os.writeObject(pieceInfo);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (result == JOptionPane.NO_OPTION) {
                String newPathname = JOptionPane.showInputDialog(view,"Please enter the new pathname");
                File file = new File(newPathname);
                try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                    os.writeObject(pieceInfo);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }else {
            try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {
             os.writeObject(pieceInfo);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        JOptionPane.showMessageDialog(panel, "Game saved");
   }

   public void load(String path){
        File f = new File(path);
       PieceInfo[][][] pieceInfoFromTxt = new PieceInfo[3][][];
       pieceInfoFromTxt[0] = new PieceInfo[1][1];
       pieceInfoFromTxt[1] = new PieceInfo[1][1];
       pieceInfoFromTxt[2] = new PieceInfo[9][7];
        try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)))){
            pieceInfoFromTxt = (PieceInfo[][][]) is.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        this.roundCounter = pieceInfoFromTxt[0][0][0].getRank();
        if (pieceInfoFromTxt[1][0][0].getRank() == 1){
            currentPlayer = PlayerColor.BLUE;
        }else {
            currentPlayer = PlayerColor.RED;
        }

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++){
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++){
                ChessboardPoint point = new ChessboardPoint(i,j);
                if (model.getGrid()[i][j].getPiece() != null){
                    model.getGrid()[i][j].removePiece();
                    view.removeChessComponentAtGrid(point);
                }
                if (pieceInfoFromTxt[2][i][j].getName() != "N"){
                    if (pieceInfoFromTxt[2][i][j].getPlayer().equals("Blue")){
                        ChessPiece piece = new ChessPiece(PlayerColor.BLUE
                                , pieceInfoFromTxt[2][i][j].getName()
                                ,pieceInfoFromTxt[2][i][j].getRank());
                        model.getGrid()[i][j].setPiece(piece);
                    }

                    if (pieceInfoFromTxt[2][i][j].getPlayer().equals("Red")){
                        ChessPiece piece = new ChessPiece(PlayerColor.RED
                                , pieceInfoFromTxt[2][i][j].getName()
                                ,pieceInfoFromTxt[2][i][j].getRank());
                        model.getGrid()[i][j].setPiece(piece);
                    }
                    if (model.getGrid()[i][j].getPiece() != null){
                    switch(model.getGrid()[i][j].getPiece().getName()){
                        case "Elephant":  view.getGridComponents()[i][j].add(new ElephantChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case "Lion" : view.getGridComponents()[i][j].add(new LionChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case "Tiger" : view.getGridComponents()[i][j].add(new TigerChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case "Leopard" : view.getGridComponents()[i][j].add(new LeopardChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case "Wolf" : view.getGridComponents()[i][j].add(new WolfChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case "Dog" : view.getGridComponents()[i][j].add(new DogChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case "Cat" : view.getGridComponents()[i][j].add(new CatChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case "Mouse" : view.getGridComponents()[i][j].add(new MouseChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                    }
                    }
                }
            }
        }
        view.repaint();
       panel.switchPlayer();
       panel.addRounds();
       JOptionPane.showMessageDialog(panel,"Game loaded!");
   }

   public void recordStep(ChessboardPoint selectedPoint, ChessboardPoint point){
        if(stepCounter<steps.size()){
            steps.remove(stepCounter);
            steps.add(stepCounter,new Step(selectedPoint,point,currentPlayer));
        }
        if(stepCounter>=steps.size()){
            steps.add(stepCounter,new Step(selectedPoint,point,currentPlayer));
        }
   }

   public void recordCaptureStep(ChessboardPoint selectedPoint, ChessboardPoint point,ChessComponent component, ChessPiece piece){
       if(stepCounter<steps.size()){
           steps.remove(stepCounter);
           steps.add(stepCounter,new Step(selectedPoint,point,currentPlayer,component,piece,true));
       }
       if(stepCounter>=steps.size()){
           steps.add(stepCounter,new Step(selectedPoint,point,currentPlayer,component,piece,true));
       }
   }

   public void undo(){
        if(stepCounter>1){
            Step step = steps.get(stepCounter-1);
            if (steps.get(stepCounter - 1).isCapture()) {
                model.moveChessPiece(steps.get(stepCounter-1).getPoint(), steps.get(stepCounter-1).getSelected_point());
                view.setChessComponentAtGrid(steps.get(stepCounter-1).getSelected_point()
                        , view.removeChessComponentAtGrid(steps.get(stepCounter-1).getPoint()));
                model.setChessPiece(step.getPoint(),step.getPiece());
                view.setChessComponentAtGrid(step.getPoint(),step.getComponent());
                stepCounter--;
                swapColor();
                panel.switchPlayer();
                if (currentPlayer == PlayerColor.BLUE && roundCounter > 1){
                    roundCounter --;
                    panel.addRounds();
                }
            }else{
                model.Trap(step.getPoint(),step.getSelected_point());
                model.moveChessPiece(steps.get(stepCounter-1).getPoint(), steps.get(stepCounter-1).getSelected_point());
                view.setChessComponentAtGrid(steps.get(stepCounter-1).getSelected_point()
                        , view.removeChessComponentAtGrid(steps.get(stepCounter-1).getPoint()));
                stepCounter--;
                swapColor();
                panel.switchPlayer();
                if (currentPlayer == PlayerColor.BLUE && roundCounter > 1){
                    roundCounter --;
                    panel.addRounds();
                }
            }
        }

   }

}
