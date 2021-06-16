package nl.craftsmen.huffman;

import java.util.ArrayList;
import java.util.List;

public class LZ77Encoder {

    public List<LZ77Triple> encode(final String input) {
        List<LZ77Triple> lz77Triples = new ArrayList<>();
        int currentPosition = 0;
        while (currentPosition < input.length()) {
            boolean found = false;
            String searchBuffer = input.substring(0, currentPosition);
            for (int i = input.length(); i > currentPosition; i--) {
                String pattern = input.substring(currentPosition, i);
                if (searchBuffer.contains(pattern)) {
                    found = true;
                    int index = searchBuffer.lastIndexOf(pattern);
                    int length = pattern.length();
                    char c = input.charAt(currentPosition + pattern.length());
                    int offset = currentPosition - index;
                    LZ77Triple lZ77Triple = LZ77Triple.builder()
                            .offset(offset)
                            .length(length)
                            .character(c).build();
                    lz77Triples.add(lZ77Triple);
                    currentPosition = currentPosition + pattern.length() + 1;
                    break;
                }

            }
            if (!found) {
                LZ77Triple lZ77Triple = LZ77Triple.builder()
                        .offset(0)
                        .length(0)
                        .character(input.charAt(currentPosition)).build();
                lz77Triples.add(lZ77Triple);
                currentPosition = currentPosition + 1;
            }
            found = false;

        }
        return lz77Triples;
    }

    public String decode(List<LZ77Triple> lz77Triples) {
        StringBuilder stringBuilder = new StringBuilder();
        for (LZ77Triple lz77Triple: lz77Triples) {
            int offset = lz77Triple.getOffset();
            int length = lz77Triple.getLength();
            int startingPosition = stringBuilder.length() - offset;
            String s;
            if (startingPosition < stringBuilder.length()) {
                s = stringBuilder.substring(startingPosition, startingPosition + length);
            } else {
                s = "";
            }
            stringBuilder.append(s);
            stringBuilder.append(lz77Triple.getCharacter());
        }
        return stringBuilder.toString();
    }
}
