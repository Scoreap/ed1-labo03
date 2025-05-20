package ed.lab;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

class Trie {
    Trie[] children = new Trie[27]; // 26 English letters and 1 space
    int frequency;
    String word = "";

    // Inserts a word into the trie with its associated frequency
    void insert(String word, int frequency) {
        Trie node = this;
        for (char ch : word.toCharArray()) {
            int index = ch == ' ' ? 26 : ch - 'a'; // Convert char to index, space represented by 26
            if (node.children[index] == null) {
                node.children[index] = new Trie();
            }
            node = node.children[index];
        }
        node.frequency += frequency; // Update the frequency of the node (word-end)
        node.word = word;
    }

    // Searches for a node with a given prefix in the trie
    Trie search(String prefix) {
        Trie node = this;
        for (char ch : prefix.toCharArray()) {
            int index = ch == ' ' ? 26 : ch - 'a';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }
}

public class E02AutocompleteSystem {
    private Trie rootTrie = new Trie(); // Trie root
    private StringBuilder currentInput = new StringBuilder();

    public E02AutocompleteSystem(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i++) {
            rootTrie.insert(sentences[i], times[i]);
        }
    }

    public List<String> input(char c) {
        List<String> autocompleteResult = new ArrayList<>();
        if (c == '#') {
            rootTrie.insert(currentInput.toString(), 1);
            currentInput = new StringBuilder();
            return autocompleteResult;
        }
        currentInput.append(c);
        Trie node = rootTrie.search(currentInput.toString());
        if (node == null) {
            return autocompleteResult;
        }
        PriorityQueue<Trie> minHeap
                = new PriorityQueue<>((a, b) -> a.frequency == b.frequency ? b.word.compareTo(a.word) : a.frequency - b.frequency);
        depthFirstSearch(node, minHeap);
        while (!minHeap.isEmpty()) {
            autocompleteResult.add(0, minHeap.poll().word);
        }
        return autocompleteResult;
    }

    private void depthFirstSearch(Trie node, PriorityQueue<Trie> minHeap) {
        if (node == null || minHeap.size() > 3 && node.frequency < minHeap.peek().frequency) {
            return;
        }
        if (node.frequency > 0) {
            minHeap.offer(node);
            if (minHeap.size() > 3) {
                minHeap.poll();
            }
        }
        for (Trie childNode : node.children) {
            depthFirstSearch(childNode, minHeap);
        }
    }
}
