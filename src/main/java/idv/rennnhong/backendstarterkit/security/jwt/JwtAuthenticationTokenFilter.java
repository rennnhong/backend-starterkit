package idv.rennnhong.backendstarterkit.security.jwt;

import idv.rennnhong.backendstarterkit.security.WebSecurityConfig;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = resolveToken(request);
            boolean validateTokenState = this.jwtUtils.validateToken(jwt);
            if (StringUtils.hasText(jwt) && validateTokenState) {
                Authentication authentication = this.jwtUtils.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.info("Security exception for user {} - {}", e.getClaims().getSubject(),
                    e.getMessage());
            log.trace("Security exception trace: {}", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(WebSecurityConfig.AUTHORIZATION_HEADER);
        String tokenPrefix = "Bearer ";
        if (StringUtils.hasText(token) && token.startsWith(tokenPrefix)) {
            return token.substring(7, token.length());
        }
//        String jwt = request.getParameter(WebSecurityConfig.AUTHORIZATION_TOKEN);
//        if (StringUtils.hasText(jwt)) {
//            return jwt;
//        }
        return null;
    }
}
