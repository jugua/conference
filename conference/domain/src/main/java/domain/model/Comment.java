package domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
public class Comment extends AbstractEntity {

	@Column(nullable = false)
	@NonNull
	private String message;
	@Column(nullable = false)
	@NonNull
	private LocalDateTime time; 	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	@NonNull
	private User user;
	@ManyToOne
	@JoinColumn(name = "talk_id", nullable = false)
	@NonNull
	private Talk talk;
	
	@Builder
	public Comment(Long id, String message, LocalDateTime time, User user, Talk talk) {
		super(id);
		this.message = message;
		this.time = time;
		this.user = user;
		this.talk = talk;
	}
	
}
