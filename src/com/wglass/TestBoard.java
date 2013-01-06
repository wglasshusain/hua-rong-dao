package com.wglass;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestBoard
{
    Board board1;
    Board board2;
    Board board3;
    Board board4;
    Board board5;

    @Before
    public void setup()
    {
        board1 = Board.initialize();
        board2 = Board.initialize();

        // center block moved down
        board3 = Board.initialize();
        board3.pieces[2][1] = Piece.SPACE;
        board3.pieces[2][2] = Piece.SPACE;
        board3.pieces[3][1] = Piece.LEFT_2x1;
        board3.pieces[3][2] = Piece.RIGHT_2x1;

        // remove upper left piece, put another horizontal below
        board4 = Board.initialize();
        board4.pieces[0][3] = Piece.SPACE;
        board4.pieces[1][3] = Piece.SPACE;
        board4.pieces[3][1] = Piece.LEFT_2x1;
        board4.pieces[3][2] = Piece.RIGHT_2x1;

        // test big pieces can't move
        board5 = Board.initialize();
        board5.pieces[3][1] = Piece.ONE;
        board5.pieces[4][1] = Piece.SPACE;
    }

    @Test
    public void validInitialState()
    {
        Board board = Board.initialize();
        validate(board);
    }

    @Test
    public void testEncodeDecodeString()
    {
        Board board = Board.initialize();
        String coded = board.getEncoding();
        Board board2 = Board.decodeString(coded);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                assertEquals(board.pieces[row][col], board2.pieces[row][col]);
            }
        }
    }

    @Test
    public void testEncodeDecodeStringWithspaces()
    {
        Board board = Board.initialize();
        String coded = board.getEncoding();
        coded = coded.substring(0, 10) + "  " + coded.substring(10);
        Board board2 = Board.decodeString(coded);

        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                assertEquals(board.pieces[row][col], board2.pieces[row][col]);
            }
        }
    }

    /**
     * check pieces are contiguous.  for error checking.
     */
    public void validate(Board board)
    {
        for (int row=0; row < 5; row++)
        {
            for (int col=0; col < 4; col++)
            {
                validateSquare(board, row, col);
            }
        }
    }

    private void validateSquare(Board board, int row, int col)
    {
        if (board.pieces[row][col] == Piece.BOTTOM_1x2)
        {
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.TOP_1x2, board.pieces[row-1][col]);
        }
        else if (board.pieces[row][col] == Piece.TOP_1x2)
        {
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.BOTTOM_1x2, board.pieces[row+1][col]);
        }
        else if (board.pieces[row][col] == Piece.LEFT_2x1)
        {
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.RIGHT_2x1, board.pieces[row][col+1]);
        }
        else if (board.pieces[row][col] == Piece.RIGHT_2x1)
        {
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.LEFT_2x1, board.pieces[row][col-1]);
        }
        else if (board.pieces[row][col] == Piece.BOTTOMLEFT_2x2)
        {
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.TOPLEFT_2x2, board.pieces[row-1][col]);
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.TOPRIGHT_2x2, board.pieces[row-1][col+1]);
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.BOTTOMRIGHT_2x2, board.pieces[row][col+1]);
        }
        else if (board.pieces[row][col] == Piece.BOTTOMRIGHT_2x2)
        {
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.TOPLEFT_2x2, board.pieces[row-1][col-1]);
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.TOPRIGHT_2x2, board.pieces[row-1][col]);
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.BOTTOMLEFT_2x2, board.pieces[row][col-1]);
        }
        else if (board.pieces[row][col] == Piece.TOPLEFT_2x2)
        {
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.TOPRIGHT_2x2, board.pieces[row][col+1]);
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.BOTTOMLEFT_2x2, board.pieces[row+1][col]);
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.BOTTOMRIGHT_2x2, board.pieces[row+1][col+1]);
        }
        else if (board.pieces[row][col] == Piece.TOPRIGHT_2x2)
        {
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.TOPLEFT_2x2, board.pieces[row][col-1]);
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.BOTTOMLEFT_2x2, board.pieces[row+1][col-1]);
            assertEquals(row + "," + col + "=" + board.pieces[row][col], Piece.BOTTOMRIGHT_2x2, board.pieces[row+1][col]);
        }
    }

    @Test
    public void testEquals()
    {
        assertTrue(board1.equals(board2));
        assertTrue(board2.equals(board1));
        assertFalse(board1.equals(board3));
    }

    @Test
    public void testCopy()
    {
        assertEquals(board1, board1.copy());
    }

    @Test
    public void testHashcode()
    {
        assertTrue(board1.hashCode() == board2.hashCode());
        assertFalse(board1.hashCode() == board3.hashCode());
    }

    @Test
    public void testEncodeEquals()
    {
        assertEquals(board1.getEncoding(), board1.getEncoding());
        assertEquals(board1.getEncoding(), board2.getEncoding());

        assertFalse(board1.getEncoding().equals(board3.getEncoding()));
    }

    @Test
    public void testFindSpaces()
    {
        int[] spacerow = new int[2];
        int[] spacecol = new int[2];

        board1.findSpaces(spacerow, spacecol);
        assertEquals(2, spacerow.length);
        assertEquals(2, spacecol.length);

        assertEquals(3, spacerow[0]);
        assertEquals(1, spacecol[0]);
        assertEquals(3, spacerow[1]);
        assertEquals(2, spacecol[1]);
    }

    @Test
    public void testCollectMove()
    {
        // board1: 3563 4784 3123 4004 9999
        Set<String> moves1 = board1.getMoves();
        assertEquals(3, moves1.size());
        assertTrue(moves1.contains(Board.decodeString("3563 4784 3123 4904 9099").getEncoding()));
        assertTrue(moves1.contains(Board.decodeString("3563 4784 3123 4094 9909").getEncoding()));
        assertTrue(moves1.contains(Board.decodeString("3563 4784 3003 4124 9999").getEncoding()));

        // board3: 3563 4784 3003 4124 9999
        Set<String> moves3 = board3.getMoves();
        assertEquals(2, moves3.size());
        assertTrue(moves3.contains(Board.decodeString("3563 4784 3123 4004 9999").getEncoding()));
        assertTrue(moves3.contains(Board.decodeString("3003 4564 3783 4124 9999").getEncoding()));

        // board4: 3560 4780 3123 4124 9999
        Set<String> moves4 = board4.getMoves();
        assertEquals(2, moves4.size());
        assertTrue(moves4.contains(Board.decodeString("3056 4078 3123 4124 9999").getEncoding()));
        assertTrue(moves4.contains(Board.decodeString("3560 4783 3124 4120 9999").getEncoding()));

        // board5: 3563 4784 3123 4904 9099
        Set<String> moves5 = board5.getMoves();
        assertEquals(5, moves5.size());
        assertTrue(moves5.contains(Board.decodeString("3563 4784 3123 4994 9009").getEncoding()));
        assertTrue(moves5.contains(Board.decodeString("3563 4784 3123 4004 9999").getEncoding()));
        assertTrue(moves5.contains(Board.decodeString("3563 4784 3123 4094 9099").getEncoding()));
        assertTrue(moves5.contains(Board.decodeString("3563 4784 3123 4904 9909").getEncoding()));
        assertTrue(moves5.contains(Board.decodeString("3563 4784 3123 4904 0999").getEncoding()));
    }

}
