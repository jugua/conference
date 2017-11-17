package service.businesslogic.api;

import java.util.List;

import service.businesslogic.dto.CommentDto;

public interface CommentService {

	Long save(CommentDto comment);
	
	CommentDto findById(Long id);
	
	void update(CommentDto comment);
	
	void delete(CommentDto comment);
	
	List<CommentDto> getAllByUserId(Long id);
	
	List<CommentDto> getAllByTalkId(Long id);
	
	List<CommentDto> getAllByTalkIdAndUserId(Long talkId, Long userId);
	
}
