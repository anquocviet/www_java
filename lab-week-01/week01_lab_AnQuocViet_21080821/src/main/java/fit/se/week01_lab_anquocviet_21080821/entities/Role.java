package fit.se.week01_lab_anquocviet_21080821.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
@NamedQueries({
      @NamedQuery(name = "Role.findAll", query = "select r from Role r")
})
public class Role {
   @Id
   @Size(max = 50)
   @Column(name = "role_id", nullable = false, length = 50)
   private String roleId;

   @Size(max = 50)
   @NotNull
   @Column(name = "role_name", nullable = false, length = 50)
   private String roleName;

   @Size(max = 50)
   @Column(name = "description", length = 50)
   private String description;

   @NotNull
   @Column(name = "status", nullable = false)
   private Byte status;

   @OneToMany(mappedBy = "role")
   private Set<GrantAccess> grantAccesses = new LinkedHashSet<>();

}