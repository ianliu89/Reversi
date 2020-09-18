package Reversi;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChessButton extends JButton
{
    private Location location;

    public ChessButton(String chessColor, int x, int y, GameBoard gb)
    {
        super(chessColor);
        this.location = new Location(x,y);
        this.addActionListener(e -> handleSquareButtonClick(e, gb));
    }

    public Location getChessLocation() {
        return location;
    }

    public void setChessLocation(Location location) {
        this.location = location;
    }

    private void handleSquareButtonClick(ActionEvent e, GameBoard gb)
    {
        gb.play(location);
    }
}
