package fit.se.week07_AnQuocViet_21080821.services;

import fit.se.week07_AnQuocViet_21080821.models.Skill;
import fit.se.week07_AnQuocViet_21080821.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description
 * @author: vie
 * @date: 13/10/24
 */
@Service
public class SkillService {
   private final SkillRepository skillRepository;

   public SkillService(SkillRepository skillRepository) {
      this.skillRepository = skillRepository;
   }

   public List<Skill> findAll() {
      return skillRepository.findAll();
   }

   public Skill findById(int id) {
      return skillRepository.findById(id);
   }

   public boolean create(Skill skill) {
      if (skill == null) {
         return false;
      }
      return skillRepository.create(skill);
   }

   public boolean update(Skill skill) {
      if (skill == null) {
         return false;
      }
      return skillRepository.update(skill);
   }

   public boolean delete(int id) {
      return skillRepository.delete(id);
   }
}
