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
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
      @NamedQuery(name = "Account.findAll", query = "select a from Account a"),
      @NamedQuery(name = "Account.login", query = "select a from Account a where a.phone = :phone and a.password = :password"),
      @NamedQuery(name = "Account.findByRole", query = "select a from Account a inner join a.grantAccesses grantAccesses where grantAccesses.role.roleId = :roleId")
})
public class Account {
   @Id
   @Size(max = 50)
   @Column(name = "account_id", nullable = false, length = 50)
   private String accountId;

   @Size(max = 50)
   @NotNull
   @Column(name = "full_name", nullable = false, length = 50)
   private String fullName;

   @Size(max = 50)
   @NotNull
   @Column(name = "password", nullable = false, length = 50)
   private String password;

   @Size(max = 50)
   @Column(name = "email", length = 50)
   private String email;

   @Size(max = 50)
   @Column(name = "phone", length = 50)
   private String phone;

   @NotNull
   @ColumnDefault("1")
   @Column(name = "status", nullable = false)
   private Byte status;

   @OneToMany(mappedBy = "account")
   private Set<GrantAccess> grantAccesses = new LinkedHashSet<>();

}