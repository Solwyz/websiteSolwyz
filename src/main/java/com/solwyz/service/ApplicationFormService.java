package com.solwyz.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solwyz.entity.ApplicationForm;
import com.solwyz.entity.Department;
import com.solwyz.entity.Designation;
import com.solwyz.repo.ApplicationFormRepository;
import com.solwyz.repo.DepartmentRepository;
import com.solwyz.repo.DesignationRepository;

@Service
public class ApplicationFormService {

    @Autowired
    private ApplicationFormRepository applicationFormRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DesignationRepository designationRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    
    
    public ApplicationForm createApplication(
            String name,
            String email,
            String phoneNo,
            LocalDate dateOfBirth,
            String highestQualification,
            Long designationId,
            Long departmentId, 
            MultipartFile resumeFile) throws IOException {

        String resumeUrl = cloudinaryService.uploadPdf(resumeFile);

        Designation designation = designationRepository.findById(designationId)
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        Department department = departmentRepository.findById(departmentId)  
                .orElseThrow(() -> new RuntimeException("Department not found"));

        ApplicationForm applicationForm = new ApplicationForm();
        applicationForm.setName(name);
        applicationForm.setEmail(email);
        applicationForm.setPhoneNo(phoneNo);
        applicationForm.setDateOfBirth(dateOfBirth);
        applicationForm.setHighestQualification(highestQualification);
        applicationForm.setResumeUrl(resumeUrl);
        applicationForm.setDesignation(designation);
        applicationForm.setDepartment(department);  

        notificationService.createNotification("New job application received");

        return applicationFormRepository.save(applicationForm);
    }


    public List<ApplicationForm> getAllApplications() {
        return applicationFormRepository.findAll();
    }
    
    public ApplicationForm getApplicationById(Long id) {
        return applicationFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + id));
    }
    
//
//    public ApplicationForm updateApplication(Long id, String name, String email, String phoneNo,
//            LocalDate dateOfBirth, String highestQualification,
//            MultipartFile resumeFile) throws IOException {
//
//ApplicationForm existing = applicationFormRepository.findById(id)
//.orElseThrow(() -> new RuntimeException("Application not found with ID: " + id));
//
//existing.setName(name);
//existing.setEmail(email);
//existing.setPhoneNo(phoneNo);
//existing.setDateOfBirth(dateOfBirth);
//existing.setHighestQualification(highestQualification);
//
//if (resumeFile != null && !resumeFile.isEmpty()) {
//existing.setResumeUrl(resumeFile.getBytes());
//}
//
//return applicationFormRepository.save(existing);
//}
    
    public void deleteApplication(Long id) {
        if (!applicationFormRepository.existsById(id)) {
            throw new RuntimeException("Application not found with ID: " + id);
        }
        applicationFormRepository.deleteById(id);
    }

    
//    public Map<String, Object> getApplicationsByDesignationId(Long designationId) {
//        List<ApplicationForm> applications = applicationFormRepository.findByDesignationId(designationId);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("count", applications.size());
//        response.put("applications", applications);
//
//        return response;
//    }


    public Map<String, Object> getApplicationsByDepartmentAndDesignation(Long departmentId, Long designationId) {
        List<ApplicationForm> applications = applicationFormRepository.findByDepartmentIdAndDesignationId(departmentId, designationId);

        Map<String, Object> response = new HashMap<>();
        response.put("count", applications.size());
        response.put("applications", applications);

        return response;
    }

}
