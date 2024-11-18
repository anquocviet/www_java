package fit.se.backend.services;

import fit.se.backend.dtos.AddressDto;
import fit.se.backend.mappers.AddressMapper;
import fit.se.backend.mappers.CandidateMapper;
import fit.se.backend.mappers.CompanyMapper;
import fit.se.backend.models.Address;
import fit.se.backend.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 5/11/24
 */
@Service
public class AddressService {
   private final AddressRepository addressRepository;
   private final AddressMapper addressMapper;
   private final Logger logger = Logger.getLogger(AddressService.class.getName());
   private final CandidateMapper candidateMapper;
   private final CompanyMapper companyMapper;

   public AddressService(AddressRepository addressRepository,
                         AddressMapper addressMapper,
                         CandidateMapper candidateMapper,
                         CompanyMapper companyMapper) {
      this.addressRepository = addressRepository;
      this.addressMapper = addressMapper;
      this.candidateMapper = candidateMapper;
      this.companyMapper = companyMapper;
   }

   public AddressDto save(AddressDto addressDto) {
      Address address = addressMapper.toEntity(addressDto);
      return addressMapper.toDto(addressRepository.save(address));
   }

   public AddressDto update(AddressDto addressDto) {
      boolean isAddressExists = addressRepository.existsById(addressDto.id());

      if (!isAddressExists) {
         logger.severe("Address not found");
         throw new RuntimeException("Address not found");
      }
      Address address = addressMapper.toEntity(addressDto);
      addressRepository.save(address);
      return addressMapper.toDto(address);
   }

   public AddressDto findById(Long id) {
      return addressRepository.findById(id)
            .map(addressMapper::toDto)
            .orElseThrow(() -> {
               logger.severe("Address not found");
               return new RuntimeException("Address not found");
            });
   }
}
