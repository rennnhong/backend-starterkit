package idv.rennnhong.backendstarterkit.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.rennnhong.backendstarterkit.service.dto.ApiDto;
import idv.rennnhong.backendstarterkit.service.dto.RoleDto;
import idv.rennnhong.backendstarterkit.service.ApiService;
import idv.rennnhong.backendstarterkit.service.RoleService;
import idv.rennnhong.backendstarterkit.service.UserService;
import idv.rennnhong.common.response.ErrorMessageBody;
import idv.rennnhong.common.response.ErrorMessages;
import idv.rennnhong.common.response.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserApiAuthorizationFilter extends GenericFilterBean {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ApiService apiService;

    String API_PREFIX = "";
    Boolean API_AUTH = false;

    //若無法使用PUT、DELETE，用此header替代
    private static final String X_HTTP_METHOD_OVERRIDE_HEADER = "X-HTTP-Method-Override";

    public UserApiAuthorizationFilter(Boolean apiAuth, String api_prefix) {
        this.API_AUTH = apiAuth;
        this.API_PREFIX = api_prefix;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURI();

        if (!this.API_AUTH) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (requestUrl.startsWith(this.API_PREFIX + "/auth/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (requestUrl.startsWith(this.API_PREFIX)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User principal = (User) authentication.getPrincipal();
            Set<String> roleCodes = principal.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toSet());
            Set<RoleDto> roleByCodes = roleService.getRoleByCodes(roleCodes);
            List<UUID> roleIds = roleByCodes.stream().map(roleDto -> roleDto.getId()).collect(Collectors.toList());
            String path = requestUrl.replace(this.API_PREFIX, "");
            HttpMethod httpMethod = getHttpMethod(request);
            ApiDto requestApi = apiService.getRestFulApi(path, httpMethod);
            boolean isAccessible = apiService.isAccessibleByRoles(roleIds, requestApi);
            if (!isAccessible) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                ErrorMessageBody errorMessageBody = ResponseBody.newErrorMessageBody(ErrorMessages.FORBIDDEN_API_NO_AUTH);
                ObjectMapper om = new ObjectMapper();
                String message = om.writeValueAsString(errorMessageBody);
                servletResponse.getWriter().write(message);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private HttpMethod getHttpMethod(HttpServletRequest request) {
        String headerValue = request.getHeader(X_HTTP_METHOD_OVERRIDE_HEADER);
        String httpMethod = StringUtils.hasLength(headerValue) ? headerValue : request.getMethod();
        return HttpMethod.resolve(httpMethod.toUpperCase().toUpperCase(Locale.ENGLISH));
    }
}
