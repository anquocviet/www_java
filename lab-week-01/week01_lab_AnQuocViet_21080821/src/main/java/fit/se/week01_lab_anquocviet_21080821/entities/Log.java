package fit.se.week01_lab_anquocviet_21080821.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log")
@NamedQueries({
      @NamedQuery(name = "Log.findByAccountIdAndLogoutTime", query = "select l from Log l where l.accountId = :accountId and l.logoutTime = :logoutTime")
})
public class Log {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @Size(max = 50)
   @NotNull
   @Column(name = "account_id", nullable = false, length = 50)
   private String accountId;

   @NotNull
   @ColumnDefault("current_timestamp()")
   @Column(name = "login_time", nullable = false)
   private Instant loginTime;

   @NotNull
   @ColumnDefault("current_timestamp()")
   @Column(name = "logout_time", nullable = false)
   private Instant logoutTime;

   @Size(max = 250)
   @NotNull
   @ColumnDefault("''")
   @Column(name = "notes", nullable = false, length = 250)
   private String notes;

}