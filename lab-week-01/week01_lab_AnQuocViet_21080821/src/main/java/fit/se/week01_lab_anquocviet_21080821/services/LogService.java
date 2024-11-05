package fit.se.week01_lab_anquocviet_21080821.services;

import fit.se.week01_lab_anquocviet_21080821.entities.Log;
import fit.se.week01_lab_anquocviet_21080821.repositories.LogRepository;

import java.time.Instant;

/**
 * @description
 * @author: vie
 * @date: 10/9/24
 */
public class LogService {
   private final LogRepository logRepository;

   public LogService() {
      logRepository = new LogRepository();
   }

   public boolean addLog(String accountId) {
      Log log = new Log(
            null,
            accountId,
            Instant.now(),
            Instant.EPOCH,
            "Login successfully"
      );
      return logRepository.add(log);
   }

   public boolean updateLog(String accountId) {
      Log log = logRepository.findByAccountId(accountId);
      log.setLogoutTime(Instant.now());
      return logRepository.update(log);

   }
}
