package nl.craftsmen.huffman;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;

public class HuffmanEncoderTest {

    @Test
    public void testPriorityQueue() {
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>();
        Node node1 = new Node((byte)'a', 10);
        Node node2 = new Node((byte)'a', 5);
        Node node3 = new Node((byte)'a', 7);
        nodePriorityQueue.add(node3);
        nodePriorityQueue.add(node2);
        nodePriorityQueue.add(node1);

        assertThat(nodePriorityQueue.poll().getFrequency()).isEqualTo(5);
        assertThat(nodePriorityQueue.poll().getFrequency()).isEqualTo(7);
        assertThat(nodePriorityQueue.poll().getFrequency()).isEqualTo(10);
    }

    @Test
    public void testString() {
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>();
        Map<Byte, Integer> map = new HashMap<>();
        String testString = "abaaabac";
        byte[] bytes = testString.getBytes();
        System.out.println(Arrays.toString(bytes));
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            map.putIfAbsent(b, 0);
            map.compute(b, (byteValue, value) -> value + 1);
        }
        map.entrySet().stream().map(entry-> new Node(entry.getKey(), entry.getValue())).forEach(nodePriorityQueue::add);
        assertThat(nodePriorityQueue.poll().getFrequency()).isEqualTo(1);
        assertThat(nodePriorityQueue.poll().getFrequency()).isEqualTo(2);
        assertThat(nodePriorityQueue.poll().getFrequency()).isEqualTo(5);
    }

    @Test
    public void testTree() {
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
