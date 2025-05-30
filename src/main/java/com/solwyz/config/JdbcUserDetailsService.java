package com.solwyz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.solwyz.entity.User;
import com.solwyz.repo.UserRepository;

@Service
public class JdbcUserDetailsService {
	   @Autowired
	    private UserRepository userRepo;

	    @Autowired
	    private PasswordEncoder passwordEncoder; 

	    

	        public CurrentUser loadUserByUsernameAndPass(String username, String rawPassword) throws Exception {
	            User user = userRepo.findByEmail(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	            if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
	                throw new BadCredentialsException("Invalid password");
	            }

	            return new CurrentUser(user); // Make sure this constructor exists
	        }
	    }


