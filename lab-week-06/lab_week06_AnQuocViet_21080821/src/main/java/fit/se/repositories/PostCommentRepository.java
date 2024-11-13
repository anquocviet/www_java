package fit.se.repositories;

import fit.se.entities.PostComment;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface PostCommentRepository extends CrudRepository<PostComment, Long> {
   List<PostComment> findByPostId(Long postId);
}