package ua.rd.cm.web.security;

import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    public static final String DEFAULT_UNAUTHORIZED_MSG = "unauthorized";

    @Setter
    private int errorCode = HttpServletResponse.SC_UNAUTHORIZED;
    @Setter
    private String message = DEFAULT_UNAUTHORIZED_MSG;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        response.setStatus(errorCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().append(String.format("{\"error\":\"%s\"}", message));
    }
}
