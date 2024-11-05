package fit.se.week07_AnQuocViet_21080821.models.mapper;

import fit.se.week07_AnQuocViet_21080821.models.Skill;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description
 * @author: vie
 * @date: 13/10/24
 */
public class SkillMapper implements RowMapper<Skill> {

   @Override
   public Skill mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Skill(
               rs.getString("skill_name"),
               rs.getString("description"),
               rs.getString("field")
      );
   }
}
