import org.apache.mahout.cf.taste.eval.*;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.*;
import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;

import java.io.File;
import java.util.List;

public class RecommendationSystem {

    public static void main(String[] args) {
        try {
            // Load dataset
            String filePath = "data/dataset.csv"; // Replace with your dataset path
            DataModel dataModel = new FileDataModel(new File(filePath));

            // Define similarity (Item-based similarity)
            ItemSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);

            // Define neighborhood (optional for user-based recommender)
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, dataModel);

            // Create recommender
            Recommender recommender = new GenericItemBasedRecommender(dataModel, similarity);

            // Generate recommendations for a user
            int userId = 1; // Replace with an actual user ID from your dataset
            List<RecommendedItem> recommendations = recommender.recommend(userId, 5);

            // Display recommendations
            System.out.println("Recommendations for user ID " + userId + ":");
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Item ID: " + recommendation.getItemID() + ", Value: " + recommendation.getValue());
            }

            // Evaluate the model (optional)
            RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
            double score = evaluator.evaluate(recommender, null, dataModel, 0.7, 1.0);
            System.out.println("Evaluation Score: " + score);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
