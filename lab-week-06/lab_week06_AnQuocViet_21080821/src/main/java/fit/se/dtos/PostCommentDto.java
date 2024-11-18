package fit.se.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * DTO for {@link fit.se.entities.PostComment}
 */
public record PostCommentDto(
      Long id,
      @NotNull @Size(max = 100) String title,
      @NotNull Boolean published,
      @NotNull Instant createdAt,
      Instant publishedAt,
      String content,
      Set<PostCommentDto> postComments
) implements Serializable {
}