package com.solwyz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solwyz.entity.JobDetails;

@Repository
public interface JobDetailsRepository extends JpaRepository<JobDetails, Long>{

}
