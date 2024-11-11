package fit.se.repositories;

import fit.se.entities.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
   boolean existsByEmail(@Size(max = 50) String email);

   boolean existsByMobile(@Size(max = 15) String mobile);

   Optional<User> findByEmail(@NotNull String email);

   @Transactional
   @Modifying
   @Query("update User u set u.lastLogin = :lastLogin where u.id = :id")
   int updateLastLoginById(@Param("lastLogin") Instant lastLogin, @Param("id") Long id);

}