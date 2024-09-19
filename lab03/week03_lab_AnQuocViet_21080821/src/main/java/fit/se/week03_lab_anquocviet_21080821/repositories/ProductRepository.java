package fit.se.week03_lab_anquocviet_21080821.repositories;

import fit.se.week03_lab_anquocviet_21080821.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 18/9/24
 */
public class ProductRepository implements IRepository<Product> {
   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public Optional<Product> findById(long id) {
      return Optional.ofNullable(entityManager.find(Product.class, id));
   }

   @Override
   @Transactional
   public Product create(Product product) {
      entityManager.persist(product);
      return product;
   }

   @Override
   @Transactional
   public Product update(Product product) {
      return entityManager.merge(product);
   }

   @Override
   @Transactional
   public boolean delete(long id) {
      Product product = entityManager.find(Product.class, id);
      if (product == null) {
         return false;
      }
      entityManager.remove(product);
      return true;
   }

   @Override
   public Set<Product> findAll() {
      return entityManager.createNamedQuery("Product.findAll", Product.class)
                   .getResultStream()
                   .collect(Collectors.toSet());
   }
}
