package trie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 29.01.2017.
 */
public class Trie {
    Node root;

    public Trie() {
        root = new Node();
    }

    public void chain(Node hook, char[] letters, int index) {
        if(index != letters.length) {
            Node link = new Node();
            hook.parts.put(letters[index], link);
            chain(link, letters, ++index);
        } else {
            hook.complete = true;
        }
    }

    public void print() {
        List<String> words = new ArrayList<>();
        getWords(root, "", words);
        for(String word : words) {
            System.out.println(word);
        }
    }

    public void getWords(Node n, String buffer, List<String> words) {
        if(n.complete) {
            words.add(buffer);
        }

        for(char letter : n.parts.keySet()) {
            Node currentNode = n.parts.get(letter);
            if(currentNode != null) {
                getWords(currentNode, buffer + letter, words);
            }
        }
    }
}
