package fit.se.week01_lab_anquocviet_21080821.repositories;

import fit.se.week01_lab_anquocviet_21080821.entities.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 7/9/24
 */
public class AccountRepository implements IRepository<Account> {
   private EntityManager em;

   EntityTransaction transaction = null;

   public AccountRepository() {
      em = Persistence.createEntityManagerFactory("default").createEntityManager();
      transaction = em.getTransaction();
   }

   @Override
   public boolean add(Account account) {
      try {
         transaction.begin();
         em.persist(account);
         transaction.commit();
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean update(Account account) {
      try {
         transaction.begin();
         em.merge(account);
         transaction.commit();
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public boolean delete(String accountId) {
      try {
         transaction.begin();
         Account account = em.find(Account.class, accountId);
         em.remove(account);
         transaction.commit();
         return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   @Override
   public Optional<Account> find(String id) {
      Account account = em.find(Account.class, id);
      return Optional.ofNullable(account);
   }

   @Override
   public Set<Account> findAll() {
      return em.createNamedQuery("Account.findAll", Account.class)
                   .getResultStream()
                   .map(account -> account)
                   .collect(Collectors.toSet());
   }

   public Account checkLogin(String phone, String password) {
      return em.createNamedQuery("Account.login", Account.class)
                   .setParameter("phone", phone)
                   .setParameter("password", password)
                   .getSingleResult();

   }

   public Set<Account> findByRole(String role) {
      return em.createNamedQuery("Account.findByRole", Account.class)
                   .setParameter("roleId", role)
                   .getResultStream()
                   .collect(Collectors.toSet());
   }
}
