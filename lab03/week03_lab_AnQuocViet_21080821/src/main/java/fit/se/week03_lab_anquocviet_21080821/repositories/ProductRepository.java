package fit.se.week03_lab_anquocviet_21080821.repositories;

import fit.se.week03_lab_anquocviet_21080821.models.Product;
import jakarta.ejb.Local;

import java.util.Set;

@Local
public interface ProductRepository extends IRepository<Product> {
   Set<Product> findByIds(Set<Long> ids);
}
