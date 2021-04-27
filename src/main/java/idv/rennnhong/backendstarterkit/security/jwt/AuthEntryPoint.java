package idv.rennnhong.backendstarterkit.security.jwt;

import idv.rennnhong.common.response.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ErrorCode", ErrorMessages.AUTHENTICATION_FAILED.getCode());
        hashMap.put("ErrorMessage", ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(String.valueOf(new JSONObject(hashMap)));
    }
}