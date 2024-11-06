package fit.se.backend.repositories;

import fit.se.backend.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyRepository extends CrudRepository<Company, Long>, PagingAndSortingRepository<Company, Long> {
   @Query("""
         select c from Company c where
         upper(c.compName) like upper(?1)
         or upper(c.email) like upper(?1)
         or upper(c.phone) like upper(?1 )
         or upper(c.webUrl) like upper(?1)
         """)
   Page<Company> searchCompaniesRelativeByCompanyNameOrEmailOrPhoneOrWebUrl(String keyword, Pageable pageable);
}