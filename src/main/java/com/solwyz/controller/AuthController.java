package com.solwyz.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solwyz.config.CurrentUser;
import com.solwyz.config.JwtTokenUtil;
import com.solwyz.entity.RefreshToken;
import com.solwyz.entity.User;
import com.solwyz.pojo.request.AuthenticationRequest;
import com.solwyz.pojo.request.ResetPasswordRequest;
import com.solwyz.pojo.response.AuthenticationResponse;
import com.solwyz.pojo.response.MessageResponse;
import com.solwyz.repo.UserRepository;
import com.solwyz.service.RefreshTokenService;
import com.solwyz.service.UserService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private UserService userService;
	
	
	@PostMapping(value = "/login", produces = "application/json")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
	    String username = authenticationRequest.getUsername();
	    String rawPassword = authenticationRequest.getPassword();

	    // Hardcoded credentials
	    String allowedUsername = "Admin";
	    String allowedPassword = "Admin@2025";

	    if (!allowedUsername.equals(username) || !allowedPassword.equals(rawPassword)) {
	        throw new Exception("Incorrect username or password");
	    }

	    User user = new User();
	    user.setEmail(username);
	    user.setPassword(passwordEncoder.encode(rawPassword));  

	    CurrentUser currentUser = new CurrentUser(user);
	    String jwt = jwtTokenUtil.generateToken(currentUser);
	    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

	    return ResponseEntity.ok(new AuthenticationResponse(jwt, refreshToken.getToken()));
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<MessageResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
	    String username = resetPasswordRequest.getUsername();

	    Optional<User> user = userRepository.findByUsername(username);

	    if (user.isEmpty()) {
	    
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(new MessageResponse("User not found with the provided username"));
	    }

	    String token = UUID.randomUUID().toString();
	    userService.createPasswordResetTokenForUser(user.get(), token);
	    userService.sendResetTokenEmail(token, user.get());

	   
	    return ResponseEntity.ok(new MessageResponse("Reset Password Link Sent to Registered Email or Mobile"));
	}

 

	@PostMapping("/signout")
	public ResponseEntity<String> signOut() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {

			SecurityContextHolder.clearContext();
		}

		return ResponseEntity.ok("You have been signed out successfully.");
	}
}


