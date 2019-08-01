package nl.craftsmen.huffman;

public class Node implements Comparable<Node> {

    private Node left;

    private Node right;

    private byte value;

    private final int frequency;

    Node(byte value, int frequency) {
        this.value = value;
        this.frequency = frequency;
    }

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

    public int compareTo(Node otherNode) {
        return Integer.compare(this.frequency, otherNode.frequency);
    }


    @Override
    public String toString() {
        return "Node{" + "left=" + left + ", right=" + right + ", value=" + value + ", frequency=" + frequency + '}';
    }
}
