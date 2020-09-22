package Reversi;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static Reversi.GameConfigs.*;

/**
 * Created by yicliu on 3/18/17.
 */
public class GameBoard {

    private ChessButton[][] chessOnBoard = new ChessButton[BOARD_SIZE][BOARD_SIZE];
    private Chess[][] availableMove = new Chess[BOARD_SIZE][BOARD_SIZE];
    private JButton gameDescriber;
    private JButton blackCount;
    private JButton whiteCount;
    private JFrame frame = new JFrame("Reversi");
    private JPanel panel;
    private boolean isBlackTerm;

    public GameBoard()
    {
        this.initializeChess();
        this.initialAvailableMove();
    }

    private void initializeChess()
    {
        frame.getContentPane();
        panel = new JPanel();
        panel.setLayout(null);

        setColumnName();
        setRowName();
        setupChessOnBoard();

        gameDescriber = new JButton();
        blackCount = new JButton();
        whiteCount = new JButton();

        setUtilButtonInfo(gameDescriber, BLACK_TURN, 580, 240);
        setUtilButtonInfo(blackCount, BLACK_CHESS + " 2", 580, 300);
        setUtilButtonInfo(whiteCount, WHITE_CHESS + " 2", 580, 360);
        setupResetButton(RESET, 580, 180);

        chessOnBoard[3][3].setText(WHITE_CHESS);
        chessOnBoard[4][4].setText(WHITE_CHESS);
        chessOnBoard[3][4].setText(BLACK_CHESS);
        chessOnBoard[4][3].setText(BLACK_CHESS);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(740,540);
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        refreshJframe();
    }

    private void setupChessOnBoard()
    {
        for(int i = 0; i< BOARD_SIZE ; ++i)
        {
            for(int j = 0; j< BOARD_SIZE; ++j)
            {
                chessOnBoard[i][j] = new ChessButton(EMPTY_CHESS, i, j, this);
                chessOnBoard[i][j].setPreferredSize(new Dimension(60, 60));
                chessOnBoard[i][j].setBounds((j+1)*60,(i+1)*60,60,60);
                chessOnBoard[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                panel.add(chessOnBoard[i][j]);
            }
        }
    }

    private void setRowName()
    {
        for(int i = 1; i<= BOARD_SIZE; i++)
        {
            JButton jbutton = setupBoardHeaderButton(Integer.toString(i), 0, i*60);
            panel.add(jbutton);
        }
    }

    private void setColumnName()
    {
        for(int i = 0; i<= BOARD_SIZE; i++)
        {
            JButton jbutton = setupBoardHeaderButton(Character.toString((char) (64 + i) ), i*60, 0);
            if( i == 0) {
                jbutton.setText(EMPTY_CHESS);
            }
            panel.add(jbutton);
        }
    }

    private JButton setupBoardHeaderButton(String text, int xLocation, int yLocation)
    {
        JButton jbutton  = new JButton(text);
        jbutton.setPreferredSize(new Dimension(60, 60));
        jbutton.setBounds(xLocation,yLocation,60,60);
        jbutton.setFocusPainted(false);
        jbutton.setBorderPainted(false);
        jbutton.setFont(new Font("Arial", Font.PLAIN, 12));
        return jbutton;
    }

    private void setUtilButtonInfo(JButton button, String text, int xLocation, int yLocation)
    {
        button.setText(text);
        button.setPreferredSize(new Dimension(200, 60));
        button.setBounds(xLocation, yLocation,200,60);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        panel.add(button);
    }

    private void handleResetButtonClick(ActionEvent e, GameBoard gb)
    {
        frame.remove(panel);
        gb.initializeChess();
        gb.initialAvailableMove();
    }

    private void setupResetButton(String text, int xLocation, int yLocation)
    {
        JButton jbutton  = new JButton(text);
        jbutton.setPreferredSize(new Dimension(200, 60));
        jbutton.setBounds(xLocation, yLocation,200,60);
        jbutton.setFont(new Font("Arial", Font.PLAIN, 12));
        jbutton.addActionListener(e -> handleResetButtonClick(e, this));
        panel.add(jbutton);
    }

    private void initialAvailableMove()
    {
        isBlackTerm = true;

        for(int i = 0; i< BOARD_SIZE ; i++)
        {
            for(int j = 0; j< BOARD_SIZE; j++)
            {
                availableMove[i][j] = new Chess();
            }
        }

        availableMove[2][3].setBlackMove(true);
        availableMove[3][2].setBlackMove(true);
        availableMove[4][5].setBlackMove(true);
        availableMove[5][4].setBlackMove(true);
        availableMove[2][4].setWhiteMove(true);
        availableMove[4][2].setWhiteMove(true);
        availableMove[3][5].setWhiteMove(true);
        availableMove[5][3].setWhiteMove(true);
    }

    public ChessButton[][] getChessOnBoard()
    {
        return chessOnBoard;
    }

    public Chess[][] getAvailableMove()
    {
        return availableMove;
    }

    public boolean checkMoving(int row, int col, String colorOnBoard)
    {
        if(colorOnBoard.equals(WHITE_CHESS))
        {
            return availableMove[row][col].getWhiteMove();
        }
        else
        {
            return availableMove[row][col].getBlackMove();
        }
    }

    public boolean checkAvailableMoves(String chess)
    {
        for(int i = 0; i< BOARD_SIZE; i++)
        {
            for(int j=0; j< BOARD_SIZE; j++)
            {
                if(chess.equals(WHITE_CHESS) && availableMove[i][j].getWhiteMove())
                {
                    return true;
                }
                else if(chess.equals(BLACK_CHESS) && availableMove[i][j].getBlackMove())
                {
                    return true;
                }
            }
        }
        return false;
    }


    protected void setChessOnBoard(int row, int column, String colorOnBoard)
    {
        if( row < 0 || row > BOARD_SIZE || column < 0 || column > BOARD_SIZE )
        {
            System.exit(EXIT_CODE_INVALID_BOUNDARY);
        }
        chessOnBoard[row][column].setText(colorOnBoard);
    }

    public int[] countChess()
    {
        int white = 0;
        int black = 0;
        int[] results = new int[2];

        for(int i=0 ; i< BOARD_SIZE ; i++)
        {
            for(int j=0 ; j< BOARD_SIZE; j++)
            {
                if(chessOnBoard[i][j].getText().equals(WHITE_CHESS) )
                {
                    white++;
                }
                else if(chessOnBoard[i][j].getText().equals(BLACK_CHESS))
                {
                    black++;
                }
            }
        }
        results[WHITE_INDEX] = white;
        results[BLACK_INDEX] = black;
        return results;
    }

    private void refreshJframe()
    {
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void play(Location location)
    {
        if(isBlackTerm)
        {
            playChess(BLACK_CHESS, location);
        }
        else
        {
            playChess(WHITE_CHESS, location);
        }
    }

    private void executeChess(int row, int col, String chess)
    {
        if(checkMoving(row, col, chess))
        {
            executeMove(row, col, chess);
        }
    }

    private void updateGameInfo()
    {
        int[] results = countChess();
        whiteCount.setText(WHITE_CHESS + " " + Integer.toString(results[WHITE_INDEX]));
        blackCount.setText(BLACK_CHESS + " " + Integer.toString(results[BLACK_INDEX]));
        if(isBlackTerm)
        {
            gameDescriber.setText(BLACK_TURN);
        }
        else
        {
            gameDescriber.setText(WHITE_TURN);
        }
    }

    private void executeMove(int row, int col, String colorOnBoard)
    {
        setChessOnBoard(row , col, colorOnBoard);
        reverseColor(row , col , colorOnBoard);
        for(int i = 0; i < BOARD_SIZE ; i++)
        {
            for(int j =0; j < BOARD_SIZE ; j++)
            {
                updateBlackAvailable(i,j);
                updateWhiteAvailable(i,j);
            }
        }

        //Check if the game is finished
        if(!checkAvailableMoves(BLACK_CHESS) && !checkAvailableMoves(WHITE_CHESS))
        {
           updateGameResult();
           return;
        }

        if(isBlackTerm)
        {
            if(checkAvailableMoves(WHITE_CHESS))
            {
                isBlackTerm = !isBlackTerm;
            }
        }
        else
        {
            if(checkAvailableMoves(BLACK_CHESS))
            {
                isBlackTerm = !isBlackTerm;
            }
        }

        updateGameInfo();
        refreshJframe();
    }

    private void playChess(String chess, Location location)
    {
        if(!checkAvailableMoves(chess))
        {
            isBlackTerm = chess.equals(WHITE_CHESS);
        }
        else
        {
            executeChess(location.getRow(), location.getCol(), chess);
        }
    }

    private void updateGameResult()
    {
        int[] results = countChess();
        whiteCount.setText(WHITE_CHESS + " " + results[WHITE_INDEX]);
        blackCount.setText(BLACK_CHESS + " " + results[BLACK_INDEX]);
        if(results[WHITE_INDEX] > results[BLACK_INDEX])
        {
            gameDescriber.setText(WHITE_WON);
        }
        else if(results[WHITE_INDEX] < results[BLACK_INDEX])
        {
            gameDescriber.setText(BLACK_WON);
        }
        else
        {
            gameDescriber.setText(DRAW);
        }
        refreshJframe();
    }

    protected void reverseColor(int row, int col, String colorOnBoard)
    {
        int tempRow = row;
        int tempCol = col;
        boolean isDiffColor = true;
        boolean isNotEmpty = true;
        List<Location> changeList = new ArrayList<>();
        List<Location> tempChange = new ArrayList<>();

        //check N
        while(tempRow-1 >= 0 && isDiffColor && isNotEmpty)
        {
            if(chessOnBoard[tempRow-1][tempCol].getText().equals(colorOnBoard))
            {
                isDiffColor = false;
            }
            else if(chessOnBoard[tempRow-1][tempCol].getText().equals(EMPTY_CHESS))
            {
                isNotEmpty = false;
            }
            else
            {
                Location local = new Location(tempRow-1,tempCol);
                tempChange.add(local);
                tempRow--;
            }
        }

        if(!isDiffColor && isNotEmpty)
        {
            changeList.addAll(tempChange);
        }

        tempChange.clear();

        //check NE
        tempRow = row;
        tempCol = col;
        isDiffColor = true;
        isNotEmpty = true;
        while(tempRow-1 >= 0 && tempCol+1 < BOARD_SIZE && isDiffColor && isNotEmpty)
        {
            if(chessOnBoard[tempRow-1][tempCol+1].getText().equals(colorOnBoard))
            {
                isDiffColor = false;
            }
            else if(chessOnBoard[tempRow-1][tempCol+1].getText().equals(EMPTY_CHESS))
            {
                isNotEmpty = false;
            }
            else
            {
                Location local = new Location(tempRow-1,tempCol+1);
                tempChange.add(local);
                tempRow--;
                tempCol++;
            }
        }

        if(!isDiffColor && isNotEmpty)
        {
            changeList.addAll(tempChange);
        }

        tempChange.clear();

        //check E
        tempRow = row;
        tempCol = col;
        isDiffColor = true;
        isNotEmpty = true;
        while(tempCol+1 < BOARD_SIZE && isDiffColor && isNotEmpty)
        {
            if(chessOnBoard[tempRow][tempCol+1].getText().equals(colorOnBoard))
            {
                isDiffColor = false;
            }
            else if(chessOnBoard[tempRow][tempCol+1].getText().equals(EMPTY_CHESS))
            {
                isNotEmpty = false;
            }
            else
            {
                Location local = new Location(tempRow, tempCol+1);
                tempChange.add(local);
                tempCol++;
            }
        }

        if(!isDiffColor && isNotEmpty)
        {
            changeList.addAll(tempChange);
        }

        tempChange.clear();

        //check SE
        tempRow = row;
        tempCol = col;
        isDiffColor = true;
        isNotEmpty = true;
        while(tempCol+1 < BOARD_SIZE && tempRow+1 < BOARD_SIZE && isDiffColor && isNotEmpty)
        {
            if(chessOnBoard[tempRow+1][tempCol+1].getText().equals(colorOnBoard))
            {
                isDiffColor = false;
            }
            else if(chessOnBoard[tempRow+1][tempCol+1].getText().equals(EMPTY_CHESS))
            {
                isNotEmpty = false;
            }
            else
            {
                Location local = new Location(tempRow+1, tempCol+1);
                tempChange.add(local);
                tempCol++;
                tempRow++;
            }
        }

        if(!isDiffColor && isNotEmpty)
        {
            changeList.addAll(tempChange);
        }

        tempChange.clear();

        //check S
        tempRow = row;
        tempCol = col;
        isDiffColor = true;
        isNotEmpty = true;
        while(tempRow+1 < BOARD_SIZE && isDiffColor && isNotEmpty)
        {
            if(chessOnBoard[tempRow+1][tempCol].getText().equals(colorOnBoard))
            {
                isDiffColor = false;
            }
            else if(chessOnBoard[tempRow+1][tempCol].getText().equals(EMPTY_CHESS))
            {
                isNotEmpty = false;
            }
            else
            {
                Location local = new Location(tempRow+1, tempCol);
                tempChange.add(local);
                tempRow++;
            }
        }

        if(!isDiffColor && isNotEmpty)
        {
            changeList.addAll(tempChange);
        }

        tempChange.clear();

        //check SW
        tempRow = row;
        tempCol = col;
        isDiffColor = true;
        isNotEmpty = true;
        while(tempRow+1 < BOARD_SIZE && tempCol-1 >= 0 && isDiffColor && isNotEmpty)
        {
            if(chessOnBoard[tempRow+1][tempCol-1].getText().equals(colorOnBoard))
            {
                isDiffColor = false;
            }
            else if(chessOnBoard[tempRow+1][tempCol-1].getText().equals(EMPTY_CHESS))
            {
                isNotEmpty = false;
            }
            else
            {
                Location local = new Location(tempRow+1, tempCol-1);
                tempChange.add(local);
                tempRow++;
                tempCol--;
            }
        }

        if(!isDiffColor && isNotEmpty)
        {
            changeList.addAll(tempChange);
        }

        tempChange.clear();

        //check W
        tempRow = row;
        tempCol = col;
        isDiffColor = true;
        isNotEmpty = true;
        while(tempCol-1 >= 0 && isDiffColor && isNotEmpty)
        {
            if(chessOnBoard[tempRow][tempCol-1].getText().equals(colorOnBoard))
            {
                isDiffColor = false;
            }
            else if(chessOnBoard[tempRow][tempCol-1].getText().equals(EMPTY_CHESS))
            {
                isNotEmpty = false;
            }
            else
            {
                Location local = new Location(tempRow, tempCol-1);
                tempChange.add(local);
                tempCol--;
            }
        }

        if(!isDiffColor && isNotEmpty)
        {
            changeList.addAll(tempChange);
        }

        tempChange.clear();

        //check NW
        tempRow = row;
        tempCol = col;
        isDiffColor = true;
        isNotEmpty = true;

        while(tempCol-1 >= 0 && tempRow-1 >= 0 && isDiffColor && isNotEmpty)
        {
            if(chessOnBoard[tempRow-1][tempCol-1].getText().equals(colorOnBoard))
            {
                isDiffColor = false;
            }
            else if(chessOnBoard[tempRow-1][tempCol-1].getText().equals(EMPTY_CHESS))
            {
                isNotEmpty = false;
            }
            else
            {
                Location local = new Location(tempRow-1, tempCol-1);
                tempChange.add(local);
                tempCol--;
                tempRow--;
            }
        }

        if(!isDiffColor && isNotEmpty)
        {
            changeList.addAll(tempChange);
        }

        tempChange.clear();

        for(Location point : changeList)
        {
            int changeRow = point.getRow();
            int changeCol = point.getCol();
            setChessOnBoard(changeRow, changeCol, colorOnBoard);
        }

    }

    private void updateWhiteAvailable(int row, int col)
    {
        if(!chessOnBoard[row][col].getText().equals(EMPTY_CHESS))
        {
            availableMove[row][col].setWhiteMove(false);
        }
        else
        {
            int tempRow = row;
            int tempCol = col;

            //check N
            if(tempRow-1 >= 0 && chessOnBoard[tempRow-1][tempCol].getText().equals(BLACK_CHESS))
            {
                tempRow--;

                while(tempRow-1 >= 0)
                {
                    if(chessOnBoard[tempRow-1][tempCol].getText().equals(WHITE_CHESS))
                    {
                        availableMove[row][col].setWhiteMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow-1][tempCol].getText().equals(BLACK_CHESS))
                    {
                        tempRow--;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check NE
            tempRow = row;
            tempCol = col;

            if(tempRow-1 >= 0 && tempCol+1 < BOARD_SIZE && chessOnBoard[tempRow-1][tempCol+1].getText().equals(BLACK_CHESS))
            {
                tempRow--;
                tempCol++;

                while(tempRow-1 >= 0 && tempCol+1 < BOARD_SIZE)
                {
                    if(chessOnBoard[tempRow-1][tempCol+1].getText().equals(WHITE_CHESS))
                    {
                        availableMove[row][col].setWhiteMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow-1][tempCol+1].getText().equals(BLACK_CHESS))
                    {
                        tempRow--;
                        tempCol++;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check E
            tempRow = row;
            tempCol = col;

            if(tempCol+1 < BOARD_SIZE && chessOnBoard[tempRow][tempCol+1].getText().equals(BLACK_CHESS))
            {
                tempCol++;

                while(tempCol+1 < BOARD_SIZE)
                {
                    if(chessOnBoard[tempRow][tempCol+1].getText().equals(WHITE_CHESS))
                    {
                        availableMove[row][col].setWhiteMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow][tempCol+1].getText().equals(BLACK_CHESS))
                    {
                        tempCol++;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check SE
            tempRow = row;
            tempCol = col;

            if(tempCol+1 < BOARD_SIZE && tempRow+1 < BOARD_SIZE && chessOnBoard[tempRow+1][tempCol+1].getText().equals(BLACK_CHESS))
            {
                tempCol++;
                tempRow++;

                while(tempRow+1 < BOARD_SIZE && tempCol+1 < BOARD_SIZE)
                {
                    if(chessOnBoard[tempRow+1][tempCol+1].getText().equals(WHITE_CHESS))
                    {
                        availableMove[row][col].setWhiteMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow+1][tempCol+1].getText().equals(BLACK_CHESS))
                    {
                        tempCol++;
                        tempRow++;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check S
            tempRow = row;
            tempCol = col;

            if(tempRow+1 < BOARD_SIZE && chessOnBoard[tempRow+1][tempCol].getText().equals(BLACK_CHESS))
            {
                tempRow++;

                while(tempRow+1 < BOARD_SIZE)
                {
                    if(chessOnBoard[tempRow+1][tempCol].getText().equals(WHITE_CHESS))
                    {
                        availableMove[row][col].setWhiteMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow+1][tempCol].getText().equals(BLACK_CHESS))
                    {
                        tempRow++;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check SW
            tempRow = row;
            tempCol = col;

            if(tempRow+1 < BOARD_SIZE && tempCol-1 >= 0  && chessOnBoard[tempRow+1][tempCol-1].getText().equals(BLACK_CHESS))
            {
                tempRow++;
                tempCol--;

                while(tempRow+1 < BOARD_SIZE && tempCol-1 >= 0)
                {
                    if(chessOnBoard[tempRow+1][tempCol-1].getText().equals(WHITE_CHESS))
                    {
                        availableMove[row][col].setWhiteMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow+1][tempCol-1].getText().equals(BLACK_CHESS))
                    {
                        tempRow++;
                        tempCol--;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check W
            tempRow = row;
            tempCol = col;

            if(tempCol-1 >= 0  && chessOnBoard[tempRow][tempCol-1].getText().equals(BLACK_CHESS))
            {
                tempCol--;

                while(tempCol-1 >= 0)
                {
                    if(chessOnBoard[tempRow][tempCol-1].getText().equals(WHITE_CHESS))
                    {
                        availableMove[row][col].setWhiteMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow][tempCol-1].getText().equals(BLACK_CHESS))
                    {
                        tempCol--;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check NW
            tempRow = row;
            tempCol = col;

            if(tempCol-1 >= 0 && tempRow-1 >= 0  && chessOnBoard[tempRow-1][tempCol-1].getText().equals(BLACK_CHESS))
            {
                tempCol--;
                tempRow--;

                while(tempCol-1 >= 0 && tempRow-1 >= 0)
                {
                    if(chessOnBoard[tempRow-1][tempCol-1].getText().equals(WHITE_CHESS))
                    {
                        availableMove[row][col].setWhiteMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow-1][tempCol-1].getText().equals(BLACK_CHESS))
                    {
                        tempCol--;
                        tempRow--;
                    }
                    else
                    {
                        break;
                    }
                }

            }
            availableMove[row][col].setWhiteMove(false);
        }
    }


    private void updateBlackAvailable(int row, int col)
    {
        if(!chessOnBoard[row][col].getText().equals(EMPTY_CHESS))
        {
            availableMove[row][col].setBlackMove(false);
        }
        else
        {
            int tempRow = row;
            int tempCol = col;

            //check N
            if(tempRow-1 >= 0 && chessOnBoard[tempRow-1][tempCol].getText().equals(WHITE_CHESS))
            {
                tempRow--;

                while(tempRow-1 >= 0)
                {
                    if(chessOnBoard[tempRow-1][tempCol].getText().equals(BLACK_CHESS))
                    {
                        availableMove[row][col].setBlackMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow-1][tempCol].getText().equals(WHITE_CHESS))
                    {
                        tempRow--;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check NE
            tempRow = row;
            tempCol = col;

            if(tempRow-1 >= 0 && tempCol+1 < BOARD_SIZE && chessOnBoard[tempRow-1][tempCol+1].getText().equals(WHITE_CHESS))
            {
                tempRow--;
                tempCol++;

                while(tempRow-1 >= 0 && tempCol+1 < BOARD_SIZE)
                {
                    if(chessOnBoard[tempRow-1][tempCol+1].getText().equals(BLACK_CHESS))
                    {
                        availableMove[row][col].setBlackMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow-1][tempCol+1].getText().equals(WHITE_CHESS))
                    {
                        tempRow--;
                        tempCol++;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check E
            tempRow = row;
            tempCol = col;

            if(tempCol+1 < BOARD_SIZE && chessOnBoard[tempRow][tempCol+1].getText().equals(WHITE_CHESS))
            {
                tempCol++;

                while(tempCol+1 < BOARD_SIZE)
                {
                    if(chessOnBoard[tempRow][tempCol+1].getText().equals(BLACK_CHESS))
                    {
                        availableMove[row][col].setBlackMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow][tempCol+1].getText().equals(WHITE_CHESS))
                    {
                        tempCol++;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check SE
            tempRow = row;
            tempCol = col;

            if(tempCol+1 < BOARD_SIZE && tempRow+1 < BOARD_SIZE && chessOnBoard[tempRow+1][tempCol+1].getText().equals(WHITE_CHESS))
            {
                tempCol++;
                tempRow++;

                while(tempRow+1 < BOARD_SIZE && tempCol+1 < BOARD_SIZE)
                {
                    if(chessOnBoard[tempRow+1][tempCol+1].getText().equals(BLACK_CHESS))
                    {
                        availableMove[row][col].setBlackMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow+1][tempCol+1].getText().equals(WHITE_CHESS))
                    {
                        tempCol++;
                        tempRow++;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check S
            tempRow = row;
            tempCol = col;

            if(tempRow+1 < BOARD_SIZE && chessOnBoard[tempRow+1][tempCol].getText().equals(WHITE_CHESS))
            {
                tempRow++;

                while(tempRow+1 < BOARD_SIZE)
                {
                    if(chessOnBoard[tempRow+1][tempCol].getText().equals(BLACK_CHESS))
                    {
                        availableMove[row][col].setBlackMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow+1][tempCol].getText().equals(WHITE_CHESS))
                    {
                        tempRow++;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check SW
            tempRow = row;
            tempCol = col;

            if(tempRow+1 < BOARD_SIZE && tempCol-1 >= 0  && chessOnBoard[tempRow+1][tempCol-1].getText().equals(WHITE_CHESS))
            {
                tempRow++;
                tempCol--;

                while(tempRow+1 < BOARD_SIZE && tempCol-1 >= 0)
                {
                    if(chessOnBoard[tempRow+1][tempCol-1].getText().equals(BLACK_CHESS))
                    {
                        availableMove[row][col].setBlackMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow+1][tempCol-1].getText().equals(WHITE_CHESS))
                    {
                        tempRow++;
                        tempCol--;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check W
            tempRow = row;
            tempCol = col;

            if(tempCol-1 >= 0  && chessOnBoard[tempRow][tempCol-1].getText().equals(WHITE_CHESS))
            {
                tempCol--;

                while(tempCol-1 >= 0)
                {
                    if(chessOnBoard[tempRow][tempCol-1].getText().equals(BLACK_CHESS))
                    {
                        availableMove[row][col].setBlackMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow][tempCol-1].getText().equals(WHITE_CHESS))
                    {
                        tempCol--;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            //check NW
            tempRow = row;
            tempCol = col;

            if(tempCol-1 >= 0 && tempRow-1 >= 0  && chessOnBoard[tempRow-1][tempCol-1].getText().equals(WHITE_CHESS))
            {
                tempCol--;
                tempRow--;

                while(tempCol-1 >= 0 && tempRow-1 >= 0)
                {
                    if(chessOnBoard[tempRow-1][tempCol-1].getText().equals(BLACK_CHESS))
                    {
                        availableMove[row][col].setBlackMove(true);
                        return;
                    }
                    else if(chessOnBoard[tempRow-1][tempCol-1].getText().equals(WHITE_CHESS))
                    {
                        tempCol--;
                        tempRow--;
                    }
                    else
                    {
                        break;
                    }
                }

            }
            availableMove[row][col].setBlackMove(false);
        }
    }

}
