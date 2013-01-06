package com.wglass;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BoardGraph
{
    Queue<Node> boardsToSearch;
    Set<String> searchedBoards;

    String initialBoard;

    public BoardGraph(String initialBoard)
    {
        this.initialBoard = initialBoard;

        boardsToSearch = new LinkedList<Node>();
        searchedBoards = new HashSet<String>();
    }

    public void search()
    {
        Node node = new Node(initialBoard);
        boardsToSearch.add(node);
        searchedBoards.add(initialBoard);
        Node finalNode = null;
        while (boardsToSearch.size() > 0)
        {
            node = boardsToSearch.remove();
            String board = node.getBoard();
            Set<String> moves = Board.decodeString(board).getMoves();
            for (String move : moves)
            {
                if (Board.decodeString(move).isSolved())
                {
                    finalNode = new Node(move, node);
                    break;
                }
                else if (!searchedBoards.contains(move))
                {
                    Node newNode = new Node(move, node);
                    boardsToSearch.add(newNode);
                    searchedBoards.add(move);
                }
            }
        }

        if (finalNode != null)
        {
            System.out.println("Solution in " + (finalNode.getBoardList().size() - 1) + " moves.\n---------\n");
            int i=0;
            for (String s : finalNode.getBoardList())
            {
                System.out.println(i + ": " + Board.decodeString(s).getPrettyEncoding());
                System.out.println(Board.decodeString(s).toString());
                System.out.println("-------------------------------");
                i++;}
        }


    }

}
