package nl.craftsmen.huffman;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoder {

    /**
     * Giving an array of bytes, creates a map where the kys are the unique byte values in the array, and the value the number of occurences of that byte in the array.
     * For example , the array [0, 0, 1, 2, 1] will result in a map with key value pairs: [{0,2}, {1,2}, {2,1}]
     * @param input the byte array
     * @return a map containing the number of occurences in the array for each unique byte value.
     */
    static Map<Byte, Integer> createFrequencyMap(byte[] input) {
        Map<Byte, Integer> map = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            byte currentByteValue = input[i];
            map.putIfAbsent(currentByteValue, 0);
            map.compute(currentByteValue, (byteValue, value) -> value + 1);
        }
        return map;
    }

    /**
     * For each entry in the byteFrequencyMap, create a Node object containing the byte value and frequency,
     * and put that node in a PriorityQueue.
     * The natural ordering of Nodes is determind by the frequency value. A node is "less" than another node when its frequency is higher
     * than the frequency of the other node.
     *
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
    static PriorityQueue<Node> createInitialTreeElements(Map<Byte, Integer> byteFrequencyMap) {
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
    static Node createTree(PriorityQueue<Node> nodePriorityQueue) {
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
     * @param rootNode the root rootNode of the Huffman tree.
     * @return
     */
    static Map<Byte, String> createSymbolToCodeMap(Node rootNode) {
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




    public static String encode(final String testString, final Map<Byte, String> map) {
        String encodedString = "";
        for (int i = 0; i < testString.length(); i++) {
            byte b = (byte) testString.charAt(i);
            encodedString = encodedString + map.get(b);
        }
        return encodedString;

    }

    public static Byte[] toByteArray(final String encodedString) {
        int rest = encodedString.length() % 8;
        final String paddedString = encodedString + "0".repeat(8 - rest);
        String[] split = paddedString.split("(?<=\\G.{8})");
        Byte[] encodedBytes = Arrays.stream(split)
                .map(bits -> (byte)Integer.parseInt(bits, 2))
                .toArray(n -> new Byte[n]);
        return encodedBytes;
    }

    public static String decode(final Byte[] encodedArray, final Node rootNode, final int stringLength) {
        Node node = rootNode;
        String decodedString = "";
        String binaryString = Arrays.stream(encodedArray)
                .map(b -> String.format("%8s", Integer.toBinaryString(b & 0xFF))
                        .replace(' ', '0'))
                .reduce("", (accumulatedString, newCharacter) -> accumulatedString + newCharacter);
        String trimmedBinaryString = binaryString.substring(0, stringLength);
        for (int i = 0; i < trimmedBinaryString.length(); i++) {
            if (trimmedBinaryString.charAt(i) == '0') {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
            if (node.getRight() == null && node.getLeft() == null) {
                decodedString = decodedString + (char) node.getValue();
                node = rootNode;
            }
        }
        return decodedString;
    }
}
