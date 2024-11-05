package fit.se.week07_AnQuocViet_21080821.models.mapper;

import fit.se.week07_AnQuocViet_21080821.models.Job;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description
 * @author: vie
 * @date: 13/10/24
 */
public class JobMapper implements RowMapper<Job> {
   @Override
   public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Job(
              rs.getInt("id"),
              rs.getString("description")
      );
   }
}
