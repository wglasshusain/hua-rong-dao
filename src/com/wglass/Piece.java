package com.wglass;

import java.util.HashMap;
import java.util.Map;

public enum Piece
{
    SPACE, LEFT_2x1, RIGHT_2x1, TOP_1x2, BOTTOM_1x2, TOPLEFT_2x2, TOPRIGHT_2x2, BOTTOMLEFT_2x2, BOTTOMRIGHT_2x2, ONE;

    private static final Map<Integer, Piece> ORDINAL_TO_ENUM_MAPPING = new HashMap<Integer, Piece>();
    static
    {
        for (Piece piece : Piece.values())
        {
            ORDINAL_TO_ENUM_MAPPING.put(piece.ordinal(), piece);
        }
    };

    public static Piece valueOf(int ordinal)
    {
        return ORDINAL_TO_ENUM_MAPPING.get(ordinal);
    }

}
