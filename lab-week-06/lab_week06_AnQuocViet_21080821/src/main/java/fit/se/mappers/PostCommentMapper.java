package fit.se.mappers;

import fit.se.dtos.PostCommentDto;
import fit.se.entities.PostComment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PostCommentMapper {
   PostComment toEntity(PostCommentDto postCommentDto);

   PostCommentDto toDto(PostComment postComment);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   PostComment partialUpdate(PostCommentDto postCommentDto, @MappingTarget PostComment postComment);
}
