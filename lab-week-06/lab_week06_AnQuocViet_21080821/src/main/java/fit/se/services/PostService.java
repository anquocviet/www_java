package fit.se.services;

import fit.se.dtos.PostDto;
import fit.se.entities.Post;
import fit.se.exceptions.AppException;
import fit.se.mappers.PostMapper;
import fit.se.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * @description
 * @author: vie
 * @date: 11/11/24
 */
@Service
public class PostService {
   private final PostRepository postRepository;
   private final PostMapper postMapper;

   public PostService(
         PostRepository postRepository,
         PostMapper postMapper
   ) {
      this.postRepository = postRepository;
      this.postMapper = postMapper;
   }

   public PostDto save(PostDto postDto) {
      Post post = postMapper.toEntity(postDto);
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

   public List<PostDto> findAll() {
      return StreamSupport.stream(postRepository.findAll().spliterator(), false)
                   .map(postMapper::toDto)
                   .toList();
   }
}
