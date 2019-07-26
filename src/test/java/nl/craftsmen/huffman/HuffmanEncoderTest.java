package nl.craftsmen.huffman;

import java.util.Map;

import org.junit.Test;

public class HuffmanEncoderTest {

    @Test
    public void testHuffmanEncodingAndDecoding() {
        String testString = "the quick brown fox jumped over the lazy dog.";
        byte[] bytes = testString.getBytes();
        Node node = HuffmanEncoder.createTree(HuffmanEncoder.createInitialTreeElements(HuffmanEncoder.createFrequencyMap(bytes)));
        Map<Byte, String> map = HuffmanEncoder.createSymbolToCodeMap(node);
        String encodedString = HuffmanEncoder.encode(testString, map);
        int l = encodedString.length();
        Byte[] encodedArray = HuffmanEncoder.toByteArray(encodedString);
        String decodedString = HuffmanEncoder.decode(encodedArray, node, l);
        System.out.println(decodedString);

    }
}
