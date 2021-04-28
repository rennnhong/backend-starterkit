package idv.rennnhong.backendstarterkit.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {

    @Autowired
    private UserDetailsService userDetailsService;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String username = "";
    private final Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);
    private String secretKey;
    private long tokenValidityInMilliseconds;
    private long tokenValidityInMillisecondsForRememberMe;

    /**
     * 初始 Token 資料
     */
    @PostConstruct
    public void init() {
        this.secretKey = "Rennnhong";
        int secondIn1day = 1000 * 60 * 60 * 24;
        this.tokenValidityInMilliseconds = secondIn1day * 2L;
        this.tokenValidityInMillisecondsForRememberMe = secondIn1day * 7L;
//
//        this.tokenValidityInMilliseconds = 1000 * 60 * 10;
//        this.tokenValidityInMillisecondsForRememberMe = 1000 * 60 * 10;
    }

    /**
     * 建立 Token
     *
     * @param authentication
     * @param rememberMe
     * @return
     */
    public String createToken(Authentication authentication, Boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }
        else {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        }

        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    /**
     * 取得使用者驗證資訊
     *
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        String jwtAuthentication = claims.get(AUTHORITIES_KEY).toString();
        boolean isTokenHaveAuthData = "".equals(jwtAuthentication);
        if (isTokenHaveAuthData) {
            return null;
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(jwtAuthentication.split((",")))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * 驗證 Token
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        if (token != null) {
            try {
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                return true;
            }
            catch (SignatureException e) {
                log.debug("Invalid JWT signature.");
                log.trace("Invalid JWT signature trace: {}", e);
            }
            catch (MalformedJwtException e) {
                log.debug("Invalid JWT token.");
                log.trace("Invalid JWT token trace: {}", e);
            }
            catch (ExpiredJwtException e) {
                log.debug("Expired JWT token.");
                log.trace("Expired JWT token trace: {}", e);
            }
            catch (UnsupportedJwtException e) {
                log.debug("Unsupported JWT token.");
                log.trace("Unsupported JWT token trace: {}", e);
            }
            catch (IllegalArgumentException e) {
                log.debug("JWT token compact of handler are invalid.");
                log.trace("JWT token compact of handler are invalid trace: {}", e);
            }
        }
        return false;
    }
}