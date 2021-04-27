//package idv.rennnhong.backendstarterkit.security;
//
//import idv.rennnhong.backendstarterkit.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// * @author wei-xiang
// * @version 1.0
// * @date 2020/10/15
// */
//@Component
//public class AuthenticationProviderImpl implements AuthenticationProvider {
//
//    @Autowired
//    UserService userService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication)
//        throws AuthenticationException {
//        String account = (String) authentication.getPrincipal();
//        String password = (String) authentication.getCredentials();
//
//        UserDetails userDetails = userService.loadUserDetailByUserId(account);
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//
//        if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
//            return new UsernamePasswordAuthenticationToken(userDetails, null,
//                userDetails.getAuthorities());
//        }
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
