package trie;

/**
 * Created by John on 29.01.2017.
 */
public class Test {
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.chain(trie.root, "mancare".toCharArray(), 0);
        trie.print();
    }
}
