package fit.se.week03_lab_anquocviet_21080821.repositories;

import java.util.Set;

public interface IRepository<T> {
   public T findById(int id);

   public T create(T t);

   public T update(T t);

   public boolean delete(int id);

   public Set<T> findAll();
}
