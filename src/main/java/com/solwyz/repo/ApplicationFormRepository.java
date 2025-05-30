package com.solwyz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solwyz.entity.ApplicationForm;

@Repository
public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long>{

	List<ApplicationForm> findByDesignationId(Long designationId);

}
