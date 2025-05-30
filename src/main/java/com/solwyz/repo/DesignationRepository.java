package com.solwyz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solwyz.entity.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long>{

	List<Designation> findByDepartmentId(Long departmentId);

}
