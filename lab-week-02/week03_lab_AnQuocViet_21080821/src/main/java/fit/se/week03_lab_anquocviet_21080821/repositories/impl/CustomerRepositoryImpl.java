package fit.se.week03_lab_anquocviet_21080821.repositories.impl;

import fit.se.week03_lab_anquocviet_21080821.models.Customer;
import fit.se.week03_lab_anquocviet_21080821.repositories.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 19/9/24
 */
public class CustomerRepositoryImpl implements CustomerRepository {
   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public Optional<Customer> findById(long id) {
      return Optional.ofNullable(entityManager.find(Customer.class, id));
   }

   @Override
   @Transactional
   public Customer create(Customer customer) {
      entityManager.persist(customer);
      return customer;
   }

   @Override
   @Transactional
   public Customer update(Customer customer) {
      return entityManager.merge(customer);
   }

   @Override
   @Transactional
   public boolean delete(long id) {
      Customer customer = entityManager.find(Customer.class, id);
      if (customer == null) {
         return false;
      }
      entityManager.remove(customer);
      return true;
   }

   @Override
   public Set<Customer> findAll() {
      return entityManager.createNamedQuery("Customer.findAll", Customer.class)
                   .getResultStream()
                   .collect(Collectors.toSet());
   }
}
