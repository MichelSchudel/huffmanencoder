package nl.craftsmen.huffman;

/**
 * Implements a node in the Huffman tree. A node is either:
 * <ul>
 *     <li>A leaf, having a byte value and a frequency of occurence</li>
 *     <li>An intermediate or root node, have a left and a right node, and a frequency which is the sum of the frequencies of the left and right nodes.</li>
 * </ul>
 */

public class Node implements Comparable<Node> {

    private Node left;

    private Node right;

    private byte value;

    private final int frequency;

    /**
     * Construct a leaf node.
     * @param value the value
     * @param frequency the frequency.
     */
    Node(byte value, int frequency) {
        this.value = value;
        this.frequency = frequency;
    }

    /**
     * Constructs an intermediate node.
     * @param left leftNode
     * @param right rightNode
     */
    Node(Node left, Node right) {
        this.left = left;
        this.right = right;
        this.frequency = left.frequency + right.frequency;
    }

    Node getLeft() {
        return left;
    }

    Node getRight() {
        return right;
    }

    byte getValue() {
        return value;
    }

    /**
     * Node ordering is on frequency. Node A is greater than node B if its frequency is greater than that of node B.
     * @param otherNode nobe B.
     * @return -1, 0, or 1 if this node's frequency is less, equal, or greater than the frequency of the other node.
     */
    public int compareTo(Node otherNode) {
        return Integer.compare(this.frequency, otherNode.frequency);
    }

    @Override
    public String toString() {
        return "Node{" + "left=" + left + ", right=" + right + ", value=" + value + ", frequency=" + frequency + '}';
    }
}
