package web.comparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class WebResourceService {
    private static final String TAG_REGEX = "<(?:\"[^\"]*\"['\"]*|'[^']*'['\"]*|[^'\">])+>";
    private static final String PUNCT_REGEX = "\\p{Punct}";

    public Map<String, Double> getUniqueWordsFrequency(URL url, int topCount) {
        Map<String, Integer> uniqueWords = getUniqueWords(url);
        int totalCount = uniqueWords.values().stream().mapToInt(Integer::intValue).sum();
        List<Map.Entry<String, Integer>> topWords = getTopWords(topCount, uniqueWords);

        Map<String, Double> topWordsFrequency = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : topWords) {
            topWordsFrequency.put(entry.getKey(), (double) entry.getValue() / totalCount);
        }
        return topWordsFrequency;

    }

    private List<Map.Entry<String, Integer>> getTopWords(int topCount, Map<String, Integer> uniqueWords) {
        List<Map.Entry<String, Integer>> listEntry = new ArrayList<>(uniqueWords.entrySet());
        listEntry.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        int size = Math.min(topCount, listEntry.size());
        return listEntry.subList(0, size);
    }

    private Map<String, Integer> getUniqueWords(URL url) {
        Map<String, Integer> uniqueWords = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll(TAG_REGEX, "");
                line = line.replaceAll(PUNCT_REGEX, "");
                String[] words = line.split(" ");
                for (String word : words) {
                    if (word.length() > 3) {
                        String lowerCase = word.toLowerCase();
                        int count = uniqueWords.getOrDefault(lowerCase, 0) + 1;
                        uniqueWords.put(lowerCase, count);
                    }
                }
            }
        } catch (IOException e) {
            return Collections.emptyMap();
        }
        return uniqueWords;
    }
}
