package nl.craftsmen.huffman;

public class Node implements Comparable<Node> {

    private Node left;
    private Node right;
    private byte value;
    private int frequency;

    public Node(byte value, int frequency) {
        this.value = value;
        this.frequency = frequency;
    }

    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
        this.frequency = left.frequency + right.frequency;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public byte getValue() {
        return value;
    }

    public int getFrequency() {
        return frequency;
    }

    public int compareTo(Node otherNode) {
        if (this.frequency > otherNode.frequency) {
            return 1;
        } else if (this.frequency < otherNode.frequency) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "left=" + left +
                ", right=" + right +
                ", value=" + value +
                ", frequency=" + frequency +
                '}';
    }
}
