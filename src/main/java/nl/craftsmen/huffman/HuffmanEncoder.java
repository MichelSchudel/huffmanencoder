package nl.craftsmen.huffman;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

class HuffmanEncoder {

    private HuffmanEncoder() {

    }

    /**
     * Compresses a byte array to a compressed array.
     *
     * @param data the byte array.
     * @return an encoding result containing the compressed byte array, along with the Huffman tree needed for decoding.
     */
    static EncodingResult compress(byte[] data) {

        // create a map that maps each occuring byte value to its frequency in the array
        final Map<Byte, Integer> frequencyMap = HuffmanEncoder.createFrequencyMap(data);

        //create a priority queue, with a node for each entry in the map
        final PriorityQueue<Node> initialTreeElements = HuffmanEncoder.createInitialTreeElements(frequencyMap);

        //construct the Huffman tree, see the algorithm in the readme for an explanation.
        Node huffmanTreeRootNode = HuffmanEncoder.createTree(initialTreeElements);

        //create a map that maps each occuring byte value to its Huffman code, using the Huffman tree.
        Map<Byte, String> symbolToCodeMap = HuffmanEncoder.createSymbolToCodeMap(huffmanTreeRootNode);

        //encode the data using the symbol-to-code map
        String compressedDataBitString = HuffmanEncoder.encodeToBitString(data, symbolToCodeMap);

        int compressedDataBitLength = compressedDataBitString.length();

        //convert the binary string to a byte array
        Byte[] encodedArray = HuffmanEncoder.convertBinaryStringToByteArray(compressedDataBitString);

        //store all information in a handy wrapper
        return new EncodingResult(encodedArray, compressedDataBitLength, huffmanTreeRootNode);
    }

    /**
     * Giving an array of bytes, creates a map where the keys are the unique byte values in the array, and the value the number of occurences of that byte in the array.
     * For example , the array [0, 0, 1, 2, 1] will result in a map with key value pairs: [{0,2}, {1,2}, {2,1}]
     *
     * @param input the byte array
     * @return a map containing the number of occurences in the array for each unique byte value.
     */
    private static Map<Byte, Integer> createFrequencyMap(byte[] input) {
        Map<Byte, Integer> map = new HashMap<>();
        for (byte currentByteValue : input) {
            map.putIfAbsent(currentByteValue, 0);
            map.compute(currentByteValue, (byteValue, value) -> value + 1);
        }
        return map;
    }

    /**
     * For each entry in the byteFrequencyMap, create a Node object containing the byte value and frequency,
     * and put that node in a PriorityQueue.
     * The natural ordering of Nodes is determined by the frequency value. A node is "less" than another node when its frequency is higher
     * than the frequency of the other node.
     * <p>
     * For example, the map [{0,2}, {1,2}, {2,1}] will result in three Node objects:
     * node1.value=0
     * node1.frequency=2
     * node2.value=1
     * node2.frequency=2
     * node3.value=2
     * node3.frequency=1
     *
     * @param byteFrequencyMap the map of key-value pairs of byte value and frequency.
     * @return a priorityQueue containing a node object for each entry in the map.
     */
    private static PriorityQueue<Node> createInitialTreeElements(Map<Byte, Integer> byteFrequencyMap) {
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>();
        byteFrequencyMap.entrySet()
                .stream()
                .map(entry -> new Node(entry.getKey(), entry.getValue()))
                .forEach(nodePriorityQueue::add);
        return nodePriorityQueue;
    }

    /**
     * Creates a Huffman tree based on the Node elements in the priority queue.
     * Algorithm:
     * Take the two nodes with the lowest frequency from the queue.
     * Build a new node pointing to those nodes, with a frequency that is the sum of the frequencies of both child nodes
     * Put the new node into the queue.
     * Repat until there is only one node element left in the queue.
     *
     * @param nodePriorityQueue the priority queue
     * @return the root node of the resulting Huffman tree.
     */
    private static Node createTree(PriorityQueue<Node> nodePriorityQueue) {
        while (nodePriorityQueue.size() >= 2) {
            Node node1 = nodePriorityQueue.poll();
            Node node2 = nodePriorityQueue.poll();
            Node resultNode = new Node(node2, node1);
            nodePriorityQueue.add(resultNode);
        }
        return nodePriorityQueue.poll();
    }

    /**
     * Builds a map where the key is a byte value and the value is a Huffman binary code representing that byte value.
     *
     * @param rootNode the root rootNode of the Huffman tree.
     * @return a map that maps a byte value to a binary code, for example [{56, "10"}]
     */
    private static Map<Byte, String> createSymbolToCodeMap(Node rootNode) {
        return createSymbolToCodeMap(rootNode, new HashMap<>(), "");
    }

    private static Map<Byte, String> createSymbolToCodeMap(Node node, Map<Byte, String> map, String path) {
        if (node.getLeft() != null) {
            String newPath = path + "0";
            createSymbolToCodeMap(node.getLeft(), map, newPath);
        }
        if (node.getRight() != null) {
            String newPath = path + "1";
            createSymbolToCodeMap(node.getRight(), map, newPath);
        } else {
            map.put(node.getValue(), path);
        }
        return map;
    }

    /**
     * Compresses a string to a sequence of bits by looking up each byte in the huffman tree.
     *
     * @param data the data
     * @param map  a mapping from each byte value to the Huffman coding in bits.
     * @return a string respresenting a sequence of bits, like "0111011011011001"
     */
    private static String encodeToBitString(final byte[] data, final Map<Byte, String> map) {
        StringBuilder encodedStringBuilder = new StringBuilder();
        for (byte b : data) {
            encodedStringBuilder.append(map.get(b));
        }
        return encodedStringBuilder.toString();

    }

    /**
     * Converts a string respresenting a sequence of bits into a byte array.
     * For example, the string 0000001100000100 converts to [3,4].
     *
     * @return the byte array.
     */
    private static Byte[] convertBinaryStringToByteArray(final String encodedString) {
        int rest = encodedString.length() % 8;
        final String paddedString = encodedString + "0".repeat(8 - rest);
        String[] split = paddedString.split("(?<=\\G.{8})");
        return Arrays.stream(split)
                .map(bits -> (byte) Integer.parseInt(bits, 2))
                .toArray(Byte[]::new);
    }

    /**
     * Decompresses the Huffman-encoded byte array using the Huffman tree and the length of the compressed bitstream.
     *
     * @param encodingResult the encoding result
     * @return the decompressed
     */
    static byte[] decompress(EncodingResult encodingResult) {

        //convert the compressed byte array to a bit string
        String paddedCompressedDataBitString = convertByteArrayToBinaryString(encodingResult.getCompressedData());
        String compressedDataBitString = paddedCompressedDataBitString.substring(0, encodingResult.getCompressedDataBitLength());

        //decode the bit string
        return decode(encodingResult.getHuffmanTree(), compressedDataBitString);
    }

    /**
     * Convert byte array into binary String. For example, [3,4] yields "0000001100000100".
     *
     * @param compressedData the compressed data.
     * @return the binary string.
     */
    private static String convertByteArrayToBinaryString(final Byte[] compressedData) {
        StringBuilder stringBuilder = new StringBuilder(compressedData.length * 2);
        List<String> bytes =  Arrays.stream(compressedData)
                .map(b -> String.format("%8s", Integer.toBinaryString(b & 0xFF))
                        .replace(' ', '0')).collect(Collectors.toList());
        bytes.forEach(e -> stringBuilder.append(e));
        return stringBuilder.toString();
    }

    /**
     * Decompresses the trimmed binary string back to the original using the huffman tree.
     *
     * @param huffmanRootNode the root node of the huffman tree.
     * @param compressedDataBitString the binary string representing a compressed huffman encoding.
     * @return the original, decompressed, data.
     */
    private static byte[] decode(final Node huffmanRootNode, final String compressedDataBitString) {
        Node node = huffmanRootNode;
        ByteArrayOutputStream byteArrayOutputStreams = new ByteArrayOutputStream();

        //traverse the Huffman tree for each bit in the bit string, stop when a leaf node is found.
        //the value of this node is an original byte value.
        //repeat until the end of the compressed data bit string is reached.
        for (int i = 0; i < compressedDataBitString.length(); i++) {
            if (compressedDataBitString.charAt(i) == '0') {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
            if (node.getRight() == null && node.getLeft() == null) {
                byteArrayOutputStreams.write(node.getValue());
                node = huffmanRootNode;
            }
        }
        return byteArrayOutputStreams.toByteArray();
    }
}
