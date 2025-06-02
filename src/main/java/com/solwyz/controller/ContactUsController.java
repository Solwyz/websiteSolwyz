package com.solwyz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solwyz.entity.ContactUs;
import com.solwyz.pojo.response.ApiResponse;
import com.solwyz.repo.AuditRepository;
import com.solwyz.repo.ContactUsRepository;
import com.solwyz.service.ContactUsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contact")
public class ContactUsController {
	
	
	@Autowired
	private ContactUsService contactUsService;
	
	@Autowired 
	private ContactUsRepository contactRepo;
	
	@Autowired
	private AuditRepository auditRepo;
	
	
	@PostMapping("/create")
	public ResponseEntity<ContactUs>addContact(@RequestBody ContactUs contact){
		return  ResponseEntity.ok(contactUsService.addContact(contact));
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<ContactUs>>>getAllContacts(){
		List<ContactUs>contactUs=contactUsService.getAllContacts();
		return ResponseEntity.ok(new ApiResponse<>("success",contactUs));
	}
	
	@GetMapping("/page/all")
	public ResponseEntity<ApiResponse<Page<ContactUs>>> getAllContacts(
	        @RequestParam(defaultValue = "0") int page) {

	    int size = 6; // fixed page size
	    Page<ContactUs> contactUsPage = contactUsService.getAllContacts(page, size);
	    return ResponseEntity.ok(new ApiResponse<>("success", contactUsPage));
	}

	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse<String>> deleteContact(@PathVariable Long id) {
	    contactUsService.deleteContact(id);
	    return ResponseEntity.ok(new ApiResponse<>("Contact deleted successfully", null));
	}
	
//	@GetMapping("/count")
//	public ResponseEntity<Long> getEnquiryCount() {
//	    return ResponseEntity.ok(contactUsService.getEnquiryCount());
//	}

	@GetMapping("/counts")
	public ResponseEntity<Map<String, Long>> getCounts() {
	    Map<String, Long> counts = new HashMap<>();
	    counts.put("contactEnquiryCount", contactRepo.count());
	    counts.put("websiteAuditCount", auditRepo.count());
	    return ResponseEntity.ok(counts);
	}
	
	
	@GetMapping("/filter-by-date")
	public ResponseEntity<ApiResponse<List<ContactUs>>> getContactsByDateRange(
	        @RequestParam String startDate,
	        @RequestParam String endDate) {

	    List<ContactUs> filteredContacts = contactUsService.getContactsByDateRange(startDate, endDate);
	    return ResponseEntity.ok(new ApiResponse<>("success", filteredContacts));
	}


}