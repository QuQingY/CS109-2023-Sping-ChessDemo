package model;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
                grid[i][j].setType(Cell.Type.Land);
            }
        }
        grid[0][2].setType(Cell.Type.redTrap);
        grid[0][4].setType(Cell.Type.redTrap);
        grid[1][3].setType(Cell.Type.redTrap);

        grid[8][2].setType(Cell.Type.blueTrap);
        grid[8][4].setType(Cell.Type.blueTrap);
        grid[7][3].setType(Cell.Type.blueTrap);

        grid[0][3].setType(Cell.Type.redDen);

        grid[8][3].setType(Cell.Type.blueDen);

        grid[3][1].setType(Cell.Type.River);
        grid[3][2].setType(Cell.Type.River);
        grid[3][4].setType(Cell.Type.River);
        grid[3][5].setType(Cell.Type.River);

        grid[4][1].setType(Cell.Type.River);
        grid[4][2].setType(Cell.Type.River);
        grid[4][4].setType(Cell.Type.River);
        grid[4][5].setType(Cell.Type.River);

        grid[5][1].setType(Cell.Type.River);
        grid[5][2].setType(Cell.Type.River);
        grid[5][4].setType(Cell.Type.River);
        grid[5][5].setType(Cell.Type.River);

    }

    private void initPieces() {
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Mouse",1));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",4));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",5));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Mouse",1));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",4));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",5));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        setChessPiece(dest, removeChessPiece(src));
        // TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        if (getChessPieceAt(src).getRank() != 1 && getGridAt(dest).getType() == Cell.Type.River  ){
            return false;
        }
        if(getGridAt(dest).getType() == Cell.Type.blueDen && getChessPieceOwner(src) == PlayerColor.BLUE){
            return  false;
        }
        if (getGridAt(dest).getType() == Cell.Type.redDen && getChessPieceOwner(src) == PlayerColor.RED){
            return false;
        }
        if (calculateDistance(src,dest) > 1 &&
                getChessPieceAt(src).getRank() == 7 || getChessPieceAt(src).getRank() == 6) {
            if (src.getRow() != dest.getRow() && src.getCol() != dest.getCol()) {
                return false;
            }//判断是否在同一行/列

            if (src.getRow() == dest .getRow()){
                int row = src .getRow();
                int direction = src.getCol() < dest.getCol() ? 1 : -1;
                int col = src.getCol() + direction;

                while ( col != dest.getCol() ){
                    if (grid[row][col].getType() != Cell.Type.River
                            | grid[row][col].getPiece() != null){
                        return false;
                    }
                    col += direction;
                }
            }

            if (src.getCol() == dest .getCol()){
                int col = src.getCol();
                int direction = src.getRow() < dest.getRow()? 1 : -1;
                int row = src .getRow() + direction;

                while ( row != dest.getRow() ){
                    if (grid[row][col].getType() != Cell.Type.River
                            | grid[row][col].getPiece() != null){
                        return false;
                    }
                    row += direction;
                }
            }

            return true;
        }
        return calculateDistance(src, dest) == 1;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) == null){
            return  false;
        }
        if (getChessPieceOwner(src) == getChessPieceOwner(dest)){
            return false;
        }
        if (grid[dest.getRow()][dest.getCol()].getType() == Cell.Type.River){
            return false;
        }
        if (grid[src.getRow()][src.getCol()].getType() == Cell.Type.River){
            return false;
        }

        if (calculateDistance(src,dest) > 1 &&
                getChessPieceAt(src).getRank() == 7 || getChessPieceAt(src).getRank() == 6){

            if (src.getRow() != dest.getRow() && src.getCol() != dest.getCol()) {
                return false;
            }//判断是否在同一行/列

            if (src.getRow() == dest .getRow()){
                int row = src .getRow();
                int direction = src.getCol() < dest.getCol() ? 1 : -1;
                int col = src.getCol() + direction;

                while ( col != dest.getCol() ){
                    if (grid[row][col].getType() != Cell.Type.River
                            | grid[row][col].getPiece() != null){
                        return false;
                    }
                    col += direction;
                }
            }

            if (src.getCol() == dest .getCol()){
                int col = src.getCol();
                int direction = src.getRow() < dest.getRow()? 1 : -1;
                int row = src .getRow() + direction;

                while ( row != dest.getRow() ){
                    if (grid[row][col].getType() != Cell.Type.River
                            | grid[row][col].getPiece() != null){
                        return false;
                    }
                    row += direction;
                }
            }

            return getChessPieceAt(src).canCapture(getChessPieceAt(dest));
        }

        return calculateDistance(src,dest)==1 && getChessPieceAt(src).canCapture(getChessPieceAt(dest));
    }

    public void enterTrap(ChessboardPoint selectedPoint, ChessboardPoint dest){
        if (getGridAt(dest).getType() == Cell.Type.blueTrap
                && getChessPieceOwner(selectedPoint) == PlayerColor.RED){
            getChessPieceAt(selectedPoint).setRank(0);
        } else if (getGridAt(dest).getType() == Cell.Type.redTrap
                && getChessPieceOwner(selectedPoint) == PlayerColor.BLUE){
            getChessPieceAt(selectedPoint).setRank(0);
        }
    }

    public void escapeTrap(ChessboardPoint selectedPoint, ChessboardPoint dest){
        if (getGridAt(selectedPoint).getType() == Cell.Type.blueTrap
        | getGridAt(selectedPoint).getType() == Cell.Type.redTrap){
            switch (getChessPieceAt(selectedPoint).getName()){
                case "Mouse":
                    getChessPieceAt(selectedPoint).setRank(1);
                case "Cat":
                    getChessPieceAt(selectedPoint).setRank(2);
                case "Dog":
                    getChessPieceAt(selectedPoint).setRank(3);
                case "Leopard":
                    getChessPieceAt(selectedPoint).setRank(4);
                case "Wolf":
                    getChessPieceAt(selectedPoint).setRank(5);
                case "Tiger":
                    getChessPieceAt(selectedPoint).setRank(6);
                case "Lion":
                    getChessPieceAt(selectedPoint).setRank(7);
                case "Elephant":
                    getChessPieceAt(selectedPoint).setRank(8);
            }
        }
    }

    public boolean enterDen(ChessboardPoint point){
        if (getGridAt(point).getType() == Cell.Type.blueDen && getChessPieceOwner(point) == PlayerColor.RED){
            return true;
        }

        if (getGridAt(point).getType() == Cell.Type.redDen && getChessPieceOwner(point) == PlayerColor.BLUE){
            return true;
        }

        return false;
    }

}
