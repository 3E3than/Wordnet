package ngordnet.main;
import java.util.*;

public class Graph extends HashMap{
    //adjlist hashmap?
    // hashmap of id-words/word ids and hashmap of adjlist?
    private static HashMap<Integer, List<Integer>> AdjList;
    private HashMap<Integer, List<String>> IdWords;
    private HashMap<String, HashSet<Integer>> WordIds;

    public Graph() {
        AdjList = new HashMap();
        IdWords = new HashMap();
        WordIds = new HashMap();
    }
    public void mapIds() {
        for (int key : IdWords.keySet()) {
            for (String s : IdWords.get(key)) {
                if (!WordIds.containsKey(s)) {
                    HashSet<Integer> h = new HashSet();
                    WordIds.put(s, h);
                    WordIds.get(s).add(key);
                }
                WordIds.get(s).add(key);
            }
        }
    }
    public void addAdj(Integer key, List<Integer> val) {
        if (AdjList.containsKey(key)) {
            AdjList.get(key).addAll(val);
        } else {
            AdjList.put(key, val);
        }
    }
    public void addWords(Integer ID, List<String> words) {
        IdWords.put(ID, words);
    }
    // USE HASHSET, NO NEED CONTAINS
    public List<Integer> traverse(int key) {
        if (AdjList.get(key) == null) {
            return null;
        }
        HashSet<Integer> Hyponyms = new HashSet();
        Hyponyms.add(key);
        for (int id : AdjList.get(key)) {
            Hyponyms.add(id);
            tHelper(id, Hyponyms);
        }
        List<Integer> a = new ArrayList(Hyponyms);
        return a;
    }
    private void tHelper(int id, HashSet l) {
        if (AdjList.get(id) == null) {
            return;
        }
        for (int i : AdjList.get(id)) {
            if (AdjList.get(i) != null) {
                tHelper(i, l);
            }
            l.add(i);
        }
    }
    public List<String> getWord(int key) {
        if (IdWords.get(key) == null) {
            return null;
        }
        List<String> wordList = new ArrayList();
        for (String s : IdWords.get(key)) {
            wordList.add(s);
        }
        return wordList;
    }
    public List<String> getWords(List<Integer> l) {
        if (l == null) {
            return null;
        }
        List<String> wordList = new ArrayList();
        for (int i : l) {
            for (String s : IdWords.get(i)) {
                wordList.add(s);
            }
        }
        Collections.sort(wordList);
        return wordList;
    }

    public List<Integer> check_for_word(String s) {
        List<Integer> list = new ArrayList();
        for (int key : IdWords.keySet()) {
            if (IdWords.get(key).contains(s)) {
                list.add(key);
            }
        }
        return list;
    }
    public List<Integer> check_for_words(String s) {
        if (WordIds.get(s) == null) {
            List<Integer> empty = new ArrayList();
            return empty;
        }
        List<Integer> l = new ArrayList(WordIds.get(s));
        return l;
    }

}
