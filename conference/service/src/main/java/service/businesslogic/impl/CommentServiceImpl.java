package service.businesslogic.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.model.Comment;
import domain.repository.CommentRepository;
import domain.repository.TalkRepository;
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
	@Autowired
	private TalkRepository TalkRepository;

	@Override
	public Long save(CommentDto commentDto) {
		Comment comment = commentDtoToComment(commentDto);
		commentRepository.save(comment);
		return comment.getId();
	}

	@Override
	public CommentDto findById(Long id) {
		Comment comment = commentRepository.findOne(id);
		CommentDto commentDto = commentToCommentDto(comment);
		return commentDto;
	}

	@Override
	public void update(CommentDto commentDto) {
		Comment comment = commentDtoToComment(commentDto);
		commentRepository.save(comment);
	}

	@Override
	public void delete(CommentDto commentDto) {
		Comment comment = commentDtoToComment(commentDto);
		commentRepository.delete(comment);
	}

	@Override
	public List<CommentDto> getAllByTalkId(Long id) {
		List<Comment> comments = commentRepository.findAllByTalkId(id);
		return listCommentToListCommentDto(comments);
	}

	private CommentDto commentToCommentDto(Comment comment) {
		Long id = comment.getId();
		String message = comment.getMessage();
		Long userId = comment.getUser().getId();
		Long talkId = comment.getTalk().getId();
		LocalDateTime time = comment.getTime();
		return new CommentDto(id, message, userId, talkId, time);
	}

	private Comment commentDtoToComment(CommentDto commentDto) {
		return Comment.builder().id(commentDto.getId()).message(commentDto.getMessage())
				.user(userRepository.findOne(commentDto.getUserId()))
				.talk(TalkRepository.findOne(commentDto.getTalkId())).time(commentDto.getTime()).build();
	}

	private List<CommentDto> listCommentToListCommentDto(List<Comment> comments) {
		List<CommentDto> commentsDto = comments.stream().map(
				m -> new CommentDto(m.getId(), m.getMessage(), m.getUser().getId(), m.getTalk().getId(), m.getTime()))
				.collect(Collectors.toList());
		return commentsDto;
	}

}
