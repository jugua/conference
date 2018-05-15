package service.businesslogic.api;

import java.util.List;

import service.businesslogic.dto.CommentDto;

public interface CommentService {

    CommentDto getById(Long id);

    List<CommentDto> getByTalkId(Long id);

    Long save(CommentDto comment);

    void update(CommentDto comment);

    void delete(CommentDto comment);

}
