package com.wglass;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    List<String> boardEncodings;

    public Node(String boardEncoding)
    {
        boardEncodings = new ArrayList<String>();
        boardEncodings.add(boardEncoding);
    }
    public Node(String boardEncoding, Node originalNode)
    {
        boardEncodings = new ArrayList<String>();
        boardEncodings.addAll(originalNode.getBoardList());
        boardEncodings.add(boardEncoding);
    }

    public List<String> getBoardList()
    {
        return boardEncodings;
    }

    public String getBoard()
    {
        return boardEncodings.get(boardEncodings.size() - 1);
    }

}
