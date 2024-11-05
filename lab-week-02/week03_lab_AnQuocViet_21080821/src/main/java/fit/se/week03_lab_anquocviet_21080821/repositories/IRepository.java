package fit.se.week03_lab_anquocviet_21080821.repositories;

import java.util.Optional;
import java.util.Set;

public interface IRepository<T> {
   public Optional<T> findById(long id);

   public T create(T t);

   public T update(T t);

   public boolean delete(long id);

   public Set<T> findAll();
}
