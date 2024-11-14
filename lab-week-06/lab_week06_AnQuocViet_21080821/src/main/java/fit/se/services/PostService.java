package fit.se.services;

import fit.se.dtos.CreatePostDto;
import fit.se.dtos.PostDto;
import fit.se.entities.Post;
import fit.se.exceptions.AppException;
import fit.se.mappers.PostMapper;
import fit.se.repositories.PostRepository;
import fit.se.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
@Service
public class PostService {
   private final PostRepository postRepository;
   private final UserRepository userRepository;
   private final PostMapper postMapper;

   public PostService(
         PostRepository postRepository, UserRepository userRepository,
         PostMapper postMapper
   ) {
      this.postRepository = postRepository;
      this.userRepository = userRepository;
      this.postMapper = postMapper;
   }

   public PostDto save(CreatePostDto postDto) {
      Post post = postMapper.toEntity(postDto);
      userRepository.findById(postDto.authorId())
            .ifPresentOrElse(post::setAuthor,
                  () -> {
                     throw new AppException(404, "Author not found");
                  });
      if (postDto.parentPost().isPresent()) {
         postRepository.findById(postDto.parentPost().get())
               .ifPresentOrElse(post::setParent,
                     () -> {
                        throw new AppException(404, "Parent post not found");
                     });
      }
      post.setCreatedAt(Instant.now());
      if (postDto.published() != null && postDto.published()) {
         post.setPublishedAt(Instant.now());
      }
      post = postRepository.save(post);
      return postMapper.toDto(post);
   }

   public PostDto update(PostDto postDto) {
      if (postRepository.existsById(postDto.id())) {
         Post post = postMapper.toEntity(postDto);
         post = postRepository.save(post);
         return postMapper.toDto(post);
      }
      throw new AppException(404, "Post not found");
   }

   public boolean delete(Long id) {
      if (postRepository.existsById(id)) {
         postRepository.deleteById(id);
         return true;
      }
      throw new AppException(404, "Post not found");
   }

   public PostDto findById(Long id) {
      Post post = postRepository.findById(id)
                        .orElseThrow(() -> new AppException(404, "Post not found"));
      return postMapper.toDto(post);
   }

   public Page<PostDto> findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
      return postRepository.findPostsByPublishedTrue(pageable)
                   .map(postMapper::toDto);

   }

   public List<PostDto> findPostsByUserId(Long id, boolean isForAuthor) {
      List<Post> posts =
            isForAuthor
                  ? postRepository.findPostByAuthor_Id(id)
                  : postRepository.findPostByAuthor_IdAndPublishedTrue(id);
      return posts.stream()
                   .map(postMapper::toDto)
                   .toList();
   }

   public PostDto publishPost(Long id) {
      Post post = postRepository.findById(id)
                        .orElseThrow(() -> new AppException(404, "Post not found"));
      post.setPublished(true);
      post.setPublishedAt(Instant.now());
      postRepository.save(post);
      return postMapper.toDto(post);
   }
}
