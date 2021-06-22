package nl.craftsmen.huffman;

public final class LZ77StateRenderer {
    public static void renderState(String input, int currentPosition, LZ77Triple lz77Triple) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (i == currentPosition) {
                sb.append("[" + c + "] ");
            } else {
                sb.append(c + " ");
            }
        }
        System.out.println(sb);

    }
}
