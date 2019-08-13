package nl.craftsmen.huffman;

import org.junit.Test;

public class LzwEncoderTest {

    @Test
    public void test_encoder() {
        String testString = "BABAABAAA";
        LzwEncoder lzwEncoder = new LzwEncoder();
        Short[] bytes = lzwEncoder.encode(testString.getBytes());
    }
}
