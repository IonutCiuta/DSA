package trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by John on 29.01.2017.
 */
public class Node {
    boolean complete;
    Map<Character, Node> parts;

    public Node() {
        this.complete = false;
        this.parts = new HashMap<>();
    }
}
