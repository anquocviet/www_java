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
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author: vie
 * @date: 1/12/24
 */
@Slf4j
public class CandidateRecommendationModel {
   public static void main(String[] args) throws Exception {
      // Load data
      Map<String, Map<String, INDArray>> data = DataProcessor.loadData();
      Map<String, INDArray> candidateSkills = data.get("candidates");
      Map<String, INDArray> jobSkills = data.get("jobs");

      // Process data into training pairs
      List<DataSet> dataSets = new ArrayList<>();
      for (String jobId : jobSkills.keySet()) {
         INDArray jobFeature = padFeatures(jobSkills.get(jobId), 100);

         for (String candidateId : candidateSkills.keySet()) {
            INDArray candidateFeature = padFeatures(candidateSkills.get(candidateId), 100);

            // Label: 1 nếu phù hợp, 0 nếu không phù hợp
            double label = calculateLabel(jobFeature, candidateFeature);

            // Combine job and candidate features
            INDArray input = Nd4j.hstack(jobFeature, candidateFeature); // [1 x 200]
            INDArray output = Nd4j.create(new double[]{label}, new int[]{1, 1});

            dataSets.add(new DataSet(input, output));
         }
      }

      // Shuffle data and split into train/test
      Collections.shuffle(dataSets);
      int trainSize = (int) (dataSets.size() * 0.8);
      DataSetIterator trainIter = new ListDataSetIterator<>(dataSets.subList(0, trainSize));
      DataSetIterator testIter = new ListDataSetIterator<>(dataSets.subList(trainSize, dataSets.size()));

      // Define neural network
      MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
            .seed(123)
            .updater(new Adam(0.001)) // Optimizer
            .weightInit(WeightInit.XAVIER)
            .list()
            .layer(0, new DenseLayer.Builder()
                  .nIn(200) // 100 (job skills) + 100 (candidate skills)
                  .nOut(128) // Hidden layer size
                  .activation(Activation.RELU)
                  .build())
            .layer(1, new DenseLayer.Builder()
                  .nIn(128)
                  .nOut(64)
                  .activation(Activation.RELU)
                  .build())
            .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.XENT)
                  .nIn(64)
                  .nOut(1) // Binary classification (0 hoặc 1)
                  .activation(Activation.SIGMOID)
                  .build())
            .build();

      // Initialize model
      MultiLayerNetwork model = new MultiLayerNetwork(config);
      model.init();
      model.setListeners(new ScoreIterationListener(10));

      // Train model
      model.fit(trainIter);

      // Evaluate model
      Evaluation eval = model.evaluate(testIter);
      log.info("Model Evaluation: \n{}", eval.stats());

      // Save model
      model.save(new File("candidate_recommendation_model.zip"), true);
      log.info("Model saved to candidate_recommendation_model.zip");
   }

   /**
    * Pad features to ensure each vector has the same number of columns.
    */
   private static INDArray padFeatures(INDArray features, int targetColumns) {
      int currentColumns = features.columns();
      if (currentColumns >= targetColumns) return features;

      // Padding thêm cột 0 vào cuối
      INDArray padding = Nd4j.zeros(1, targetColumns - currentColumns);
      return Nd4j.hstack(features, padding);
   }

   /**
    * Calculate label using cosine similarity.
    *
    * @param jobFeature       Feature vector for the job.
    * @param candidateFeature Feature vector for the candidate.
    * @return 1 if similarity > 0.8, otherwise 0.
    */
   private static double calculateLabel(INDArray jobFeature, INDArray candidateFeature) {
      double similarity = Transforms.cosineSim(jobFeature, candidateFeature);
      return similarity > 0.8 ? 1 : 0;
   }
}
