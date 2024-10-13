package fit.se.week07_AnQuocViet_21080821.repositories;

import fit.se.week07_AnQuocViet_21080821.models.Candidate;
import fit.se.week07_AnQuocViet_21080821.models.mapper.CandidateMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 12/10/24
 */
@Repository
public class CandidateRepository {
   private final JdbcTemplate temp;
   private final Logger logger = Logger.getLogger(CandidateRepository.class.getName());

   public CandidateRepository(DataSource dataSource) {
      temp = new JdbcTemplate(dataSource);
   }

   public List<Candidate> findAll() {
      return temp.query("SELECT * FROM candidates", new CandidateMapper());
   }

   public Candidate findById(int id) {
      return temp.queryForObject("SELECT * FROM candidates WHERE id = ?", new CandidateMapper(), id);
   }

   public boolean create(Candidate candidate) {
      String sql = "INSERT INTO candidates (first_name, middle_name, last_name, dob, email, address, phone) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
      int result = temp.update(sql,
            candidate.getFullName().split(" ")[0],
            candidate.getFullName().split(" ")[1],
            candidate.getFullName().split(" ")[2],
            candidate.getDob(),
            candidate.getEmail(),
            candidate.getAddress(),
            candidate.getPhone()
      );
      if (result == 1) {
         logger.info("Candidate added successfully");
         return true;
      } else {
         logger.warning("Failed to add candidate");
         return false;
      }
   }

   public boolean update(Candidate candidate) {
      String sql = "UPDATE candidates SET first_name = ?, middle_name = ?, last_name = ?, dob = ?, email = ?, address = ?, phone = ? WHERE id = ?";
      int result = temp.update(sql,
            candidate.getFullName().split(" ")[0],
            candidate.getFullName().split(" ")[1],
            candidate.getFullName().split(" ")[2],
            candidate.getDob(),
            candidate.getEmail(),
            candidate.getAddress(),
            candidate.getPhone(),
            candidate.getId()
      );
      if (result == 1) {
         logger.info("Candidate updated successfully");
         return true;
      } else {
         logger.warning("Failed to update candidate");
         return false;
      }
   }

   public boolean delete(int id) {
      int result = temp.update("DELETE FROM candidates WHERE id = ?", id);
      if (result == 1) {
         logger.info("Candidate deleted successfully");
         return true;
      } else {
         logger.warning("Failed to delete candidate");
         return false;
      }
   }
}
