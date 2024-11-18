package fit.se.backend.mappers;

import fit.se.backend.dtos.CompanyDto;
import fit.se.backend.dtos.CreateCompanyDTO;
import fit.se.backend.models.Company;
import org.mapstruct.Mapper;

/**
 * @description
 * @author: vie
 * @date: 6/11/24
 */
@Mapper(componentModel = "spring")
public interface CompanyMapper {
   public CompanyDto toDto(Company company);

   public Company toEntity(CompanyDto company);

   public Company toEntity(CreateCompanyDTO companyDTO);
}
