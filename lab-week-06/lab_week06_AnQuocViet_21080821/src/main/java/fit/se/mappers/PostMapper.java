package fit.se.mappers;

import fit.se.dtos.CreatePostDto;
import fit.se.dtos.PostDto;
import fit.se.entities.Post;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PostMapper {
   Post toEntity(PostDto postDto);

   Post toEntity(CreatePostDto postDto);

   @Mapping(target = "authorName",
         expression = "java(post.getAuthor().getFirstName() + ' ' + post.getAuthor().getMiddleName() + ' ' + post.getAuthor().getLastName())")
   @Mapping(target = "parentId", expression = "java(post.getParent() != null ? post.getParent().getId() : null)")
   @Mapping(target = "authorId", expression = "java(post.getAuthor().getId())")
   PostDto toDto(Post post);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   Post partialUpdate(PostDto postDto, @MappingTarget Post post);
}
