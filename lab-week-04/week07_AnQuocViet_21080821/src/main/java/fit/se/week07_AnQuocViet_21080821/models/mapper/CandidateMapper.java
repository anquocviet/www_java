package fit.se.week07_AnQuocViet_21080821.models.mapper;


import fit.se.week07_AnQuocViet_21080821.models.Candidate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description
 * @author: vie
 * @date: 12/10/24
 */
public class CandidateMapper implements RowMapper<Candidate> {
   @Override
   public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Candidate(
              rs.getInt("id"),
              rs.getString("first_name") + " " + rs.getString("middle_name") + " " + rs.getString("last_name"),
              rs.getTimestamp("dob").toInstant(),
              rs.getString("email"),
              rs.getString("address"),
              rs.getString("phone")
      );
   }
}
