package fit.se.ml_models;

import lombok.extern.slf4j.Slf4j;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 25/11/24
 */
@Slf4j
public class DataProcessor {
   private static final String DB_URL = "jdbc:mariadb://localhost:3307/jobs";
   private static final String USER = "root";
   private static final String PASSWORD = "root";

   public static Map<String, Map<String, INDArray>> loadData() throws SQLException {
      Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

      // Load user skills
      String candidateQuery = "SELECT cs.can_id AS candidate_id, cs.skill_id, cs.skill_level FROM candidate_skill cs;";
      PreparedStatement candidateStatement = connection.prepareStatement(candidateQuery);
      ResultSet candidateResult = candidateStatement.executeQuery();

      Map<String, INDArray> candidateSkills = new HashMap<>();
      while (candidateResult.next()) {
         String candidateId = candidateResult.getString("candidate_id");
         long skillId = candidateResult.getLong("skill_id");
         int skillLevel = candidateResult.getInt("skill_level");

         candidateSkills.putIfAbsent(candidateId, Nd4j.zeros(1, 100)); // Assuming max 100 skills
         candidateSkills.get(candidateId).putScalar(0, skillId % 100, skillLevel + 1.0);
      }

      // Load job skills
      String jobQuery = "SELECT js.job_id, js.skill_id, js.skill_level FROM job_skill js;";
      PreparedStatement jobStatement = connection.prepareStatement(jobQuery);
      ResultSet jobResult = jobStatement.executeQuery();

      Map<String, INDArray> jobSkills = new HashMap<>();
      while (jobResult.next()) {
         String jobId = jobResult.getString("job_id");
         long skillId = jobResult.getLong("skill_id");
         int skillLevel = jobResult.getInt("skill_level");

         jobSkills.putIfAbsent(jobId, Nd4j.zeros(1, 100)); // Assuming max 100 skills
         jobSkills.get(jobId).putScalar(0, skillId % 100, skillLevel + 1.0);
      }

      connection.close();

      return Map.of("candidates", candidateSkills, "jobs", jobSkills);
   }
}