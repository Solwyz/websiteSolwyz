package com.solwyz.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solwyz.entity.ContactUs;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long>{

	//List<ContactUs> findByCreatedDateBetween(LocalDateTime atStartOfDay, LocalDateTime atTime);

	List<ContactUs> findByCreatedAtBetween(LocalDateTime atStartOfDay, LocalDateTime atTime);

}
