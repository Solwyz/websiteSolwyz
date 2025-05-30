package com.solwyz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.solwyz.entity.Audit;
import com.solwyz.repo.AuditRepository;
import org.springframework.data.domain.Sort;

@Service
public class AuditService {
	
	@Autowired
	private AuditRepository auditRepository;
	

	@Autowired
	private NotificationService notificationService;

	public Audit createAudit(Audit audit) {
	    notificationService.createNotification("You have received a new website audit request");
	    return auditRepository.save(audit);
	}



	public List<Audit> getAllAudit() {
		
		return auditRepository.findAll();
	}


	public Audit getById(Long id) {
		
		return auditRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("audit not found" + id));
	}


	public void deleteAudit(Long id) {
		  if (!auditRepository.existsById(id)) {
	            throw new RuntimeException("audit not found with id: " + id);
	        }
		  auditRepository.deleteById(id);
	    }


	public Page<Audit> getAllAudit(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending()); 
	    return auditRepository.findAll(pageable);
	}

	}

