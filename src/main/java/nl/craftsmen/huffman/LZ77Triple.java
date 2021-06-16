package nl.craftsmen.huffman;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class LZ77Triple {

    private final int offset;
    private final int length;
    private final char character;
}
