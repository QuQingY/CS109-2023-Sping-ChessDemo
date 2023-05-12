package controller;


import listener.GameListener;
import model.*;
import view.Animal.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;

import java.io.*;

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
    private PlayerColor currentPlayer;
    private PlayerColor winner;

    private int roundCounter = 1;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public int getRoundCounter(){
        return this.roundCounter;
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    public Chessboard getModel() {
        return model;
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }



    private void solveWin(){
        if (model.win(currentPlayer)){
            winner = currentPlayer;
            System.out.println("Winner is " + winner);
            view.showWinningInterface(winner.toString());
        }
    }

    private void denWin(){
        winner = currentPlayer;
        System.out.println("Winner is " + winner);
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
            selectedPoint = null;
            view.repaint();
            if (model.enterDen(point)){
                denWin();
            }
            swapColor();
            if (currentPlayer == PlayerColor.BLUE){
                roundCounter++;
            }
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
            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            view.repaint();
            solveWin();
            swapColor();
        }
        if (currentPlayer == PlayerColor.BLUE){
            roundCounter ++;
        }

    }

    public void restart(){
        model.initPieces();
        view.initiateChessComponent(model);
        selectedPoint = null;
        winner = null;
        view.repaint();
        currentPlayer = PlayerColor.RED;
    }




    public void save() {
        PieceInfo[][] pieceInfo = new PieceInfo[9][7];
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 7; j++){
                pieceInfo[i][j] = new PieceInfo(model.rankStorage()[i][j],
                        model.playerAndNameStorage()[1][i][j],
                        model.playerAndNameStorage()[0][i][j]);
            }
        }

       File f = new File("./save.sav");
        try(ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {
            os.writeObject(pieceInfo);
        }catch (IOException e){
            e.printStackTrace();
        }
   }

   public void load(String path){
        File f = new File(path);
        PieceInfo[][] pieceInfoFromTxt = new PieceInfo[9][7];
        try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)))){
            pieceInfoFromTxt = (PieceInfo[][]) is.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++){
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++){
                ChessboardPoint point = new ChessboardPoint(i,j);
                if (model.getGrid()[i][j].getPiece() != null){
                    model.getGrid()[i][j].removePiece();
                    view.removeChessComponentAtGrid(point);
                }
                if (pieceInfoFromTxt[i][j].getRank() > 0){
                    if (pieceInfoFromTxt[i][j].getPlayer().equals("Blue")){
                        ChessPiece piece = new ChessPiece(PlayerColor.BLUE
                                , pieceInfoFromTxt[i][j].getName()
                                ,pieceInfoFromTxt[i][j].getRank());
                        model.getGrid()[i][j].setPiece(piece);
                    }

                    if (pieceInfoFromTxt[i][j].getPlayer().equals("Red")){
                        ChessPiece piece = new ChessPiece(PlayerColor.RED
                                , pieceInfoFromTxt[i][j].getName()
                                ,pieceInfoFromTxt[i][j].getRank());
                        model.getGrid()[i][j].setPiece(piece);
                    }
                    switch(model.getGrid()[i][j].getPiece().getRank()){
                        case 8:  view.getGridComponents()[i][j].add(new ElephantChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()
                        ));break;
                        case 7 : view.getGridComponents()[i][j].add(new LionChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case 6 : view.getGridComponents()[i][j].add(new TigerChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case 5 : view.getGridComponents()[i][j].add(new LeopardChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case 4 : view.getGridComponents()[i][j].add(new WolfChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case 3 : view.getGridComponents()[i][j].add(new DogChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case 2 : view.getGridComponents()[i][j].add(new CatChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                        case 1 : view.getGridComponents()[i][j].add(new MouseChessComponent(
                                model.getChessPieceOwner(point),view.getCHESS_SIZE()));break;
                    }
                }
            }
        }
        view.repaint();
   }
}
