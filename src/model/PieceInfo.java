package model;

import java.io.Serializable;

public class PieceInfo implements Serializable {
    int rank;
    String name;
    String player;

    private static final long serialVersionUID = 2;

    public PieceInfo(int rank, String name, String player){
        this.rank = rank;
        this.name = name;
        this.player = player;
    }

    public int getRank() {
        return rank;
    }

    public String getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }
}
