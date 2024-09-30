package fit.se.week03_lab_anquocviet_21080821.repositories.impl;

import fit.se.week03_lab_anquocviet_21080821.enums.ProductStatus;
import fit.se.week03_lab_anquocviet_21080821.models.Product;
import fit.se.week03_lab_anquocviet_21080821.models.ProductImage;
import fit.se.week03_lab_anquocviet_21080821.models.ProductPrice;
import fit.se.week03_lab_anquocviet_21080821.repositories.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {
   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public Optional<Product> findById(long id) {
      return Optional.ofNullable(entityManager.find(Product.class, id));
   }

   @Override
   @Transactional
   public Product create(Product product) {
      product.getPrices().forEach(entityManager::persist);
      product.getImages().forEach(entityManager::persist);
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
                   .setParameter("status", ProductStatus.ACTIVE)
                   .getResultStream()
                   .collect(Collectors.toSet());
   }

   @Override
   public Set<Product> findByIds(Set<Long> ids) {
      return entityManager.createNamedQuery("Product.findByIds", Product.class)
                   .setParameter("ids", ids)
                   .getResultStream()
                   .collect(Collectors.toSet());
   }

   @Override
   @Transactional
   public Product updatePrice(Product product, ProductPrice price) {
      product.getPrices().add(price);
      entityManager.persist(price);
      entityManager.merge(product);
      return product;
   }

   @Override
   @Transactional
   public Product updateImage(Product product, ProductImage image) {
      product.getImages().add(image);
      entityManager.persist(image);
      entityManager.merge(product);
      return product;
   }
}
