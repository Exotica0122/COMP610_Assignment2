package Question2;

public class HuffmanNode implements Comparable<HuffmanNode> {
    private Character symbol;
    private int frequency;
    private HuffmanNode leftChild;
    private HuffmanNode rightChild;


    public HuffmanNode() {
        this.leftChild = null;
        this.rightChild = null;
    }
    public HuffmanNode(Character symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
        this.leftChild = null;
        this.rightChild = null;
    }

    public HuffmanNode(Character symbol) {
        this.symbol = symbol;
        this.frequency = 0;
        this.leftChild = null;
        this.rightChild = null;
    }

    public void setFrequency(int frequency) { this.frequency = frequency; }

    public int getFrequency() { return frequency; }

    public Character getSymbol() { return symbol; }

    public boolean isLeaf() { return (rightChild == null && leftChild == null); }

    public boolean detachLeftChild() {
        if(leftChild == null) return false;
        this.leftChild = null;
        return true;
    }

    public boolean detachrightChild() {
        if(rightChild == null) return false;
        this.rightChild = null;
        return true;
    }

    public boolean attachLeftChild(HuffmanNode child) {
        if(leftChild != null) return false;

        this.leftChild = child;
        return true;
    }

    public boolean attachRightChild(HuffmanNode child) {
        if(rightChild != null) return false;

        this.rightChild = child;
        return true;
    }

    public HuffmanNode getLeftChild() { return leftChild; }

    public HuffmanNode getRightChild() { return rightChild; }

    @Override
    public int compareTo(HuffmanNode o) {
        return Integer.compare(frequency, o.frequency);
    }
}
