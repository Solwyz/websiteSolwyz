package com.solwyz.entity;




import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "t_users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String resetToken;
	private Date resetTokenExpiryDate;
	
	private String username;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public Date getResetTokenExpiryDate() {
		return resetTokenExpiryDate;
	}

	public void setResetTokenExpiryDate(Date resetTokenExpiryDate) {
		this.resetTokenExpiryDate = resetTokenExpiryDate;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User(Long id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

    
}


