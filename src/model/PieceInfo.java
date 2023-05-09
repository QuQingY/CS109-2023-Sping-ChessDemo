package model;

import java.io.Serializable;

public class PieceInfo implements Serializable {
    int rank;
    String name;
    String player;

    public PieceInfo(int rank, String name, String player){
        this.rank = rank;
        this.name = name;
        this.player = player;
    }
}
