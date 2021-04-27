package idv.rennnhong.backendstarterkit.security;

import idv.rennnhong.backendstarterkit.dto.ApiDto;
import idv.rennnhong.backendstarterkit.dto.UserDto;
import idv.rennnhong.backendstarterkit.service.ApiService;
import idv.rennnhong.backendstarterkit.service.UserService;
import idv.rennnhong.common.response.ErrorMessages;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserApiAuthenticationFilter extends GenericFilterBean {

    @Autowired
    UserService userService;

    @Autowired
    ApiService apiService;

    String API_PREFIX = "";
    Boolean API_AUTH = false;

    private static final String X_HTTP_METHOD_OVERRIDE_HEADER = "X-HTTP-Method-Override";

    public UserApiAuthenticationFilter(Boolean apiAuth, String api_prefix) {
        this.API_AUTH = apiAuth;
        this.API_PREFIX = api_prefix;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestUrl = request.getRequestURI();
        String httpMethod = request.getMethod();

        if (!this.API_AUTH) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (requestUrl.startsWith(this.API_PREFIX + "/auth/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (requestUrl.startsWith(this.API_PREFIX)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            Object credentials = authentication.getCredentials();
            Object details = authentication.getDetails();
            Object principal = authentication.getPrincipal();
            UserDto user = userService.getUserByAccount(name);
            String replaceUrl = requestUrl.replace(this.API_PREFIX, "");
            String headerValue = request.getHeader(X_HTTP_METHOD_OVERRIDE_HEADER);
            if (StringUtils.hasLength(headerValue)) {
                httpMethod = headerValue.toUpperCase(Locale.ENGLISH);
            }

            List<UUID> roleIds = user.getRoles().stream().map(roleId -> UUID.fromString(roleId)).collect(Collectors.toList());
            List<ApiDto> apiAccessList = apiService.getAllApiByRoles(roleIds, replaceUrl, httpMethod);
            Integer noAuth = 0;
            if (apiAccessList.size() == noAuth) {
                HashMap hashMap = new HashMap();
                hashMap.put("ErrorCode", ErrorMessages.FORBIDDEN_API_NO_AUTH.getCode());
                hashMap.put("ErrorMessage", ErrorMessages.FORBIDDEN_API_NO_AUTH.getErrorMessage());
                ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
                servletResponse.getWriter().write(String.valueOf(new JSONObject(hashMap)));
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
