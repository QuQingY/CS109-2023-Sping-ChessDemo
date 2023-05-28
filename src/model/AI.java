package model;

import java.util.Random;

public class AI {
    private ChessboardPoint src;
    private ChessboardPoint dest;

    private Chessboard model;

    public AI (Chessboard model){
        this.model = model;
    }

    public void selectSrc(Chessboard model){

        while(src == null){
            Random random = new Random();
            int rank = random.nextInt(8);
            for (int i = 0; i < 9; i++){
                for(int j = 0; j < 7; j++){
                    if (model.getGrid()[i][j].getPiece()!= null
                            &&model.getGrid()[i][j].getPiece().getRank() == rank
                    &&model.getGrid()[i][j].getPiece().getOwner() == PlayerColor.RED){
                        ChessboardPoint chessboardPoint =new ChessboardPoint(i,j);
                        src = chessboardPoint;
                    }
                }
            }
        }
    }

    public void selectDest(Chessboard model){

        int[] dirX = {1,0,-1,0};
        int[] dirY = {0,1,0,-1};
        while (dest == null){
            Random random = new Random();
            int dir = random.nextInt(4);

            ChessboardPoint temp = new ChessboardPoint(src.getRow()+dirX[dir],src.getCol()+dirY[dir]);

            if (model.isValidMove(src, temp)|| model.isValidCapture(src,temp)) {
                dest = temp;
            }
        }

    }

    public void clearSelection(){
        this.dest = null;
        this.src = null;
    }

    public ChessboardPoint getDest() {
        return dest;
    }

    public ChessboardPoint getSrc() {
        return src;
    }
}
