package fit.se.week07_AnQuocViet_21080821.services;

import fit.se.week07_AnQuocViet_21080821.models.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SkillServiceTest {
   @Autowired
   private SkillService skillService;

   @Test
   void findAll() {
      List<Skill> list = skillService.findAll();
      assertFalse(list.isEmpty());
   }

   @Test
   void findById() {
      Skill skill = skillService.findById(1);
      System.out.println(skill);
      assertNotNull(skill);

   }

   @Test
   void create() {
      Skill skill = new Skill("Java", "Java programming language", "IT");
      boolean result = skillService.create(skill);
      assertTrue(result);

   }

   @Test
   void update() {
      Skill skill = new Skill(1, "Java", "Java programming language", "IT");
      boolean result = skillService.update(skill);
      assertTrue(result);
   }

   @Test
   void delete() {
      boolean result = skillService.delete(1);
      assertTrue(result);
   }
}