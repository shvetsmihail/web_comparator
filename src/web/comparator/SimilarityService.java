package web.comparator;

import java.util.*;

public class SimilarityService {
    public double calculateSimilarity(Map<String, Double> tf1, Map<String, Double> tf2) {
        Map<String, Double> tfIdf1 = getTfIdf(tf1, tf2);
        Map<String, Double> tfIdf2 = getTfIdf(tf2, tf1);
        return getSimilarity(new ArrayList<>(tfIdf1.values()), new ArrayList<>(tfIdf2.values()));

    }

    private Map<String, Double> getTfIdf(Map<String, Double> tf1, Map<String, Double> tf2) {
        Map<String, Double> tfIdf = new HashMap<>();
        for (Map.Entry<String, Double> entry : tf1.entrySet()) {
            double idf = getInverseDocFrequency(entry.getKey(), tf2.keySet());
            tfIdf.put(entry.getKey(), entry.getValue() * idf);
        }
        return tfIdf;
    }

    private double getInverseDocFrequency(String word, Set<String> anotherDoc) {
        int docCount = 2;
        int hasWordCount = anotherDoc.contains(word) ? 2 : 1;
        return Math.log(1.0 * docCount / hasWordCount);
    }

    private double getSimilarity(List<Double> a, List<Double> b) {
        double abSum = 0;
        double aSquaredSum = 0;
        double bSquaredSum = 0;

        for (int i = 0; i < a.size(); i++) {
            abSum = abSum + a.get(i) * b.get(i);
            aSquaredSum = aSquaredSum + a.get(i) * a.get(i);
            bSquaredSum = bSquaredSum + b.get(i) + b.get(i);
        }
        return abSum / (Math.sqrt(aSquaredSum) * Math.sqrt(bSquaredSum));
    }
}
