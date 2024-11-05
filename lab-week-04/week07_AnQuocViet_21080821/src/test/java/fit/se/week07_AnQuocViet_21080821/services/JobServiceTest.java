package fit.se.week07_AnQuocViet_21080821.services;

import fit.se.week07_AnQuocViet_21080821.models.Job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JobServiceTest {
   @Autowired
   private JobService jobService;

   @Test
   void findAll() {
      List<Job> list = jobService.findAll();
      assertFalse(list.isEmpty());
   }

   @Test
   void findById() {
      Job job = jobService.findById(1);
      System.out.println(job);
      assertNotNull(job);
   }

   @Test
   void create() {
      Job job = new Job("Test");
      boolean result = jobService.create(job);
      assertTrue(result);
   }

   @Test
   void update() {
      Job job = new Job(1, "Test");
      boolean result = jobService.update(job);
      assertTrue(result);
   }

   @Test
   void delete() {
      boolean result = jobService.delete(3);
      assertTrue(result);
   }
}