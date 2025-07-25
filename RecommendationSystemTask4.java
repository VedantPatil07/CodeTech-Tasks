import java.util.*;

class Item {
    String title;
    List<String> features;

    public Item(String title, List<String> features) {
        this.title = title;
        this.features = features;
    }

    // Convert to feature vector (binary 0/1)
    public int[] getFeatureVector(List<String> allFeatures) {
        int[] vector = new int[allFeatures.size()];
        for (int i = 0; i < allFeatures.size(); i++) {
            vector[i] = features.contains(allFeatures.get(i)) ? 1 : 0;
        }
        return vector;
    }
}

public class RecommendationSystem {

    // Cosine similarity formula
    public static double cosineSimilarity(int[] vec1, int[] vec2) {
        int dotProduct = 0;
        double normA = 0, normB = 0;

        for (int i = 0; i < vec1.length; i++) {
            dotProduct += vec1[i] * vec2[i];
            normA += vec1[i] * vec1[i];
            normB += vec2[i] * vec2[i];
        }

        if (normA == 0 || normB == 0) return 0;
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public static List<Item> recommendBasedOnInterest(List<String> userInterests, List<Item> items, List<String> allFeatures) {
        List<Item> recommendations = new ArrayList<>();

        // Create a dummy item from user interests
        Item virtualUserItem = new Item("User", userInterests);
        int[] userVector = virtualUserItem.getFeatureVector(allFeatures);

        // Calculate similarity
        Map<Item, Double> similarityMap = new HashMap<>();
        for (Item item : items) {
            int[] itemVector = item.getFeatureVector(allFeatures);
            double similarity = cosineSimilarity(userVector, itemVector);
            similarityMap.put(item, similarity);
        }

        // Sort and return top 3
        similarityMap.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .forEach(entry -> recommendations.add(entry.getKey()));

        return recommendations;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // All known features (you can expand this list)
        List<String> allFeatures = Arrays.asList("action", "sci-fi", "romance", "comedy", "drama", "robot", "marvel", "space", "thriller");

        // Item dataset
        List<Item> items = new ArrayList<>();
        items.add(new Item("Iron Man", Arrays.asList("action", "sci-fi", "robot", "marvel")));
        items.add(new Item("Avengers", Arrays.asList("action", "sci-fi", "marvel")));
        items.add(new Item("Interstellar", Arrays.asList("sci-fi", "drama", "space")));
        items.add(new Item("Deadpool", Arrays.asList("action", "comedy", "marvel")));
        items.add(new Item("Titanic", Arrays.asList("romance", "drama")));
        items.add(new Item("The Notebook", Arrays.asList("romance", "drama")));
        items.add(new Item("Mr. Bean", Arrays.asList("comedy")));
        items.add(new Item("Inception", Arrays.asList("sci-fi", "thriller", "action")));

        // Get user interests
        System.out.println("Enter your interests separated by commas (e.g., action,romance,sci-fi): ");
        String input = sc.nextLine();
        String[] interestsArray = input.toLowerCase().split(",");
        List<String> userInterests = new ArrayList<>();

        for (String interest : interestsArray) {
            interest = interest.trim();
            if (allFeatures.contains(interest)) {
                userInterests.add(interest);
            }
        }

        if (userInterests.isEmpty()) {
            System.out.println("No valid interests entered. Exiting.");
            return;
        }

        // Recommend
        List<Item> recommended = recommendBasedOnInterest(userInterests, items, allFeatures);

        System.out.println("\nBased on your interests, we recommend:");
        for (Item item : recommended) {
            System.out.println("- " + item.title);
        }
    }
}
