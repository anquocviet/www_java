package fit.se.repositories;

import fit.se.entities.PostComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostCommentRepository extends CrudRepository<PostComment, Long> {
   @Query("select p from PostComment p where p.parent is null and p.post.id = ?1 order by p.post.publishedAt DESC")
   List<PostComment> findByParentNullAndPost_IdOrderByPost_PublishedAtDesc(Long postId);
}