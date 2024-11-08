package fit.se.backend.services;

import fit.se.backend.dtos.SkillDto;
import fit.se.backend.mappers.SkillMapper;
import fit.se.backend.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * @description
 * @author: vie
 * @date: 7/11/24
 */
@Service
public class SkillService {
   private final SkillRepository skillRepository;
   private final SkillMapper skillMapper;

   public SkillService(SkillRepository skillRepository, SkillMapper skillMapper) {
      this.skillRepository = skillRepository;
      this.skillMapper = skillMapper;
   }

   public List<SkillDto> findAll() {
      return StreamSupport.stream(skillRepository.findAll().spliterator(), false)
                   .map(skillMapper::toSkillDto)
                   .toList();
   }
}
