package com.solwyz.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.solwyz.entity.ContactUs;
import com.solwyz.repo.ContactUsRepository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class ContactUsService {
	
	@Autowired
	private ContactUsRepository contactUsRepository;
	
	@Autowired
	private NotificationService notificationService;

	public ContactUs addContact(ContactUs contact) {
	    notificationService.createNotification("New contact enquiry received");
	    return contactUsRepository.save(contact);
	}

	public List<ContactUs> getAllContacts() {
		return contactUsRepository.findAll();
	}

	 public void deleteContact(Long id) {
	        
	        if(!contactUsRepository.existsById(id)) {
	            throw new RuntimeException("Contact with id " + id + " not found");
	        }
	        contactUsRepository.deleteById(id);
	    }

	 public Page<ContactUs> getAllContacts(int page, int size) {
		    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		    return contactUsRepository.findAll(pageable);
		}

	
	 
	 public List<ContactUs> getContactsByDateRange(String startDate, String endDate) {
		   
		    LocalDate start = LocalDate.parse(startDate);
		    LocalDate end = LocalDate.parse(endDate);

		    
		    if (start.isAfter(end)) {
		        throw new IllegalArgumentException("Start date must not be after end date");
		    }

		  
		    return contactUsRepository.findByCreatedAtBetween(start.atStartOfDay(), end.atTime(LocalTime.MAX));
		}




}
