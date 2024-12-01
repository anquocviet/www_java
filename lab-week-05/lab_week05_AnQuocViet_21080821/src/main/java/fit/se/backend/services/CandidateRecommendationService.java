package fit.se.backend.services;

import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.dtos.SkillWithLevelDTO;
import fit.se.backend.repositories.JobRepository;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 1/12/24
 */
@Service
public class CandidateRecommendationService {
   private final JobRepository jobRepository;
   private final CandidateService candidateService;
   private MultiLayerNetwork model;
   private static final int FEATURE_SIZE = 100; // Kích thước vector đầu vào
   private static final double threshold = 0.6; // Ngưỡng dự đoán

   public CandidateRecommendationService(JobRepository jobRepository, CandidateService candidateService) {
      try {
         this.model = MultiLayerNetwork.load(new File("candidate_recommendation_model.zip"), true);
      } catch (IOException e) {
         Logger.getLogger(JobRecommendationService.class.getName()).severe("Error loading model: " + e.getMessage());
         this.model = null; // Đảm bảo ứng dụng không bị lỗi toàn cục
      }
      this.jobRepository = jobRepository;
      this.candidateService = candidateService;
   }

   public List<CandidateDto> recommendCandidates(Long jobId) {
      if (model == null) {
         throw new IllegalStateException("Model not loaded.");
      }
      List<SkillWithLevelDTO> jobSkills = jobRepository.findSkillOfJob(jobId);
      INDArray jobVector = encodeSkills(jobSkills);

      INDArray predictions = model.output(jobVector);
      Logger.getLogger(CandidateRecommendationService.class.getName())
            .info("Predictions: " + predictions.toStringFull());

      // Lọc các ứng viên phù hợp
      List<Long> candidateIds = new ArrayList<>();
      for (int i = 0; i < predictions.length(); i++) {
         if (predictions.getDouble(i) > threshold) {
            candidateIds.add((long) i);
         }
      }
      return candidateService.findCandidatesByIds(candidateIds);
   }

   private INDArray encodeSkills(List<SkillWithLevelDTO> skills) {
      INDArray vector = null;
      for (SkillWithLevelDTO skill : skills) {
         INDArray skillVector = Nd4j.create(skill.skillId(), FEATURE_SIZE);
         skillVector.putScalar(skill.skillLevel().ordinal(), 1.0);
         if (vector == null) {
            vector = skillVector;
         } else {
            vector.addi(skillVector);
         }
      }
      return vector;
   }
}
