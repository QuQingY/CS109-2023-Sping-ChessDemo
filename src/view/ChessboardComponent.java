package view;


import controller.*;
import model.*;
import view.Animal.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();

    private final Set<ChessboardPoint> trapCell = new HashSet<>();

    private final Set<ChessboardPoint> denCell = new HashSet<>();

    private GameController gameController;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        setTerrain();
        initiateGridComponents();
    }

     public int getChessSize(){
        return CHESS_SIZE;
     }

    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                gridComponents[i][j].removeAll();
            }
        }
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard

//                if (grid[i][j].getPiece().getRank() ==8 ) {
//                    ChessPiece chessPiece = grid[i][j].getPiece();
//                    System.out.println(chessPiece.getOwner());
//                    gridComponents[i][j].add(
//                            new ElephantChessComponent(
//                                    chessPiece.getOwner(),
//                                    CHESS_SIZE));

//               }

                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    switch (grid[i][j].getPiece().getRank()) {
                        case 8:
                            gridComponents[i][j].add(
                                    new ElephantChessComponent(
                                            chessPiece.getOwner(),
                                            CHESS_SIZE));
                            break;
                        case 7:
                            gridComponents[i][j].add(
                                    new LionChessComponent(
                                            chessPiece.getOwner(),
                                            CHESS_SIZE));
                            break;
                        case 6:
                            gridComponents[i][j].add(
                                    new TigerChessComponent(
                                            chessPiece.getOwner(),
                                            CHESS_SIZE));
                            break;
                        case 5:
                            gridComponents[i][j].add(
                                    new LeopardChessComponent(
                                            chessPiece.getOwner(),
                                            CHESS_SIZE));
                            break;
                        case 4:
                            gridComponents[i][j].add(
                                    new WolfChessComponent(
                                            chessPiece.getOwner(),
                                            CHESS_SIZE));
                            break;
                        case 3:
                            gridComponents[i][j].add(
                                    new DogChessComponent(
                                            chessPiece.getOwner(),
                                            CHESS_SIZE));
                            break;
                        case 2:
                            gridComponents[i][j].add(
                                    new CatChessComponent(
                                            chessPiece.getOwner(),
                                            CHESS_SIZE));
                            break;
                        case 1:
                            gridComponents[i][j].add(
                                    new MouseChessComponent(
                                            chessPiece.getOwner(),
                                            CHESS_SIZE));
                            break;
                    }
                }


            }
        }
    }

    public void setTerrain() {
        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));

        trapCell.add(new ChessboardPoint(0, 2));
        trapCell.add(new ChessboardPoint(0, 4));
        trapCell.add(new ChessboardPoint(1, 3));

        trapCell.add(new ChessboardPoint(7, 3));
        trapCell.add(new ChessboardPoint(8, 2));
        trapCell.add(new ChessboardPoint(8, 4));

        denCell.add(new ChessboardPoint(0, 3));

        denCell.add(new ChessboardPoint(8, 3));
    }

    public void initiateGridComponents() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE, "D:\\JavaProject\\河流.png");
                    this.add(cell);
                } else if (trapCell.contains(temp)) {
                    cell = new CellComponent(Color.orange, calculatePoint(i, j), CHESS_SIZE, "D:\\JavaProject\\荆棘.jpg");
                    this.add(cell);
                } else if (denCell.contains(temp)) {
                    cell = new CellComponent(Color.GREEN, calculatePoint(i, j), CHESS_SIZE, "D:\\JavaProject\\无标题.png");
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE, "D:\\JavaProject\\草地.png");
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        this.showMove(point, chess, this);
        return chess;
    }

    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }

    public void showWinningInterface(String winner) {
        /*int result = JOptionPane.showConfirmDialog(this, "Do you want to restart the game?", winner + " win! Game Over", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            gameController.restart();
        }
        if (result == JOptionPane.NO_OPTION) {
            System.exit(0);
        }*/
        JOptionPane.showMessageDialog(this ,"Game Over! "+ winner +" Win!");
    }

    public GameController getGameController() {
        return gameController;
    }

    public CellComponent[][] getGridComponents() {
        return gridComponents;
    }

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }

    public void showMove(ChessboardPoint point, ChessComponent component, ChessboardComponent view) {
        List<ChessboardPoint> list = new ArrayList<>();
        Chessboard temp = this.gameController.getModel();
        if (component.isSelected()) {
            System.out.println("nn");
            for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                    ChessboardPoint chessboardPoint = new ChessboardPoint(i, j);
                    if (temp.isValidMove(point, chessboardPoint)) {
                        view.getGridComponentAt(chessboardPoint).setInfluenced(true);
                        view.getGridComponentAt(chessboardPoint).repaint();
                    }
                    if (temp.isValidCapture(point, chessboardPoint)) {
                        if (view.getGridComponentAt(chessboardPoint).getComponents().length != 0) {
                            ChessComponent chess = (ChessComponent) view.getGridComponentAt(chessboardPoint).getComponents()[0];
                            chess.setInfluenced(true);
                            chess.repaint();
                        }
                    }
                }
            }
        } else if (component.isSelected() == false) {
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
            }

        }
    }
}

