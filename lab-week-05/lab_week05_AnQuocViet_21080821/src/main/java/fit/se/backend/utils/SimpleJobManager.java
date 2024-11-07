package fit.se.backend.utils;

import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.dtos.JobDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 7/11/24
 */
@Setter
@Service
public class SimpleJobManager implements JobManager {
   private MailSender mailSender;
   private SimpleMailMessage templateMessage;
   private final Logger logger = Logger.getLogger(SimpleJobManager.class.getName());

   public void sendEmail(JobDto job, CandidateDto candidateDto) {

      // Do the business calculations...

      // Call the collaborators to persist the order...

      // Create a thread-safe "copy" of the template message and customize it
      SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
      msg.setFrom(job.companyEmail());
      msg.setTo(candidateDto.email());
      msg.setText("Dear " + candidateDto.fullName() + ",\n\n" +
                        "We are pleased to invite you to apply for the position of " + job.jobName() + " at our company.\n\n" +
                        "Job Description:\n" + job.jobDesc() + "\n\n" +
                        "Please let us know if you are interested.\n\n" +
                        "Best regards,\n" +
                        job.companyName());
      try {
         this.mailSender.send(msg);
      } catch (MailException ex) {
         logger.log(Level.SEVERE, "An error occurred while sending the email: " + ex.getMessage());
         throw new RuntimeException("An error occurred while sending the email: " + ex.getMessage());
      }
   }

}
