package service.businesslogic.dto;

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
public class ConferenceDateDto {

	private String startDate;
	private String endDate;
	private String cfpStartDate;
	private String cfpEndDate;
	private String notificationDue;

}
