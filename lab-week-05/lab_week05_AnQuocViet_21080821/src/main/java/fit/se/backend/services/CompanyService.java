package fit.se.backend.services;

import fit.se.backend.dtos.CompanyDto;
import fit.se.backend.dtos.CreateCompanyDTO;
import fit.se.backend.exceptions.impl.CompanyNotFoundException;
import fit.se.backend.exceptions.impl.EmailAlreadyExistsException;
import fit.se.backend.exceptions.impl.PhoneAlreadyExistsException;
import fit.se.backend.exceptions.impl.WebURLAlreadyExistsException;
import fit.se.backend.mappers.CompanyMapper;
import fit.se.backend.models.Company;
import fit.se.backend.repositories.AddressRepository;
import fit.se.backend.repositories.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
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
   private final PasswordEncoder passwordEncoder;
   private final AddressService addressService;
   private final AddressRepository addressRepository;

   public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, PasswordEncoder passwordEncoder, AddressService addressService, AddressRepository addressRepository) {
      this.companyRepository = companyRepository;
      this.companyMapper = companyMapper;
      this.passwordEncoder = passwordEncoder;
      this.addressService = addressService;
      this.addressRepository = addressRepository;
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
            .orElseThrow(CompanyNotFoundException::new);
   }

   public CompanyDto save(CreateCompanyDTO companyDTO) {
      if (companyRepository.existsCompaniesByEmail(companyDTO.email())) {
         throw new EmailAlreadyExistsException();
      }
      if (companyRepository.existsCompaniesByPhone(companyDTO.phone())) {
         throw new PhoneAlreadyExistsException();
      }
      if (companyRepository.existsCompaniesByWebUrl(companyDTO.webUrl())) {
         throw new WebURLAlreadyExistsException();
      }
      Company company = companyMapper.toEntity(companyDTO);
      String hashedPassword = passwordEncoder.encode(companyDTO.password());
      company.setPassword(hashedPassword);
      Company compSave = companyRepository.save(company);
      return companyMapper.toDto(compSave);
   }

   public void update(CompanyDto companyDto) {
      Company companpy = companyRepository.findById(companyDto.id())
            .orElseThrow(CompanyNotFoundException::new);
      companyRepository.findCompanyByEmail(companyDto.email())
            .ifPresent(company -> {
               if (!company.getId().equals(companyDto.id())) {
                  throw new EmailAlreadyExistsException();
               }
            });
      companyRepository.findCompanyByPhone(companyDto.phone())
            .ifPresent(company -> {
               if (!company.getId().equals(companyDto.id())) {
                  throw new PhoneAlreadyExistsException();
               }
            });

      Company newCompany = companyMapper.toEntity(companyDto);
      newCompany.setPassword(companpy.getPassword());
      companyRepository.save(newCompany);
   }

   public CompanyDto findByEmail(String email) {
      Company company = companyRepository.findCompanyByEmail(email)
            .orElseThrow(CompanyNotFoundException::new);
      return companyMapper.toDto(company);
   }
}
