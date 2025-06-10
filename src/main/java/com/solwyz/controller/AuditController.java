package com.solwyz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.solwyz.entity.Audit;
import com.solwyz.pojo.response.ApiResponse;
import com.solwyz.service.AuditService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/audit")
@Tag(name = "audit Authentication", description = "APIs for website Audit")
public class AuditController {
	
	@Autowired
	private AuditService auditService;
	
	@PostMapping("/create")
	public ResponseEntity<Audit>createAudit(@RequestBody Audit audit){
		return ResponseEntity.ok(auditService.createAudit(audit));
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<Audit>>>getAllAudit(){
		List<Audit>audit=auditService.getAllAudit();
		
		 return ResponseEntity.ok(new ApiResponse<>("success", audit));
		
	}
	@GetMapping("page/all")
	public ResponseEntity<ApiResponse<Page<Audit>>> getAllAudit(
	        @RequestParam(defaultValue = "0") int page) {

	    int size = 6; // fixed page size
	    Page<Audit> audits = auditService.getAllAudit(page, size);

	    return ResponseEntity.ok(new ApiResponse<>("success", audits));
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Audit>getById(@PathVariable Long id){
		return ResponseEntity.ok(auditService.getById(id));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String>deteteAudit(@PathVariable Long id){
		auditService.deleteAudit(id);
		 return ResponseEntity.ok("Audit deleted successfully");
		
	}
	


}
