package fit.se.ml_models;

import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.datasets.iterator.utilty.ListDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author: vie
 * @date: 25/11/24
 */
@Slf4j
public class JobRecommendationModel {
   public static void main(String[] args) throws Exception {
      // Load data
      Map<String, Map<String, INDArray>> data = DataProcessor.loadData();
      Map<String, INDArray> candidateSkills = data.get("candidates");
      Map<String, INDArray> jobSkills = data.get("jobs");

      // Process data into training pairs
      INDArray userSkillMatrix = normalizeMatrix(Nd4j.vstack(candidateSkills.values().toArray(new INDArray[0])));
      INDArray jobSkillMatrix = normalizeMatrix(Nd4j.vstack(jobSkills.values().toArray(new INDArray[0])));

      // Determine the target number of rows (max rows of the two matrices)
      int maxRows = Math.max(userSkillMatrix.rows(), jobSkillMatrix.rows());
      // Pad matrices to have the same number of rows
      INDArray paddedUserSkillMatrix = padMatrix(userSkillMatrix, maxRows);
      INDArray paddedJobSkillMatrix = padMatrix(jobSkillMatrix, maxRows);

      // Split data into train and test sets
      DataSet fullDataSet = new DataSet(paddedUserSkillMatrix, paddedJobSkillMatrix);
      List<DataSet> splitData = fullDataSet.asList();
      int trainSize = (int) (splitData.size() * 0.8);
      DataSetIterator trainIter = new ListDataSetIterator<>(splitData.subList(0, trainSize));
      DataSetIterator testIter = new ListDataSetIterator<>(splitData.subList(trainSize, splitData.size()));

      // Build neural network
      MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
            .seed(123)
            .updater(new Adam(0.0005)) // Lower learning rate
            .weightInit(WeightInit.XAVIER)
            .l2(1e-4)
            .list()
            .layer(0, new DenseLayer.Builder()
                  .nIn(userSkillMatrix.columns())
                  .nOut(256) // Increase neurons for better learning
                  .activation(Activation.RELU)
                  .dropOut(0.3) // Reduce dropout
                  .build())
            .layer(1, new DenseLayer.Builder()
                  .nIn(256)
                  .nOut(128)
                  .activation(Activation.RELU)
                  .build())
            .layer(2, new DenseLayer.Builder()
                  .nIn(128)
                  .nOut(64)
                  .activation(Activation.RELU)
                  .build())
            .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.COSINE_PROXIMITY)
                  .nIn(64)
                  .nOut(jobSkillMatrix.columns())
                  .activation(Activation.SIGMOID)
                  .build())
            .build();

      MultiLayerNetwork model = new MultiLayerNetwork(config);
      model.init();
      model.setListeners(new ScoreIterationListener(10));

      // Train model
      for (int epoch = 0; epoch < 50; epoch++) {
         model.fit(trainIter);
         log.info("Epoch {} completed", epoch);
      }

      // Evaluate model
      double testScore = model.score(testIter.next());
      log.info("Test Score: {}", testScore);

      // Save model
      model.save(new File("job_recommendation_model.zip"), true);
      log.info("Model training completed and saved to job_recommendation_model.zip");
   }

   private static INDArray padMatrix(INDArray matrix, int targetRows) {
      int currentRows = matrix.rows();
      int columns = matrix.columns();

      if (currentRows == targetRows) {
         return matrix; // No padding needed
      }

      // Create padding rows with zeros
      INDArray padding = Nd4j.zeros(targetRows - currentRows, columns);

      // Append padding rows to the original matrix
      return Nd4j.vstack(matrix, padding);
   }

   private static INDArray normalizeMatrix(INDArray matrix) {
      // Normalize data between 0 and 1
      INDArray max = matrix.max(0);
      INDArray min = matrix.min(0);
      return matrix.subRowVector(min).divRowVector(max.sub(min));
   }
}
