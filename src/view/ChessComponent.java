package view;

import model.PlayerColor;

import javax.swing.*;

public class ChessComponent extends JComponent {
    private PlayerColor owner;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

//    public ChessComponent(PlayerColor owner,int size){
//        this.selected=false;
//        this.owner=owner;
//        setSize(size/2, size/2);
//        setLocation(0,0);
//        setVisible(true);
//    }
}
