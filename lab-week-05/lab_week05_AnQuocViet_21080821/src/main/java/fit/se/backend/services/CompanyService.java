package fit.se.backend.services;

import fit.se.backend.dtos.CompanyDto;
import fit.se.backend.exceptions.AppException;
import fit.se.backend.mappers.CompanyMapper;
import fit.se.backend.repositories.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author: vie
 * @date: 6/11/24
 */
@Service
public class CompanyService {
   private final CompanyRepository companyRepository;
   private final CompanyMapper companyMapper;

   public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
      this.companyRepository = companyRepository;
      this.companyMapper = companyMapper;
   }

   public Page<CompanyDto> findAllWithPagination(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      return companyRepository.findAll(pageable).map(companyMapper::toDto);
   }

   public Page<CompanyDto> search(String keyword, int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      return companyRepository
                   .searchCompaniesRelativeByCompanyNameOrEmailOrPhoneOrWebUrl("%" + keyword + "%", pageable)
                   .map(companyMapper::toDto);
   }

   public CompanyDto findById(Long id) {
      return companyRepository.findById(id)
                   .map(companyMapper::toDto)
                   .orElseThrow(() -> new AppException(404, "Company not found"));
   }
}
