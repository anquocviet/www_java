package fit.se.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link fit.se.entities.Post}
 */
public record PostDto(
      Long id,
      @NotNull @Size(max = 75) String title,
      @Size(max = 100) String metaTitle,
      String summary,
      @NotNull Boolean published,
      @NotNull Instant createdAt,
      Instant updatedAt,
      Instant publishedAt,
      String content,
      Long authorId,
      String authorName,
      Long parentId
) implements Serializable {
}