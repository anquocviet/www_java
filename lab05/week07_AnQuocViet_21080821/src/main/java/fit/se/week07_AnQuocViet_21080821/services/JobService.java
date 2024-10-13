package fit.se.week07_AnQuocViet_21080821.services;

import fit.se.week07_AnQuocViet_21080821.models.Job;
import fit.se.week07_AnQuocViet_21080821.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description
 * @author: vie
 * @date: 13/10/24
 */
@Service
public class JobService {
   private final JobRepository jobRepository;

   public JobService(JobRepository jobRepository) {
      this.jobRepository = jobRepository;
   }

   public List<Job> findAll() {
      return jobRepository.findAll();
   }

   public Job findById(int id) {
      return jobRepository.findById(id);
   }

   public boolean create(Job job) {
      if (job == null) {
         return false;
      }
      return jobRepository.create(job);
   }

   public boolean update(Job job) {
      if (job == null) {
         return false;
      }
      return jobRepository.update(job);
   }

   public boolean delete(int id) {
      return jobRepository.delete(id);
   }
}
