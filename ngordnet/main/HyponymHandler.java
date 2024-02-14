package ngordnet.main;

import java.util.*;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

public class HyponymHandler extends NgordnetQueryHandler {
    private WordNet w;
    private NGramMap ng;
    public HyponymHandler(WordNet wn, NGramMap ngm) {
        w = wn;
        ng = ngm;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<Integer> l = new ArrayList();
        l.add(0);
        l.add(1);
        l.add(3);
        l.add(11);
        List<String> str = new ArrayList();
        str.add("occurrence");
        str.add("change");
        String response = "";
        List a = w.basicCase("change");
        List b = w.traverse(0);
        List c = w.listCase(str);
        return w.check_for_word("dash").toString();
    }
}
