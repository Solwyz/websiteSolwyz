package com.solwyz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solwyz.entity.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long>{

}
