package fit.se.week01_lab_anquocviet_21080821.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GrantAccessId implements Serializable {
   private static final long serialVersionUID = -1266306788825943162L;
   @Size(max = 50)
   @NotNull
   @Column(name = "role_id", nullable = false, length = 50)
   private String roleId;

   @Size(max = 50)
   @NotNull
   @Column(name = "account_id", nullable = false, length = 50)
   private String accountId;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      GrantAccessId entity = (GrantAccessId) o;
      return Objects.equals(this.accountId, entity.accountId) &&
                   Objects.equals(this.roleId, entity.roleId);
   }

   @Override
   public int hashCode() {
      return Objects.hash(accountId, roleId);
   }

}