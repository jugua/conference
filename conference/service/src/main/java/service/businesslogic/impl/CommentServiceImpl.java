package service.businesslogic.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.model.Comment;
import domain.model.User;
import domain.repository.CommentRepository;
import domain.repository.UserRepository;
import service.businesslogic.api.CommentService;
import service.businesslogic.dto.CommentDto;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Long save(CommentDto commentDto) {
        Comment comment = toEntity(commentDto);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    public CommentDto findById(Long id) {
        Comment comment = commentRepository.findOne(id);
        CommentDto commentDto = toDto(comment);
        return commentDto;
    }

    @Override
    public void update(CommentDto commentDto) {
        Comment comment = toEntity(commentDto);
        commentRepository.save(comment);
    }

    @Override
    public void delete(CommentDto commentDto) {
        Comment comment = toEntity(commentDto);
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getAllByTalkId(Long id) {
        List<Comment> comments = commentRepository.findAllByTalkId(id);
        return toDto(comments);
    }

    private Comment toEntity(CommentDto commentDto) {
        User one = userRepository.findOne(commentDto.getUserId());
        return Comment.builder()
                .id(commentDto.getId())
                .message(commentDto.getMessage())
                .user(one)
                .talkId(commentDto.getTalkId())
                .time(commentDto.getTime())
                .build();
    }

    private CommentDto toDto(Comment comment) {
        User user = comment.getUser();
        return CommentDto.builder()
                .id(comment.getId())
                .talkId(comment.getTalkId())
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .message(comment.getMessage())
                .time(comment.getTime())
                .build();
    }

    private List<CommentDto> toDto(List<Comment> comments) {
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
