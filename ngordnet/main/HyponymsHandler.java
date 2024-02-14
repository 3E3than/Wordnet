package ngordnet.main;
import java.util.*;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap ngm;
    public HyponymsHandler(WordNet w, NGramMap n) {
        wn = w;
        ngm = n;
    }
    @Override
    public String handle(NgordnetQuery q) {
        int k = q.k();
        int startYear = q.startYear();
        int endYear = q.endYear();
        List<String> words = q.words();
        if (k == 0) {
            if (words.size() == 1) {
                return wn.basicCase(words.get(0)).toString();
            }
            return wn.listCase(words).toString();
        }
        if (words.size() == 1 && k != 0) {
            return handleK(startYear, endYear, wn.basicCase(words.get(0)), k).toString();
        }
        return handleK(startYear, endYear, wn.listCase(words), k).toString();
    }
    private List<String> handleK(int s, int e, List<String> w, int k) {
        HashMap<String, Double> totals = new HashMap();
        for (String str : w) {
            TimeSeries a = ngm.countHistory(str, s, e);
            double total = 0;
            for (Double i : a.values()) {
                total += i;
            }
            if (total != 0) {
                totals.put(str, total);
            }
        }
        if (k >= totals.keySet().size()) {
            List<String> result = new ArrayList(totals.keySet());
            Collections.sort(result);
            return result;
        }
        List val = new ArrayList(totals.values());
        Collections.sort(val);
        Collections.reverse(val);
        List<Double> largest = new ArrayList(k);
        for (int i = 0; i < k; i++) {
            largest.add((Double) val.get(i));
        }
        List result = new ArrayList(k);
        for (Double d : largest) {
            result.add(getKey(d, totals));
        }
        Collections.sort(result);
        return result;
    }
    private String getKey(Double d, HashMap<String, Double> m) {
        for (Map.Entry<String, Double> entry : m.entrySet()) {
            if (Objects.equals(d, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
