package idv.rennnhong.backendstarterkit.controller;


import idv.rennnhong.backendstarterkit.controller.request.login.LoginRequestDto;
import idv.rennnhong.backendstarterkit.security.WebSecurityConfig;
import idv.rennnhong.backendstarterkit.security.jwt.JwtTokenUtils;
import idv.rennnhong.backendstarterkit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;

import static idv.rennnhong.common.response.ErrorMessages.AUTHENTICATION_FAILED;
import static idv.rennnhong.common.response.ErrorMessages.INVALID_FIELDS_REQUEST;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @PostMapping("/login")
    @ApiOperation("使用者登入")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto,
                                   HttpServletResponse httpServletResponse, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<Object>(INVALID_FIELDS_REQUEST.toObject(),
                    HttpStatus.BAD_REQUEST);
        }


        /*
        這段應該不需要
        Boolean loginStatus = userService
                .isLoginStatus(loginRequestDto.getAccount(), loginRequestDto.getPassword());
        if (!loginStatus) {
            return new ResponseEntity<Object>(AUTHENTICATION_FAILED.toObject(),
                    HttpStatus.UNAUTHORIZED);
        }
        */

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getUsername(), loginRequestDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtils.createToken(authentication, false);
            httpServletResponse.addHeader(WebSecurityConfig.AUTHORIZATION_HEADER, token);
            HashMap<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);

            return new ResponseEntity<Object>(responseData, HttpStatus.OK);
        } catch (BadCredentialsException authentication) {
            log.debug(authentication.getMessage());
        }
        return new ResponseEntity<Object>(AUTHENTICATION_FAILED.toObject(),
                HttpStatus.UNAUTHORIZED);
    }
}
