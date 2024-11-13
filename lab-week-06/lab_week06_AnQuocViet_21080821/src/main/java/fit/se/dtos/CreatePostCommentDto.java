package fit.se.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link fit.se.entities.PostComment}
 */
public record CreatePostCommentDto(
      @NotNull @Size(max = 100) String title,
      String content
) implements Serializable {
}