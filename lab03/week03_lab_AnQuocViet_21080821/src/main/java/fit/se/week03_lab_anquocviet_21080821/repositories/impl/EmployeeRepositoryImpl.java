package fit.se.week03_lab_anquocviet_21080821.repositories.impl;

import fit.se.week03_lab_anquocviet_21080821.models.Employee;
import fit.se.week03_lab_anquocviet_21080821.repositories.EmployeeRepository;
import jakarta.ejb.Stateless;
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
@Stateless
public class EmployeeRepositoryImpl implements EmployeeRepository {
   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public Optional<Employee> findById(long id) {
      return Optional.ofNullable(entityManager.find(Employee.class, id));
   }

   @Override
   @Transactional
   public Employee create(Employee employee) {
      entityManager.persist(employee);
      return employee;
   }

   @Override
   @Transactional
   public Employee update(Employee employee) {
      return entityManager.merge(employee);
   }

   @Override
   @Transactional
   public boolean delete(long id) {
      Employee employee = entityManager.find(Employee.class, id);
      if (employee == null) {
         return false;
      }
      entityManager.remove(employee);
      return true;
   }

   @Override
   public Set<Employee> findAll() {
      return entityManager.createNamedQuery("Employee.findAll", Employee.class)
                   .getResultStream()
                   .collect(Collectors.toSet());
   }
}
