package fit.se.week03_lab_anquocviet_21080821.repositories;

import fit.se.week03_lab_anquocviet_21080821.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
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
   public Product findById(int id) {
      return entityManager.find(Product.class, id);
   }

   @Override
   public Product create(Product product) {
      return null;
   }

   @Override
   public Product update(Product product) {
      return null;
   }

   @Override
   public boolean delete(int id) {
      return false;
   }

   @Override
   public Set<Product> findAll() {
      return entityManager.createNamedQuery("Product.findAll", Product.class)
                   .getResultStream()
                   .collect(Collectors.toSet());
   }
}
