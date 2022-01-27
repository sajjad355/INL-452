package com.internal.service.somruinternal.controller2;

import com.internal.service.somruinternal.config.SecurityConfig;
import com.internal.service.somruinternal.dto.AuthInput;
import com.internal.service.somruinternal.dto.AuthOutput;
import com.internal.service.somruinternal.dto.SomruUserDetailsService;
import com.internal.service.somruinternal.error.ForbiddenException;
import com.internal.service.somruinternal.error.UnauthorizedException;
import com.internal.service.somruinternal.model2.UserV2;
import com.internal.service.somruinternal.repository2.LoginRepositoryV2;
import com.internal.service.somruinternal.utils.SendEmailUtil;
import com.internal.service.somruinternal.utils.JWTUtil;
import com.internal.service.somruinternal.utils.JwtTokenUtilV2;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/loginV2")
public class LoginControlV2 extends ParentControl {

  private final static Logger LOGGER = LoggerFactory.getLogger(LoginControlV2.class);

  @Autowired
  AuthenticationManager authManager;

  @Autowired
  SomruUserDetailsService userDetailService;

  @Autowired
  JWTUtil jwtUtil;

  @Autowired
  LoginRepositoryV2 loginRepository;

  @Autowired
  JwtTokenUtilV2 tokenUtil;

  @Autowired
  SendEmailUtil sendEmailUtil;

  @Autowired
  SecurityConfig securityConfig;

  @PostMapping("/authenticate")
  AuthOutput authenticate(@RequestBody AuthInput data) throws AuthenticationException {
    String token = "";
    AuthOutput output = null;

    try {
      UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
      Authentication authentication = authManager.authenticate(authReq);
      UserV2 user = loginRepository.searchSingleUserByEmail(data.getUsername());
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      token = jwtUtil.generateToken(userDetails);

      // reset invalid login count to 0
      if (user.getInvalidlogincount() > 0) {
        user.setInvalidlogincount(0);
        loginRepository.save(user);
      }
      user.getUserRoles().forEach(
        role -> Hibernate.initialize(role.getOperations())
      );
      output = new AuthOutput(user, token);
      super.saveUserHistory(user.getName(), "Login", "User authentication", "User logged on");
    } catch (AuthenticationException ex) {
      handleAuthenticationException(ex, data);
    }

    return output;
  }

  @GetMapping("/requestResetPassword")
  public boolean requestResetPassword(@RequestParam(value = "email") String email) {
    boolean result = false;
    UserV2 user = loginRepository.searchSingleUserByEmail(email);

    final UserDetails USER_DETAILS = userDetailService.loadUserByUsername(email);
    final String JWT_STRING = jwtUtil.generateToken(USER_DETAILS);

    if (user != null) {
      String userEmail = user.getEmail();
//            // Send email
      try {
        sendEmailUtil.sendResetPasswordEmail(userEmail, JWT_STRING);
        result = true;
      } catch (MessagingException e) {
        LOGGER.warn("An exception occurred while sending the password reset email.", e);
      }
    }
    return result;
  }

  @GetMapping("/verifyToken/{token}")
  public HashMap<String, Object> verifyToken(@PathVariable(value = "token") String token) {
    String result
      = "This reset password link is either malformed, expired, or has already been used. "
      + "Please request another password reset to continue.";

    UserV2 user = loginRepository.searchSingleUserByEmail(jwtUtil.extractUsername(token));

//        Claims claims = tokenUtil.decode(token);
    HashMap<String, Object> returnValue = new LinkedHashMap<>();
//        if (claims != null) {
//            String userID = claims.get("userID").toString();
//            String userPassword = claims.get("userPassword").toString();
//            // Get UserV2 instance
//            UserV2 user = loginRepository.findOne(Long.parseLong(userID));
    if (user == null) {
      result = "The user does not exist.";
    } else {
//                if (userPassword.equals(user.getPassword())) {
      result = "" + user.getUserId();
//                }
    }
//        }
    returnValue.put("result", result);
    return returnValue;
  }

  @PutMapping("/updatePassword/{id}/{password}")
  public ResponseEntity<AuthOutput> updatePassword(@PathVariable(value = "id") Long userID,
                                                   @PathVariable(value = "password") String updatedPassword) {
    UserV2 user = loginRepository.findByUserId(userID);

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    PasswordEncoder passwordEncoder = securityConfig.getPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(updatedPassword);
    user.setPassword(encodedPassword);

    UserV2 newUser = loginRepository.save(user);
    newUser.getUserRoles().forEach(
      role -> Hibernate.initialize(role.getOperations())
    );

    final UserDetails USER_DETAILS = userDetailService.loadUserByUsername(newUser.getEmail());
    final String JWT_STRING = jwtUtil.generateToken(USER_DETAILS);

    return ResponseEntity
      .ok(new AuthOutput(user, JWT_STRING));
  }

  @PutMapping("/updateInvalidLoginCount/{id}/{count}")
  public ResponseEntity<UserV2> updateInvalidLoginCount(@PathVariable(value = "id") Long userID,
                                                        @PathVariable(value = "count") Long updatedCount) {
    UserV2 user = loginRepository.findByUserId(userID);

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    user.setInvalidlogincount(updatedCount);
    UserV2 newUser = loginRepository.save(user);
    return ResponseEntity.ok(newUser);
  }

  @PutMapping("/updateStatus/{id}/{status}")
  public ResponseEntity<UserV2> updateUserStatus(@PathVariable(value = "id") Long userID,
                                                 @PathVariable(value = "status") boolean updatedStatus) {
    UserV2 user = loginRepository.findByUserId(userID);

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    user.setActive(updatedStatus);
    UserV2 newUser = loginRepository.save(user);
    return ResponseEntity.ok(newUser);
  }

  private void handleAuthenticationException(AuthenticationException ex, AuthInput data) throws UnauthorizedException, ForbiddenException {
    UserV2 user = loginRepository.searchSingleUserByEmail(data.getUsername());

    if (ex instanceof BadCredentialsException) {
      if (user != null) updateLoginAttempts(user);

      throw new UnauthorizedException("Incorrect email or password", ex);
    }

    if (ex instanceof DisabledException) {
      super.saveUserHistory(user.getName(), "Login", "User authentication", "User login failure - account is inactive");
      throw new ForbiddenException("User account is inactive", ex);
    }

    throw ex;
  }

  private void updateLoginAttempts(UserV2 user) {
    if (user != null) {
      long failedLogins = user.getInvalidlogincount();
      failedLogins++;
      LOGGER.info(String.format("user %s now has %d failed logins", user.getName(), failedLogins));
      user.setInvalidlogincount(failedLogins);
      if (failedLogins > 3) {
        user.setActive(false);
      }
      loginRepository.save(user);
    }
  }
}
