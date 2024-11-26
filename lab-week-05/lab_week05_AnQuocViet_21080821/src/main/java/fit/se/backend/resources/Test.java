package fit.se.backend.resources;

import fit.se.backend.dtos.JobDto;
import fit.se.backend.services.JobRecommendationService;
import fit.se.backend.services.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description
 * @author: vie
 * @date: 25/11/24
 */
@RestController
@RequestMapping("/test")
public class Test {
   private final JobRecommendationService jobRecommendationService;
   private final JobService jobService;

   public Test(JobRecommendationService jobRecommendationService, JobService jobService) {
      this.jobRecommendationService = jobRecommendationService;
      this.jobService = jobService;
   }

   @GetMapping("/recommend/{id}")
   public List<JobDto> predict(@PathVariable("id") Long candidateId) {
      return jobRecommendationService.recommendJobs(candidateId);
//      return jobService.findJobsForCandidate(candidateId);
   }
}
