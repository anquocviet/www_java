package fit.se.week01_lab_anquocviet_21080821.repositories;

import fit.se.week01_lab_anquocviet_21080821.entities.GrantAccess;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 10/9/24
 */
public class GrantAccessRepository implements IRepository<GrantAccess> {
   private EntityManager em;
   private EntityTransaction transaction;
   private static final String PERSISTENCE_UNIT_NAME = "default";

   public GrantAccessRepository() {
      em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
      transaction = em.getTransaction();
   }

   @Override
   public boolean add(GrantAccess grantAccess) {
      try {
         transaction.begin();
         em.persist(grantAccess);
         transaction.commit();
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean update(GrantAccess grantAccess) {
      try {
         transaction.begin();
         em.merge(grantAccess);
         transaction.commit();
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean delete(String id) {
      throw new UnsupportedOperationException();
   }

   public boolean delete(GrantAccess grantAccess) {
      try {
         transaction.begin();
         em.remove(grantAccess);
         transaction.commit();
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public Optional<GrantAccess> find(String id) {
      throw new UnsupportedOperationException();
   }

   public Optional<GrantAccess> find(String accountId, String roleId) {
      return em.createNamedQuery("GrantAccess.findByAccountAndRole", GrantAccess.class)
                   .setParameter("accountId", accountId)
                   .setParameter("roleId", roleId)
                   .getResultStream()
                   .findFirst();
   }

   public Set<GrantAccess> findByAccount(String accountId) {
      return new HashSet<>(
            em.createNamedQuery("GrantAccess.findByAccountId", GrantAccess.class)
                  .setParameter("accountId", accountId)
                  .getResultList());
   }

   @Override
   public Set<GrantAccess> findAll() {
      return new HashSet<>(
            em.createNamedQuery("GrantAccess.findAll", GrantAccess.class)
                  .getResultList());
   }
}
