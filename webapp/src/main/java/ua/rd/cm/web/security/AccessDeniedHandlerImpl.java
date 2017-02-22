package ua.rd.cm.web.security;

import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    public static final String DEFAULT_FORBIDDEN_MSG = "forbidden";

    @Setter
    private int errorCode = HttpServletResponse.SC_FORBIDDEN;
    @Setter
    private String forbiddenMsg = DEFAULT_FORBIDDEN_MSG;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(errorCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().append(String.format("{\"error\":\"%s\"}", forbiddenMsg));
    }
}
