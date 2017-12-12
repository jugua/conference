package service.businesslogic.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Submission {

    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    private String title;

    private Long speakerId;

    private Long conferenceId;

    private String conferenceName;

    private String name;

    @NotNull
    @Size(min = 1, max = 3000)
    private String description;

    @NotNull
    @Size(min = 1, max = 255)
    private String topic;

    @NotNull
    @Size(min = 1, max = 255)
    private String type;

    @NotNull
    @Size(min = 1, max = 255)
    private String lang;

    @NotNull
    @Size(min = 1, max = 255)
    private String level;

    @Size(max = 1500)
    private String addon;

    private String status;

    private String date;

    @Size(max = 1000)
    private String comment;

    private String assignee;

    private String startDate;
    
	private String endDate;
	
	private String cfpStartDate;
	
	private String cfpEndDate;
	
	private String notificationDue;
    
}
