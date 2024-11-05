package fit.se.week07_AnQuocViet_21080821.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 12/10/24
 */

@Data
@RequiredArgsConstructor
public class Candidate {
   // Make final to ensure immutability, lombok will not generate setter for this field,
   // and we can only set this field through constructor
   private final int id;
   private final String fullName;
   private final Instant dob;
   private final String email;
   private final String address;
   private final String phone;
   private Set<SkillCandidate> skillCandidates;

   // Constructor for creating new candidate, id is not required
   public Candidate(String fullName, Instant dob, String email, String address, String phone) {
      this.id = -1;
      this.fullName = fullName;
      this.dob = dob;
      this.email = email;
      this.address = address;
      this.phone = phone;
   }
}
