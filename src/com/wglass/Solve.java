package com.wglass;

public class Solve
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {

        BoardGraph puzzle = new BoardGraph("3563 4784 3123 4004 9999");

        puzzle.search();
    }

}
