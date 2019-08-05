package nl.craftsmen.huffman;

/**
 * The encodingResult class.
 */
public class EncodingResult {

    private final Byte[] compressedData;
    private final int compressedDataBitLength;
    private final Node huffmanTree;

    public EncodingResult(final Byte[] compressedData, final int compressedDataBitLength, final Node huffmanTree) {
        this.compressedData = compressedData;
        this.compressedDataBitLength = compressedDataBitLength;
        this.huffmanTree = huffmanTree;
    }

    /**
     * Gets the compressed data.
     * @return the compressed data.
     */
    public Byte[] getCompressedData() {
        return compressedData;
    }

    /**
     * Return the length of the bitstream. This is important because the last byte is padded with zeroes, but is no part of the encoding.
     * For example, the bitstream 10100000 with encoding length 4 returns two bytes (encoded by 10 and 10). The last 4 bits are padding.
     * @return the encoding length
     */
    public int getCompressedDataBitLength() {
        return compressedDataBitLength;
    }

    /**
     * Returns the root node of the Huffman tree.
     * @return the root node of the Huffman tree.
     */
    public Node getHuffmanTree() {
        return huffmanTree;
    }
}
