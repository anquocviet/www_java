package fit.se.week07_AnQuocViet_21080821.repositories;

import fit.se.week07_AnQuocViet_21080821.models.Skill;
import fit.se.week07_AnQuocViet_21080821.models.mapper.SkillMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * @description
 * @author: vie
 * @date: 13/10/24
 */
@Repository
public class SkillRepository {
   private final JdbcTemplate temp;

   public SkillRepository(DataSource dataSource) {
      this.temp = new JdbcTemplate(dataSource);
   }

   public List<Skill> findAll() {
      return temp.query("SELECT * FROM skills", new SkillMapper());
   }

   public Skill findById(int id) {
      return temp.queryForObject("SELECT * FROM skills WHERE id = ?", new SkillMapper(), id);
   }

   public boolean create(Skill skill) {
      String sql = "INSERT INTO skills (skill_name, description, field) " +
                         "VALUES (?, ?, ?)";
      int result = temp.update(sql,
            skill.getSkillName(),
            skill.getDescription(),
            skill.getField()
      );
      return result == 1;
   }

   public boolean update(Skill skill) {
      String sql = "UPDATE skills SET skill_name = ?, description = ?, field = ? WHERE id = ?";
      int result = temp.update(sql,
            skill.getSkillName(),
            skill.getDescription(),
            skill.getField(),
            skill.getId()
      );
      return result == 1;
   }

   public boolean delete(int id) {
      String sql = "DELETE FROM skills WHERE id = ?";
      int result = temp.update(sql, id);
      return result == 1;
   }
}
