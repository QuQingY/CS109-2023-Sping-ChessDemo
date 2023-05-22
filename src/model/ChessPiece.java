package model;


public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        // TODO: Finish this method!
        if (this.getRank()!= 8 & this.getRank() >= target.getRank()){
            return true;
        }
        else if (this.getRank() == 8 && target.getRank() != 1){
            return true;
        }
        else if (this.getRank() == 1 && target.getRank() == 8){
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
//This is my update.
// Can I use Github Desktop?
//Can I use GitHub successfully?
//well i can use idea to do this
