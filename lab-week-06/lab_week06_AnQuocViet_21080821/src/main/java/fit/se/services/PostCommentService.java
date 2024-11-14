package fit.se.services;

import fit.se.dtos.PostCommentDto;
import fit.se.entities.PostComment;
import fit.se.exceptions.AppException;
import fit.se.mappers.PostCommentMapper;
import fit.se.repositories.PostCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
@Service
public class PostCommentService {
   private final PostCommentRepository postCommentRepository;
   private final PostCommentMapper postCommentMapper;

   public PostCommentService(PostCommentRepository postCommentRepository,
                             PostCommentMapper postCommentMapper) {
      this.postCommentRepository = postCommentRepository;
      this.postCommentMapper = postCommentMapper;
   }

   public PostCommentDto save(PostCommentDto postCommentDto) {
      PostComment postComment = postCommentMapper.toEntity(postCommentDto);
      postComment = postCommentRepository.save(postComment);
      return postCommentMapper.toDto(postComment);
   }

   public PostCommentDto update(PostCommentDto postCommentDto) {
      PostComment postComment = postCommentMapper.toEntity(postCommentDto);
      if (postCommentRepository.existsById(postComment.getId())) {
         postComment = postCommentRepository.save(postComment);
         return postCommentMapper.toDto(postComment);
      }
      throw new AppException(404, "Post comment not found");
   }

   public boolean delete(Long id) {
      if (postCommentRepository.existsById(id)) {
         postCommentRepository.deleteById(id);
         return true;
      }
      throw new AppException(404, "Post comment not found");
   }

   public PostCommentDto findById(Long id) {
      PostComment postComment =
            postCommentRepository.findById(id)
                  .orElseThrow(() -> new AppException(404, "Post comment not found"));
      return postCommentMapper.toDto(postComment);
   }

   public List<PostCommentDto> findAll() {
      return StreamSupport.stream(postCommentRepository.findAll().spliterator(), false)
                   .map(postCommentMapper::toDto)
                   .toList();
   }

   public List<PostCommentDto> findByPostId(Long postId) {
      return postCommentRepository.findByPostIdOrderByPublishedAtDesc(postId)
                   .stream()
                   .map(postCommentMapper::toDto)
                   .toList();
   }
}
