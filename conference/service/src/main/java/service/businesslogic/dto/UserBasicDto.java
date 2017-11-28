package service.businesslogic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserBasicDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

}
