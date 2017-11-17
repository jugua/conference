package domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	List<Comment> findAllByUserId(Long id);
	
	List<Comment> findAllByTalkId(Long id);

	List<Comment> findAllByTalkIdAndUserId(Long talkId, Long userId);
	
}
