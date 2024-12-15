package fit.se.backend.services;

import fit.se.backend.dtos.JobDto;
import fit.se.backend.dtos.SkillWithLevelDTO;
import fit.se.backend.repositories.CandidateRepository;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 25/11/24
 */
@Service
public class JobRecommendationService {
   private MultiLayerNetwork model;
   private static final int FEATURE_SIZE = 100; // Kích thước vector đầu vào
   private static final double threshold = 0.6; // Ngưỡng dự đoán

   private final CandidateRepository candidateRepository;
   private final JobService jobService;

   public JobRecommendationService(CandidateRepository candidateRepository, JobService jobService) {
      try {
         this.model = MultiLayerNetwork.load(new File("job_recommendation_model.zip"), true);
      } catch (Exception e) {
         Logger.getLogger(JobRecommendationService.class.getName()).severe("Error loading model: " + e.getMessage());
         this.model = null;
      }
      this.candidateRepository = candidateRepository;
      this.jobService = jobService;
   }

   public List<JobDto> recommendJobs(Long candidateId) {
      if (model == null) {
         throw new IllegalStateException("Model not loaded.");
      }

      // Lấy danh sách kỹ năng của ứng viên
      List<SkillWithLevelDTO> userSkills = candidateRepository.findSkillOfUser(candidateId);

      // Mã hóa kỹ năng
      INDArray userVector = encodeSkills(userSkills);

      // Dự đoán
      INDArray predictions = model.output(userVector);
      Logger.getLogger(JobRecommendationService.class.getName())
            .info("Predictions: " + predictions.toStringFull());

      // Lọc các công việc phù hợp
      List<Long> jobIds = new ArrayList<>();
      for (int i = 0; i < predictions.columns(); i++) {
         if (predictions.getDouble(i) > threshold) {
            jobIds.add((long) i);
         }
      }

      return jobService.findJobsByIds(jobIds);
   }

   private INDArray encodeSkills(List<SkillWithLevelDTO> userSkills) {
      INDArray userVector = Nd4j.zeros(1, FEATURE_SIZE); // Đồng bộ với kích thước đầu vào của mô hình
      userSkills.forEach(skill -> {
         long index = skill.skillId() % FEATURE_SIZE; // Đảm bảo không vượt quá kích thước vector
         userVector.putScalar(
               0, index, skill.skillLevel().ordinal() + 1.0
         );
      });
      return userVector;
   }
}
