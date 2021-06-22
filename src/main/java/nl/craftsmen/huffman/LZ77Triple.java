package nl.craftsmen.huffman;

import lombok.*;

@Value
@Builder
public class LZ77Triple {

    private final int offset;
    private final int length;
    private final char character;
}
