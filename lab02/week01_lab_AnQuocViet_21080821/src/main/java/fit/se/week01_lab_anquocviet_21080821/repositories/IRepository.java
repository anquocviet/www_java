package fit.se.week01_lab_anquocviet_21080821.repositories;

import java.util.Optional;
import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 8/9/24
 */
public interface IRepository<T> {
   boolean add(T t);

   boolean update(T t);

   boolean delete(String id);

   Optional<T> find(String id);

   Set<T> findAll();

}
