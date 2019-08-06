package nl.craftsmen.huffman;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class HuffmanEncoderTest {

    @Test
    public void compress_small_string() {
        String testString = "the quick brown fox jumped over the lazy dog.";
        System.out.println("original string: " + testString);
        byte[] bytes = testString.getBytes();

        //compress the string
        EncodingResult encodingResult = HuffmanEncoder.compress(bytes);

        double originalLength = bytes.length;
        double compressedLength = encodingResult.getCompressedData().length;
        System.out.println("compression ratio: " + ((compressedLength / originalLength) * 100) + "% of original data.");
        //decompress the string
        byte[] decompressedBytes = HuffmanEncoder.decompress(encodingResult);
        System.out.println("decompressed string: " + new String(decompressedBytes));

    }

    @Test
    public void compress_the_lord_of_the_rings() throws URISyntaxException, IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(getClass().getResource("/lotr.txt").toURI()));

        //compress the data
        EncodingResult encodingResult = HuffmanEncoder.compress(bytes);

        double originalLength = bytes.length;
        System.out.println("original length: " + originalLength);
        double compressedLength = encodingResult.getCompressedData().length;
        System.out.println("compression ratio: " + ((compressedLength / originalLength) * 100) + "% of original data.");
        //decompress the data
        byte[] decompressedBytes = HuffmanEncoder.decompress(encodingResult);
        System.out.println("decompressed length: " + (double) decompressedBytes.length);


    }
}
