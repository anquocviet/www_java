package fit.se.week07_AnQuocViet_21080821.repositories;

import fit.se.week07_AnQuocViet_21080821.models.Job;
import fit.se.week07_AnQuocViet_21080821.models.mapper.JobMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 13/10/24
 */
@Repository
public class JobRepository {
   private final JdbcTemplate temp;
   private final Logger logger = Logger.getLogger(JobRepository.class.getName());

   public JobRepository(DataSource dataSource) {
      temp = new JdbcTemplate(dataSource);
   }

   public List<Job> findAll() {
      return temp.query("SELECT * FROM jobs", new JobMapper());
   }

   public Job findById(int id) {
      return temp.queryForObject("SELECT * FROM jobs WHERE id = ?", new JobMapper(), id);
   }

   public boolean create(Job job) {
      String sql = "INSERT INTO jobs (description) " +
                         "VALUES (?)";
      int result = temp.update(sql,
            job.getDescription()
      );
      if (result == 1) {
         logger.info("Job added successfully");
         return true;
      } else {
         logger.warning("Failed to add job");
         return false;
      }
   }

   public boolean update(Job job) {
      String sql = "UPDATE jobs SET description = ? WHERE id = ?";
      int result = temp.update(sql,
            job.getDescription(),
            job.getId()
      );
      if (result == 1) {
         logger.info("Job updated successfully");
         return true;
      } else {
         logger.warning("Failed to update job");
         return false;
      }
   }

   public boolean delete(int id) {
      String sql = "DELETE FROM jobs WHERE id = ?";
      int result = temp.update(sql, id);
      if (result == 1) {
         logger.info("Job deleted successfully");
         return true;
      } else {
         logger.warning("Failed to delete job");
         return false;
      }
   }
}
