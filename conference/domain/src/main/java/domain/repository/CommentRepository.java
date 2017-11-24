package domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import domain.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	List<Comment> findAllByTalkId(Long id);
	
}
