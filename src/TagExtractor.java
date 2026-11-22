import java.io.*;
import java.util.*;

public class TagExtractor {

    private TreeSet<String> stopWords;
    private TreeMap<String, Integer> tags;

    public TagExtractor() {
        stopWords = new TreeSet<>();
        tags = new TreeMap<>();
    }

    public void loadStopWords(File file) throws IOException {

        stopWords.clear();

        Scanner input = new Scanner(file);

        while (input.hasNextLine()) {

            String word = input.nextLine()
                    .trim()
                    .toLowerCase();

            if (!word.isEmpty()) {
                stopWords.add(word);
            }
        }

        input.close();
    }

    public void extractTags(File file) throws IOException {

        tags.clear();

        Scanner input = new Scanner(file);

        while (input.hasNext()) {

            String word = input.next();

            word = word.replaceAll("[^a-zA-Z]", "")
                    .toLowerCase();

            if (word.isEmpty()) {
                continue;
            }

            if (stopWords.contains(word)) {
                continue;
            }

            tags.put(word,
                    tags.getOrDefault(word, 0) + 1);
        }

        input.close();
    }

    public String getTagReport() {

        StringBuilder output = new StringBuilder();

        for (String word : tags.keySet()) {

            output.append(word)
                    .append(" : ")
                    .append(tags.get(word))
                    .append("\n");
        }

        return output.toString();
    }

    public void saveResults(File file)
            throws IOException {

        PrintWriter writer =
                new PrintWriter(file);

        writer.print(getTagReport());

        writer.close();
    }
}