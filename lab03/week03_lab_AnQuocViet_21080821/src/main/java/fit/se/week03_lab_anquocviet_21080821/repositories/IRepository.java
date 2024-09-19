package fit.se.week03_lab_anquocviet_21080821.repositories;

import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.Set;

public interface IRepository<T> {
   public Optional<T> findById(long id);

   @Transactional
   public T create(T t);

   @Transactional
   public T update(T t);

   @Transactional
   public boolean delete(int id);

   public Set<T> findAll();
}
