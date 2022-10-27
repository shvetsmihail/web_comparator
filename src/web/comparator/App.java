package web.comparator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class App {

    public static void main(String[] args) throws MalformedURLException {

        WebResourceService webResourceService = new WebResourceService();
        SimilarityService similarityService = new SimilarityService();

        URL url1 = new URL("https://en.wikipedia.org/wiki/Java_(programming_language)");
        Map<String, Double> wordsFrequency1 = webResourceService.getUniqueWordsFrequency(url1, 200);

        URL url2 = new URL("https://en.wikipedia.org/wiki/Object-oriented_programming");
        Map<String, Double> wordsFrequency2 = webResourceService.getUniqueWordsFrequency(url2, 200);

        URL url3 = new URL("https://en.wikipedia.org/wiki/Ukraine");
        Map<String, Double> wordsFrequency3 = webResourceService.getUniqueWordsFrequency(url3, 200);

        System.out.println(similarityService.calculateSimilarity(wordsFrequency1, wordsFrequency2));
        System.out.println(similarityService.calculateSimilarity(wordsFrequency1, wordsFrequency3));
    }
}
