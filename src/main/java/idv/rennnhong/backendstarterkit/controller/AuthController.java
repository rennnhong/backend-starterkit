package idv.rennnhong.backendstarterkit.controller;


import com.google.common.collect.Maps;
import idv.rennnhong.backendstarterkit.controller.request.login.LoginRequestDto;
import idv.rennnhong.backendstarterkit.security.WebSecurityConfig;
import idv.rennnhong.backendstarterkit.security.jwt.JwtUtils;
import idv.rennnhong.backendstarterkit.web.validation.BindingResultWrapper;
import idv.rennnhong.common.response.ResponseBody;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

import static idv.rennnhong.common.response.ErrorMessages.AUTHENTICATION_FAILED;
import static idv.rennnhong.common.response.ErrorMessages.INVALID_FIELDS_REQUEST;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    @ApiOperation("使用者登入")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto,
                                   HttpServletResponse httpServletResponse,
                                   BindingResult bindingResult) {
        BindingResultWrapper bindingResultWrapper = new BindingResultWrapper(bindingResult);
        if (bindingResultWrapper.hasErrors()) {
            Object errorMap = bindingResultWrapper.asHashMap();
            return new ResponseEntity(
                    ResponseBody.newErrorMessageBody(INVALID_FIELDS_REQUEST, errorMap),
                    HttpStatus.BAD_REQUEST);
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getUsername(), loginRequestDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.createToken(authentication, false);
            httpServletResponse.addHeader(WebSecurityConfig.AUTHORIZATION_HEADER, token);
            Map<String, String> data = Maps.newHashMap();
            data.put("token", token);
            return ResponseEntity.ok(ResponseBody.newSingleBody(data));
        } catch (BadCredentialsException authentication) {
            log.debug(authentication.getMessage());
        }
        return new ResponseEntity(
                ResponseBody.newErrorMessageBody(AUTHENTICATION_FAILED),
                HttpStatus.UNAUTHORIZED);
    }
}
