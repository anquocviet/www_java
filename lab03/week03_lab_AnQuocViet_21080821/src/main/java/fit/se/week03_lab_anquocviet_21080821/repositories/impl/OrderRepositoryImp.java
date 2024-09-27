package fit.se.week03_lab_anquocviet_21080821.repositories.impl;

import fit.se.week03_lab_anquocviet_21080821.models.Order;
import fit.se.week03_lab_anquocviet_21080821.repositories.OrderRepository;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 19/9/24
 */
@Stateless
public class OrderRepositoryImp implements OrderRepository {
   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public Optional<Order> findById(long id) {
      return Optional.ofNullable(entityManager.find(Order.class, id));
   }

   @Override
   @Transactional
   public Order create(Order order) {
      entityManager.persist(order);
      order.getOrderDetails().forEach(od -> {
               od.setOrder(order);
               entityManager.persist(od);
            }
      );
      return order;
   }

   @Override
   @Transactional
   public Order update(Order order) {
      return entityManager.merge(order);
   }

   @Override
   @Transactional
   public boolean delete(long id) {
      Order order = entityManager.find(Order.class, id);
      if (order == null) {
         return false;
      }
      entityManager.remove(order);
      return true;
   }

   @Override
   public Set<Order> findAll() {
      return entityManager.createNamedQuery("Order.findAll", Order.class)
                   .getResultStream()
                   .collect(Collectors.toSet());
   }

   @Override
   public Map<Long, Double> statisticsByDate(LocalDate date) {
      return entityManager.createNamedQuery("Order.statisticsByDay", Object[].class)
                   .setParameter("date", date)
                   .getResultStream()
                   .collect(Collectors.toMap(
                         row -> (Long) row[0],
                         row -> (Double) row[1]
                   ));
   }

   @Override
   public Map<Long, Double> statisticsBetweenDates(LocalDate startDate, LocalDate endDate) {
      return entityManager.createNamedQuery("Order.statisticsBetweenDates", Object[].class)
                   .setParameter("startDate", startDate)
                   .setParameter("endDate", endDate)
                   .getResultStream()
                   .collect(Collectors.toMap(
                         row -> (Long) row[0],
                         row -> (Double) row[1]
                   ));
   }

   @Override
   public Map<Long, Double> statisticsByEmployeeBetweenDates(long empId, LocalDate startDate, LocalDate endDate) {
      return entityManager.createNamedQuery("Order.statisticsByEmployeeBetweenDates", Object[].class)
                   .setParameter("empId", empId)
                   .setParameter("startDate", startDate)
                   .setParameter("endDate", endDate)
                   .getResultStream()
                   .collect(Collectors.toMap(
                         row -> (Long) row[0],
                         row -> (Double) row[1]
                   ));
   }
}
