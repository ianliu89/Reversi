package Reversi;

/**
 * Created by yicliu on 3/21/17.
 */
public class Chess
{
    private boolean blackMove = false;
    private boolean whiteMove = false;

    public boolean getBlackMove()
    {
        return blackMove;
    }

    public void setBlackMove(boolean isAvailable)
    {
        blackMove = isAvailable;
    }

    public boolean getWhiteMove()
    {
        return whiteMove;
    }

    public void setWhiteMove(boolean isAvailable)
    {
        whiteMove = isAvailable;
    }
}
