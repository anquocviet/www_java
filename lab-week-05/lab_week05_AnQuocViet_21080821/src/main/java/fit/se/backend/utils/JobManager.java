package fit.se.backend.utils;

import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.dtos.JobDto;

public interface JobManager {
   void sendEmail(JobDto job, CandidateDto candidateDto);
}
