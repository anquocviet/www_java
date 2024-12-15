package fit.se.backend.services;

import fit.se.backend.dtos.SkillDto;
import fit.se.backend.mappers.SkillMapper;
import fit.se.backend.models.Skill;
import fit.se.backend.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description
 * @author: vie
 * @date: 13/12/24
 */
@Service
public class SkillRecommendationService {
   final SkillRepository skillRepository;
   private final SkillMapper skillMapper;

   public SkillRecommendationService(SkillRepository skillRepository,
                                     SkillMapper skillMapper) {
      this.skillRepository = skillRepository;
      this.skillMapper = skillMapper;
   }

   public Map<SkillDto, Long> recommendSkill() {
      return skillRepository.recommendSkill().stream()
            .collect(Collectors.toMap(
                  o -> skillMapper.toSkillDto((Skill) o[0]),
                  o -> (Long) o[1]
            ));
   }
}
