package fit.se.week03_lab_anquocviet_21080821.repositories;

import fit.se.week03_lab_anquocviet_21080821.models.Customer;
import jakarta.ejb.Local;

@Local
public interface CustomerRepository extends IRepository<Customer> {
}
