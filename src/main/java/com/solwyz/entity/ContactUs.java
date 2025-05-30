package com.solwyz.entity;



import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class ContactUs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String company;
	private String websiteLink;
	private String service;
	private long budget;
	private String message;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	 @PrePersist
    protected void onCreate() {
	        this.createdAt = LocalDateTime.now();
	    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getWebsiteLink() {
		return websiteLink;
	}
	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public long getBudget() {
		return budget;
	}
	public void setBudget(long budget) {
		this.budget = budget;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public ContactUs() {
		super();
		
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	
	

}
