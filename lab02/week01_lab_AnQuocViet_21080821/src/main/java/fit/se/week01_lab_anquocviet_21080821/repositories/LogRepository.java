package fit.se.week01_lab_anquocviet_21080821.repositories;

import fit.se.week01_lab_anquocviet_21080821.entities.Log;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 10/9/24
 */
public class LogRepository implements IRepository<Log> {
   private final EntityManager em;
   private final EntityTransaction transaction;
   private final String PERSISTENCE_UNIT_NAME = "default";

   public LogRepository() {
      em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
      transaction = em.getTransaction();
   }

   @Override
   public boolean add(Log log) {
      try {
         transaction.begin();
         em.persist(log);
         transaction.commit();
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean update(Log log) {
      try {
         transaction.begin();
         em.merge(log);
         transaction.commit();
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean delete(String id) {
      return false;
   }

   @Override
   public Optional<Log> find(String id) {
      return Optional.empty();
   }

   public Log findByAccountId(String accountId) {
      return em.createNamedQuery("Log.findByAccountIdAndLogoutTime", Log.class)
                   .setParameter("accountId", accountId)
                   .setParameter("logoutTime", Instant.EPOCH)
                   .getSingleResult();
   }

   @Override
   public Set<Log> findAll() {
      return Set.of();
   }
}
