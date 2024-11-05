package fit.se.week01_lab_anquocviet_21080821.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grant_access")
@NamedQueries({
      @NamedQuery(name = "GrantAccess.findAll", query = "select g from GrantAccess g"),
      @NamedQuery(name = "GrantAccess.findByAccountAndRole", query = "select g from GrantAccess g where g.id.accountId = :accountId and g.id.roleId = :roleId"),
      @NamedQuery(name = "GrantAccess.findByAccountId", query = "select g from GrantAccess g where g.id.accountId = :accountId"),
      @NamedQuery(name = "GrantAccess.deleteByAccountAndRole", query = "delete from GrantAccess g where g.id.accountId = :accountId and g.id.roleId = :roleId"),
})
public class GrantAccess {
   @EmbeddedId
   private GrantAccessId id;

   @MapsId("roleId")
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "role_id", nullable = false)
   private Role role;

   @MapsId("accountId")
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "account_id", nullable = false)
   private Account account;

   @NotNull
   @ColumnDefault("b'1'")
   @Column(name = "is_grant", nullable = false)
   private Boolean isGrant = false;

   @Size(max = 250)
   @ColumnDefault("''")
   @Column(name = "note", length = 250)
   private String note;

}