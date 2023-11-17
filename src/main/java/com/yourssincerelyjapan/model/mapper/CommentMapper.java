package com.yourssincerelyjapan.model.mapper;

import com.yourssincerelyjapan.model.dto.CommentDTO;
import com.yourssincerelyjapan.model.dto.index.GetCommentDTO;
import com.yourssincerelyjapan.model.entity.Comment;
import com.yourssincerelyjapan.utils.LocalDateTimeMapper;
import com.yourssincerelyjapan.utils.LocalDateTimeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LocalDateTimeMapper.class)
public interface CommentMapper {

    @Mapping(target = "createdOn", qualifiedBy = LocalDateTimeMapping.class)
    Comment commentDtoToComment(CommentDTO commentDTO);

    GetCommentDTO commentToGetCommentDto(Comment comment);
}
