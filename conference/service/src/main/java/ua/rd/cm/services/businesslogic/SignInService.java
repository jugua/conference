package ua.rd.cm.services.businesslogic;

import ua.rd.cm.dto.LoginDto;

public interface SignInService {
	
	public LoginDto login(String email, String password);

}
