package service.businesslogic.api;

import service.businesslogic.dto.LoginDto;

public interface SignInService {

    public LoginDto login(String email, String password);

}
