package com.stardust.tool;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by Stardust on 2016/7/9.
 */
public class TrieTree {

    private static final char END_OF_WORD = 0xffff;
    private static final char ROOT = 0xffff;

    private TrieNode root;

    public TrieTree() {
        root = new TrieNode(ROOT);
    }

    public void add(String word) {
        put(word, null);
    }

    public void put(String word, Object tag) {
        TrieNode node = root;
        TrieNode parent;
        for (int i = 0; i < word.length(); i++) {
            parent = node;
            node = node.find(word.charAt(i), false);
            if (node == null) {
                node = parent;
                for (int j = i; j < word.length(); j++) {
                    TrieNode subtree = new TrieNode(word.charAt(j));
                    node.subtrees.add(subtree);
                    node = subtree;
                }
                break;
            }
        }
        TrieNode endNode = node.find(END_OF_WORD, false);
        if (endNode == null) {
            node.subtrees.add(new EndTrieNode(tag));
        } else {
            ((EndTrieNode) endNode).tag = tag;
        }
    }

    public boolean searchWord(String word, boolean ignoreCase) {
        return searchWordInternal(word, ignoreCase) != null;
    }

    public boolean searchPrefix(String prefix, boolean ignoreCase) {
        return searchPrefixInternal(prefix, ignoreCase) != null;
    }

    public List<Pair<String, Object>> searchCompletion(String prefix, boolean ignoreCase) {
        TrieNode node = searchPrefixInternal(prefix, ignoreCase);
        if (node == null)
            return null;
        List<Pair<String, Object>> list = new LinkedList<>();
        node.searchEndOfWord(list, prefix);
        return list;
    }


    private TrieNode searchPrefixInternal(String prefix, boolean ignoreCase) {
        TrieNode node = root;
        char[] chars = prefix.toCharArray();
        for (char ch : chars) {
            node = node.find(ch, ignoreCase);
            if (node == null)
                return null;
        }
        return node;
    }

    private TrieNode searchWordInternal(String word, boolean ignoreCase) {
        return searchPrefixInternal(word, ignoreCase).find(END_OF_WORD, ignoreCase);
    }


    public class TrieNode {
        public List<TrieNode> subtrees;
        public char letter;

        public TrieNode(char letter) {
            this.letter = letter;
            subtrees = new LinkedList<>();
        }

        public TrieNode find(char letter, boolean ignoreCase) {
            for (TrieNode node : subtrees) {
                if (node.letter == letter || (ignoreCase && Character.toLowerCase(letter) == Character.toLowerCase(node.letter)))
                    return node;
            }
            return null;
        }

        public void searchEndOfWord(List<Pair<String, Object>> list, String str) {
            for (TrieNode node : subtrees) {
                node.searchEndOfWord(list, str + node.letter);
            }
        }
    }

    public class EndTrieNode extends TrieNode {
        public Object tag;

        public EndTrieNode() {
            super(END_OF_WORD);
        }

        public EndTrieNode(Object tag) {
            super(END_OF_WORD);
            this.tag = tag;
        }

        @Override
        public void searchEndOfWord(List<Pair<String, Object>> list, String str) {
            list.add(new Pair<>(str.substring(0, str.length() - 1), tag));
        }
    }
}
