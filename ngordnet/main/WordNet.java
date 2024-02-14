package ngordnet.main;
import java.util.*;
import edu.princeton.cs.algs4.In;

public class WordNet {
    private Graph graph;

    public WordNet(String hyponymsFile, String synsetsFile) {
        graph = new Graph();
        In synsets = new In(synsetsFile);
        In hyponyms = new In(hyponymsFile);
        while (!hyponyms.isEmpty()) {
            String[] line = hyponyms.readLine().split(",");
            int wordID = Integer.parseInt(line[0]);
            List<Integer> hypoList = new ArrayList();
            for (int index = 1; index < line.length; index ++) {
                hypoList.add(Integer.parseInt(line[index]));
            }
            graph.addAdj(wordID, hypoList);
        }
        while (!synsets.isEmpty()) {
            String[] line = synsets.readLine().split(",");
            Integer ID = Integer.parseInt(line[0]);
            String[] words = line[1].split(" ");
            List<String> Synonyms = new ArrayList();
            for (int index = 0; index < words.length; index ++) {
                Synonyms.add(words[index]);
            }
            graph.addWords(ID, Synonyms);
        }
        graph.mapIds();
    }
    public List<Integer> traverse(int key) {
        return graph.traverse(key);
    }
    public List<String> getWord(int i) {
        return graph.getWord(i);
    }
    public List<String> getWords(List<Integer> l) {
        return graph.getWords(l);
    }
    public List<Integer> check_for_word(String s) {
        return graph.check_for_word(s);
    }
    public List<Integer> check_for_words(String s) {
        return graph.check_for_words(s);
    }
    public List<String> basicCase(String s) {
        List<String> l = new ArrayList();
        for (int i : check_for_words(s)) {
            l.addAll(getWord(i));
            List<Integer> tInt = traverse(i);
            if (getWords(tInt) != null) {
                l.addAll(getWords(tInt));
            }
        }
        List<String> a = removeDuplicate(l);
        Collections.sort(a);
        return a;
    }
    public List<String> listCase(List<String> s) {
        HashMap<String, Integer> hm = new HashMap<>();
        List<String> dupes = new ArrayList();
        int size = s.size();
        for (int i = 0; i < size; i++) {
            List<String> l = basicCase(s.get(i));
            HashSet<String> hs = new HashSet();
            for (String str : l) {
                if (!hs.contains(str)) {
                    hs.add(str);
                    hm.put(str, hm.getOrDefault(str, 0) + 1);
                }
            }
        }
        for (String key : hm.keySet()) {
            if (hm.get(key) == size) {
                dupes.add(key);
            }
        }
        Collections.sort(dupes);
        return dupes;
    }
    private List<String> removeDuplicate(List l) {
        Set<String> set = new HashSet(l);
        List<String> list = new ArrayList();
        list.addAll(set);
        return list;
    }
}

