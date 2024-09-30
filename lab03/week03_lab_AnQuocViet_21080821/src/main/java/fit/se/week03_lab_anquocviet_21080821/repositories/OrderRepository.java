package fit.se.week03_lab_anquocviet_21080821.repositories;

import fit.se.week03_lab_anquocviet_21080821.models.Order;

import java.time.LocalDate;
import java.util.Map;

public interface OrderRepository extends IRepository<Order> {
   Map<Long, Double> statisticsByDate(LocalDate localDate);

   Map<Long, Double> statisticsBetweenDates(LocalDate startDate, LocalDate endDate);

   Map<Long, Double> statisticsByEmployeeBetweenDates(long empId, LocalDate startDate, LocalDate endDate);
}
