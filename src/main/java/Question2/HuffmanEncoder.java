package Question2;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class HuffmanEncoder {

    public static Map<Character, Integer> countCharacterInstances(char[] input) {
        Map<Character, Integer> countMap = new LinkedHashMap<>();
        for(char character : input) {
            if(!countMap.containsKey(character)) countMap.put(character, 1);
            else countMap.put(character, countMap.get(character)+1);
        }

        return countMap;
    }

    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> map) {

        PriorityQueue priorityQueue = new PriorityQueue();

        Iterator<Character> keySet = map.keySet().iterator();
        while(keySet.hasNext()) {
            Character key = keySet.next();
            priorityQueue.enqueue(new HuffmanNode(key, map.get(key)));
        }

        while(priorityQueue.size() > 1) {
            HuffmanNode parent = new HuffmanNode();
            HuffmanNode x = priorityQueue.dequeue();
            HuffmanNode y = priorityQueue.dequeue();
            parent.attachLeftChild(x);
            parent.attachRightChild(y);
            parent.setFrequency(x.getFrequency() + y.getFrequency());
            priorityQueue.enqueue(parent);
        }

        return priorityQueue.dequeue();
    }

    public static Map<Character, String> extractCodeMap(HuffmanNode root) {

        Map<Character, String> map = new LinkedHashMap<>();
        codeMapGenerator(map, root, "");

        return map;
    }

//  find code by using depth first search
    private static void codeMapGenerator(Map<Character, String> map, HuffmanNode node, String code) {
        if(node.isLeaf()) {
            map.put(node.getSymbol(), code);
            return;
        }
        if(node.getLeftChild() != null) codeMapGenerator(map, node.getLeftChild(), code + "0");
        if(node.getRightChild() != null) codeMapGenerator(map, node.getRightChild(), code + "1");
    }

    public static String huffmanEncode(String input, Map<Character, String> codeMap) {
        String encode = "";
        char[] characters = input.toCharArray();
        for(char character : characters) {
            encode += codeMap.get(character);
        }
        return encode;
    }

    public static String huffmanDecode(HuffmanNode root, String code) {
        String decode = "";
        char[] characters = code.toCharArray();
        HuffmanNode loopNode = root;
        for(char character : characters) {
            if(Character.compare(character, '1') == 0) loopNode = loopNode.getRightChild();
            else loopNode = loopNode.getLeftChild();
            if(loopNode.isLeaf()) {
                decode += loopNode.getSymbol();
                loopNode = root;
            }
        }

        return decode;
    }

}
