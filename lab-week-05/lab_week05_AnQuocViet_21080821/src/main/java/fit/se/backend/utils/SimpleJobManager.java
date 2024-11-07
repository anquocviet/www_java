package fit.se.backend.utils;

import fit.se.backend.dtos.CandidateDto;
import fit.se.backend.dtos.JobDto;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 7/11/24
 */
@Service
public class SimpleJobManager implements JobManager {
   private JavaMailSender mailSender;
   private final Logger logger = Logger.getLogger(SimpleJobManager.class.getName());

   @Autowired
   public void setMailSender(JavaMailSender mailSender) {
      this.mailSender = mailSender;
   }

   public void sendEmail(JobDto job, CandidateDto candidateDto) {
      MimeMessagePreparator preparator = mimeMessage -> {
         mimeMessage.setRecipient(Message.RecipientType.TO,
               new InternetAddress(candidateDto.email())
         );
         mimeMessage.setSubject("Job Invitation");
         mimeMessage.setFrom(new InternetAddress(job.companyEmail()));
         String emailBody = """
               Dear %s,
               
               We are pleased to invite you to apply for the position of %s at our company.
               
               Job Description:
               %s
               
               Please let us know if you are interested.
               
               Best regards,
               %s
               """.formatted(
               candidateDto.fullName(),
               job.jobName(),
               job.jobDesc(),
               job.companyName()
         );

         mimeMessage.setText(emailBody);

      };
      try {
         this.mailSender.send(preparator);
      } catch (MailException ex) {
         logger.log(Level.SEVERE, "An error occurred while sending the email: " + ex.getMessage());
         throw new RuntimeException("An error occurred while sending the email: " + ex.getMessage());
      }
   }
}
