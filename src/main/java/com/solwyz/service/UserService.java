package com.solwyz.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.solwyz.entity.User;
import com.solwyz.exception.GenericException;
import com.solwyz.repo.UserRepository;


@Service
public class UserService {
	
   @Autowired 
   private UserRepository userRepository;
   
    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

	@Value("${app.frontEndResetUrl}")
	private String frontEndResetUrl;

	

   public void createPasswordResetTokenForUser(User user, String token) {
	    user.setResetToken(token);
	    Date expDate = DateUtils.addMilliseconds(new Date(), 24 * 60 * 60 * 1000); // 24 hours
	    user.setResetTokenExpiryDate(expDate);
	    userRepository.save(user);
	}

   public void sendResetTokenEmail(String token, User user) {
	   
	    try {
	        final String url = frontEndResetUrl + "?token=" + token;
	        Context thymeleafContext = new Context();
	        Map<String, Object> templateModel = new HashMap<>();
	        templateModel.put("token_link", url);
	        thymeleafContext.setVariables(templateModel);
	        String htmlBody = thymeleafTemplateEngine.process("reset-password.html", thymeleafContext);

	        // Uncomment the following lines to enable email sending
	        // MimeMessage email = constructHtmlEmail("Reset Password Link", htmlBody, user);
	        // mailSender.send(email);

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new GenericException("Unable to email: " + e.getMessage());
	    }
	}


}