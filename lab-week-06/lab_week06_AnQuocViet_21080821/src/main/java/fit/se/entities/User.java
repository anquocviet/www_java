package fit.se.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @Size(max = 50)
   @Column(name = "firstName", length = 50)
   private String firstName;

   @Size(max = 50)
   @Column(name = "middleName", length = 50)
   private String middleName;

   @Size(max = 50)
   @Column(name = "lastName", length = 50)
   private String lastName;

   @Size(max = 15)
   @Column(name = "mobile", length = 15)
   private String mobile;

   @Size(max = 50)
   @Column(name = "email", length = 50)
   private String email;

   @Size(max = 32)
   @NotNull
   @Column(name = "passwordHash", nullable = false, length = 32)
   private String passwordHash;

   @NotNull
   @Column(name = "registeredAt", nullable = false)
   private Instant registeredAt;

   @Column(name = "lastLogin")
   private Instant lastLogin;

   @Lob
   @Column(name = "intro")
   private String intro;

   @Lob
   @Column(name = "profile")
   private String profile;

   @OneToMany(mappedBy = "author")
   private Set<Post> posts = new LinkedHashSet<>();

}