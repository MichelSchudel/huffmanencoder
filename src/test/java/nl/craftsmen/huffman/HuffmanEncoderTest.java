package nl.craftsmen.huffman;

import org.junit.Test;

public class HuffmanEncoderTest {

    @Test
    public void testHuffmanEncodingAndDecoding() {
        String testString = "the quick brown fox jumped over the lazy dog.";
        System.out.println("original string: " + testString);
        byte[] bytes = testString.getBytes();
        EncodingResult encodingResult = HuffmanEncoder.compress(bytes);
        byte[] decompressedBytes = HuffmanEncoder.decompress(encodingResult);
        System.out.println("decompressed string: " + new String(decompressedBytes));

    }
}
