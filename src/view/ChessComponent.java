package view;

import model.ChessboardPoint;
import model.PlayerColor;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChessComponent extends JComponent {
    private PlayerColor owner;
    private boolean influenced;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isInfluenced() {
        return influenced;
    }

    public void setInfluenced(boolean influenced) {
        this.influenced = influenced;
    }

     @Override
     public void paintComponent(Graphics g){
        super.paintComponent(g);
         if(isInfluenced()){
             g.setColor(Color.GREEN);
             g.drawOval(0, 0, getWidth() , getHeight());
         }

     }
}
