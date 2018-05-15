package service.businesslogic.api;

import service.businesslogic.dto.LoginDto;

public interface SignInService {

    /**
     * This method should be part of Security/User service and probably should not be exposed like this.
     *
     * @return information about logged user (i.e. name, event count so on).
     */
    @Deprecated
    LoginDto login(String email, String password);

}
