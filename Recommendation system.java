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
            String filePath = "data/dataset.csv";
            DataModel dataModel = new FileDataModel(new File(filePath));

            ItemSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);

            UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, dataModel);

            Recommender recommender = new GenericItemBasedRecommender(dataModel, similarity);

            int userId = 1;
            List<RecommendedItem> recommendations = recommender.recommend(userId, 5);

            System.out.println("Recommendations for user ID " + userId + ":");
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Item ID: " + recommendation.getItemID() + ", Value: " + recommendation.getValue());
            }

            RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
            double score = evaluator.evaluate(recommender, null, dataModel, 0.7, 1.0);
            System.out.println("Evaluation Score: " + score);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

Datasets
    1,101,4.0
    1,102,3.5
    2,101,5.0
    2,103,4.5
    3,104,2.0
    3,105,3.0
    4,101,4.5
    4,104,3.5

