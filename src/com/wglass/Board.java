package com.wglass;

import java.util.HashSet;
import java.util.Set;

/**
 * Assumptions:
 *  -- 4x5 board
 *  -- exactly two spaces
 *
 */
public class Board
{
    private final static int ROWS = 5;
    private final static int COLS = 4;

    Piece[][] pieces = new Piece[ROWS][COLS];

    private Board()
    {
    }

    public Set<String> getMoves()
    {
        // find the spaces
        int[] spacerow = new int[2];
        int[] spacecol = new int[2];
        findSpaces(spacerow, spacecol);

        Set<String> result = new HashSet<String>();
        collectMoves(result, spacerow[0], spacecol[0]);
        collectMoves(result, spacerow[1], spacecol[1]);

        return result;
    }

    void findSpaces(int[] spacerow, int[] spacecol)
    {
        int spaceCount = 0;
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                if (pieces[row][col] == Piece.SPACE && spaceCount == 0)
                {
                    spacerow[0] = row;
                    spacecol[0] = col;
                    spaceCount++;
                }
                else if (pieces[row][col] == Piece.SPACE && spaceCount == 1)
                {
                    spacerow[1] = row;
                    spacecol[1] = col;
                    spaceCount++;
                    break;
                }
            }
            if (spaceCount == 2)
            {
                break;
            }
        }

        assert(spaceCount == 2);
    }

    private void collectMoves(Set<String> moves, int row, int col)
    {
        Board downMove = moveDown(row, col);
        if (downMove != null)
        {
            moves.add(downMove.getEncoding());
        }
        Board upMove = moveUp(row, col);
        if (upMove != null)
        {
            moves.add(upMove.getEncoding());
        }
        Board leftMove = moveLeft(row, col);
        if (leftMove != null)
        {
            moves.add(leftMove.getEncoding());
        }
        Board rightMove = moveRight(row, col);
        if (rightMove != null)
        {
            moves.add(rightMove.getEncoding());
        }
    }

    private Board moveLeft(int row, int col)
    {
        Board newBoard = null;

        if (col == COLS - 1)
        {
            newBoard = null;
        }

        else if (pieces[row][col+1] == Piece.ONE)
        {
            newBoard = this.copy();
            newBoard.pieces[row][col+1] = Piece.SPACE;
            newBoard.pieces[row][col] = Piece.ONE;
        }

        else if (pieces[row][col+1] == Piece.LEFT_2x1)
        {
            newBoard = this.copy();
            newBoard.pieces[row][col+2] = Piece.SPACE;
            newBoard.pieces[row][col+1] = Piece.RIGHT_2x1;
            newBoard.pieces[row][col] = Piece.LEFT_2x1;
        }

        else if (pieces[row][col+1] == Piece.TOP_1x2 && pieces[row+1][col] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row][col+1] = Piece.SPACE;
            newBoard.pieces[row+1][col+1] = Piece.SPACE;
            newBoard.pieces[row][col] = Piece.TOP_1x2;
            newBoard.pieces[row+1][col] = Piece.BOTTOM_1x2;
        }

        else if (pieces[row][col+1] == Piece.BOTTOM_1x2 && pieces[row-1][col] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row-1][col+1] = Piece.SPACE;
            newBoard.pieces[row][col+1] = Piece.SPACE;
            newBoard.pieces[row-1][col] = Piece.TOP_1x2;
            newBoard.pieces[row][col] = Piece.BOTTOM_1x2;
        }

        else if (pieces[row][col+1] == Piece.TOPLEFT_2x2 && pieces[row+1][col] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row][col+2] = Piece.SPACE;
            newBoard.pieces[row+1][col+2] = Piece.SPACE;
            newBoard.pieces[row][col+1] = Piece.TOPRIGHT_2x2;
            newBoard.pieces[row+1][col+1] = Piece.BOTTOMRIGHT_2x2;
            newBoard.pieces[row][col] = Piece.TOPLEFT_2x2;
            newBoard.pieces[row+1][col] = Piece.BOTTOMLEFT_2x2;
        }

        else if (pieces[row][col+1] == Piece.BOTTOMLEFT_2x2 && pieces[row-1][col] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row-1][col+2] = Piece.SPACE;
            newBoard.pieces[row][col+2] = Piece.SPACE;
            newBoard.pieces[row-1][col+1] = Piece.TOPRIGHT_2x2;
            newBoard.pieces[row][col+1] = Piece.BOTTOMRIGHT_2x2;
            newBoard.pieces[row-1][col] = Piece.TOPLEFT_2x2;
            newBoard.pieces[row][col] = Piece.BOTTOMLEFT_2x2;
        }

        return newBoard;
    }

    private Board moveRight(int row, int col)
    {
        Board newBoard = null;

        if (col == 0)
        {
            newBoard = null;
        }

        else if (pieces[row][col-1] == Piece.ONE)
        {
            newBoard = this.copy();
            newBoard.pieces[row][col-1] = Piece.SPACE;
            newBoard.pieces[row][col] = Piece.ONE;
        }

        else if (pieces[row][col-1] == Piece.RIGHT_2x1)
        {
            newBoard = this.copy();
            newBoard.pieces[row][col-2] = Piece.SPACE;
            newBoard.pieces[row][col-1] = Piece.LEFT_2x1;
            newBoard.pieces[row][col] = Piece.RIGHT_2x1;
        }

        else if (pieces[row][col-1] == Piece.TOP_1x2 && pieces[row+1][col] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row][col-1] = Piece.SPACE;
            newBoard.pieces[row+1][col-1] = Piece.SPACE;
            newBoard.pieces[row][col] = Piece.TOP_1x2;
            newBoard.pieces[row+1][col] = Piece.BOTTOM_1x2;
        }

        else if (pieces[row][col-1] == Piece.BOTTOM_1x2 && pieces[row-1][col] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row-1][col-1] = Piece.SPACE;
            newBoard.pieces[row][col-1] = Piece.SPACE;
            newBoard.pieces[row-1][col] = Piece.TOP_1x2;
            newBoard.pieces[row][col] = Piece.BOTTOM_1x2;
        }

        else if (pieces[row][col-1] == Piece.TOPRIGHT_2x2 && pieces[row+1][col] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row][col-2] = Piece.SPACE;
            newBoard.pieces[row+1][col-2] = Piece.SPACE;
            newBoard.pieces[row][col-1] = Piece.TOPLEFT_2x2;
            newBoard.pieces[row+1][col-1] = Piece.BOTTOMLEFT_2x2;
            newBoard.pieces[row][col] = Piece.TOPRIGHT_2x2;
            newBoard.pieces[row+1][col] = Piece.BOTTOMRIGHT_2x2;
        }

        else if (pieces[row][col-1] == Piece.BOTTOMRIGHT_2x2 && pieces[row-1][col] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row-1][col-2] = Piece.SPACE;
            newBoard.pieces[row][col-2] = Piece.SPACE;
            newBoard.pieces[row-1][col-1] = Piece.TOPLEFT_2x2;
            newBoard.pieces[row][col-1] = Piece.BOTTOMLEFT_2x2;
            newBoard.pieces[row-1][col] = Piece.TOPRIGHT_2x2;
            newBoard.pieces[row][col] = Piece.BOTTOMRIGHT_2x2;
        }

        return newBoard;
    }

    private Board moveUp(int row, int col)
    {
        Board newBoard = null;

        if (row == ROWS - 1)
        {
            newBoard = null;
        }

        else if (pieces[row+1][col] == Piece.ONE)
        {
            newBoard = this.copy();
            newBoard.pieces[row+1][col] = Piece.SPACE;
            newBoard.pieces[row][col] = Piece.ONE;
        }

        else if (pieces[row+1][col] == Piece.LEFT_2x1 && pieces[row][col+1] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row+1][col] = Piece.SPACE;
            newBoard.pieces[row+1][col+1] = Piece.SPACE;
            newBoard.pieces[row][col] = Piece.LEFT_2x1;
            newBoard.pieces[row][col+1] = Piece.RIGHT_2x1;
        }

        else if (pieces[row+1][col] == Piece.RIGHT_2x1 && pieces[row][col-1] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row+1][col-1] = Piece.SPACE;
            newBoard.pieces[row+1][col] = Piece.SPACE;
            newBoard.pieces[row][col-1] = Piece.LEFT_2x1;
            newBoard.pieces[row][col] = Piece.RIGHT_2x1;
        }

        else if (pieces[row+1][col] == Piece.TOP_1x2)
        {
            newBoard = this.copy();
            newBoard.pieces[row+2][col] = Piece.SPACE;
            newBoard.pieces[row+1][col] = Piece.BOTTOM_1x2;
            newBoard.pieces[row][col] = Piece.TOP_1x2;
        }

        else if (pieces[row+1][col] == Piece.TOPLEFT_2x2 && pieces[row][col+1] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row+2][col] = Piece.SPACE;
            newBoard.pieces[row+2][col+1] = Piece.SPACE;
            newBoard.pieces[row+1][col] = Piece.BOTTOMLEFT_2x2;
            newBoard.pieces[row+1][col+1] = Piece.BOTTOMRIGHT_2x2;
            newBoard.pieces[row][col] = Piece.TOPLEFT_2x2;
            newBoard.pieces[row][col+1] = Piece.TOPRIGHT_2x2;
        }

        else if (pieces[row+1][col] == Piece.TOPRIGHT_2x2 && pieces[row][col-1] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row+2][col-1] = Piece.SPACE;
            newBoard.pieces[row+2][col] = Piece.SPACE;
            newBoard.pieces[row+1][col-1] = Piece.BOTTOMLEFT_2x2;
            newBoard.pieces[row+1][col] = Piece.BOTTOMRIGHT_2x2;
            newBoard.pieces[row][col-1] = Piece.TOPLEFT_2x2;
            newBoard.pieces[row][col] = Piece.TOPRIGHT_2x2;
        }

        return newBoard;
    }

    private Board moveDown(int row, int col)
    {
        Board newBoard = null;

        if (row == 0)
        {
            newBoard = null;
        }

        else if (pieces[row-1][col] == Piece.ONE)
        {
            newBoard = this.copy();
            newBoard.pieces[row-1][col] = Piece.SPACE;
            newBoard.pieces[row][col] = Piece.ONE;
        }

        else if (pieces[row-1][col] == Piece.LEFT_2x1 && pieces[row][col+1] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row-1][col] = Piece.SPACE;
            newBoard.pieces[row-1][col+1] = Piece.SPACE;
            newBoard.pieces[row][col] = Piece.LEFT_2x1;
            newBoard.pieces[row][col+1] = Piece.RIGHT_2x1;
        }

        else if (pieces[row-1][col] == Piece.RIGHT_2x1 && pieces[row][col-1] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row-1][col-1] = Piece.SPACE;
            newBoard.pieces[row-1][col] = Piece.SPACE;
            newBoard.pieces[row][col-1] = Piece.LEFT_2x1;
            newBoard.pieces[row][col] = Piece.RIGHT_2x1;
        }

        else if (pieces[row-1][col] == Piece.BOTTOM_1x2)
        {
            newBoard = this.copy();
            newBoard.pieces[row-2][col] = Piece.SPACE;
            newBoard.pieces[row-1][col] = Piece.TOP_1x2;
            newBoard.pieces[row][col] = Piece.BOTTOM_1x2;
        }

        else if (pieces[row-1][col] == Piece.BOTTOMLEFT_2x2 && pieces[row][col+1] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row-2][col] = Piece.SPACE;
            newBoard.pieces[row-2][col+1] = Piece.SPACE;
            newBoard.pieces[row-1][col] = Piece.TOPLEFT_2x2;
            newBoard.pieces[row-1][col+1] = Piece.TOPRIGHT_2x2;
            newBoard.pieces[row][col] = Piece.BOTTOMLEFT_2x2;
            newBoard.pieces[row][col+1] = Piece.BOTTOMRIGHT_2x2;
        }

        else if (pieces[row-1][col] == Piece.BOTTOMRIGHT_2x2 && pieces[row][col-1] == Piece.SPACE)
        {
            newBoard = this.copy();
            newBoard.pieces[row-2][col-1] = Piece.SPACE;
            newBoard.pieces[row-2][col] = Piece.SPACE;
            newBoard.pieces[row-1][col-1] = Piece.TOPLEFT_2x2;
            newBoard.pieces[row-1][col] = Piece.TOPRIGHT_2x2;
            newBoard.pieces[row][col-1] = Piece.BOTTOMLEFT_2x2;
            newBoard.pieces[row][col] = Piece.BOTTOMRIGHT_2x2;
        }

        return newBoard;
    }

    public static Board decodeString(String codedString)
    {
        codedString = codedString.replace(" ", "");

        assert(codedString.length() == ROWS * COLS);

        Board board = new Board();
        for (int i=0; i < ROWS * COLS; i++)
        {
            int digit = Integer.parseInt(codedString.substring(i, i+1));
            int col = i % COLS;
            int row = i / COLS;
            board.pieces[row][col] = Piece.valueOf(digit);
        }

        return board;
    }


    /**
     * return initial value of board
     * @return
     */
    public static Board initialize()
    {
        Board board = new Board();
        board.pieces[0][0] = Piece.TOP_1x2;
        board.pieces[0][1] = Piece.TOPLEFT_2x2;
        board.pieces[0][2] = Piece.TOPRIGHT_2x2;
        board.pieces[0][3] = Piece.TOP_1x2;

        board.pieces[1][0] = Piece.BOTTOM_1x2;
        board.pieces[1][1] = Piece.BOTTOMLEFT_2x2;
        board.pieces[1][2] = Piece.BOTTOMRIGHT_2x2;
        board.pieces[1][3] = Piece.BOTTOM_1x2;

        board.pieces[2][0] = Piece.TOP_1x2;
        board.pieces[2][1] = Piece.LEFT_2x1;
        board.pieces[2][2] = Piece.RIGHT_2x1;
        board.pieces[2][3] = Piece.TOP_1x2;

        board.pieces[3][0] = Piece.BOTTOM_1x2;
        board.pieces[3][1] = Piece.SPACE;
        board.pieces[3][2] = Piece.SPACE;
        board.pieces[3][3] = Piece.BOTTOM_1x2;

        board.pieces[4][0] = Piece.ONE;
        board.pieces[4][1] = Piece.ONE;
        board.pieces[4][2] = Piece.ONE;
        board.pieces[4][3] = Piece.ONE;

        return board;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Board))
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                if (this.pieces[row][col] != ((Board) obj).pieces[row][col])
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return getEncoding().hashCode();
    }

    public String getEncoding()
    {
        StringBuilder s = new StringBuilder();
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                s.append(pieces[row][col].ordinal());
            }
        }
        return s.toString();
    }

    @Override
    public String toString()
    {
        return getTableString();
    }


    public String getPrettyEncoding()
    {
        String encoding = getEncoding();
        return encoding.substring(0, 4) + " "
                + encoding.substring(4, 8) + " "
                + encoding.substring(8, 12) + " "
                + encoding.substring(12, 16) + " "
                + encoding.substring(16, 20);
    }

    public Board copy()
    {
        Board board2 = new Board();
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                board2.pieces[row][col] = pieces[row][col];
            }
        }
        return board2;
    }

    public boolean isSolved()
    {
        return
            pieces[4][1] == Piece.BOTTOMLEFT_2x2
            && pieces[4][2] == Piece.BOTTOMRIGHT_2x2;
    }

    public String getTableString()
    {
        StringBuilder s = new StringBuilder();
        for (int i=0; i < ROWS * COLS; i++)
        {
            int row = i / COLS;
            int col = i % COLS;
            Piece p = pieces[row][col];
            char c;
            if (p == Piece.ONE)
            {
                //c = '\u2581';
                c = 'o';
            }
            else if (p == Piece.LEFT_2x1)
            {
                c = '\u228f';
            }
            else if (p == Piece.RIGHT_2x1)
            {
                c = '\u2290';
            }
            else if (p == Piece.TOP_1x2)
            {
                c = '\u2293';
            }
            else if (p == Piece.BOTTOM_1x2)
            {
                c = '\u2294';
            }
            else if (p == Piece.TOPLEFT_2x2)
            {
                c = 'x';
                //c = '\u231c';
            }
            else if (p == Piece.TOPRIGHT_2x2)
            {
                c = 'x';
                // c = '\u231d';
            }
            else if (p == Piece.BOTTOMLEFT_2x2)
            {
                c = 'x';
                // c = '\u231e';
            }
            else if (p == Piece.BOTTOMRIGHT_2x2)
            {
                c = 'x';
                // c = '\u231f';
            }
            else
            {
                c = '_';
            }
            s.append(c);
            if (i % COLS == 3)
            {
                s.append("\n");
            }
        }
        return s.toString();
    }

}
