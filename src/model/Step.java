package model;

import view.ChessComponent;

public class Step {
    private ChessboardPoint selected_point;
    private ChessboardPoint point;

    private PlayerColor owner;

    private boolean isCapture;

    private ChessPiece piece;
    private ChessComponent component;

    public Step(ChessboardPoint selected_point, ChessboardPoint point,PlayerColor owner){
        this.point=point;
        this.selected_point=selected_point;
        this.owner=owner;
    }

    public Step(ChessboardPoint selected_point, ChessboardPoint point,PlayerColor owner,ChessComponent component,ChessPiece piece, boolean isCapture){
        this.point=point;
        this.selected_point=selected_point;
        this.owner=owner;
        this.piece=piece;
        this.component=component;
        this.isCapture=isCapture;
    }

    public ChessboardPoint getPoint() {
        return point;
    }

    public ChessboardPoint getSelected_point() {
        return selected_point;
    }

    public void setSelected_point(ChessboardPoint selected_point) {
        this.selected_point = selected_point;
    }

    public void setPoint(ChessboardPoint point) {
        this.point = point;
    }

    public void setCapture(boolean capture) {
        isCapture = capture;
    }

    public boolean isCapture() {
        return isCapture;
    }

    public ChessComponent getComponent() {
        return component;
    }

    public ChessPiece getPiece() {
        return piece;
    }
}
