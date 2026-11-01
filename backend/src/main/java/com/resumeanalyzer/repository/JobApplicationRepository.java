package com.resumeanalyzer.repository;
import com.resumeanalyzer.entity.JobApplication; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface JobApplicationRepository extends JpaRepository<JobApplication,String> { List<JobApplication> findByUserIdOrderByCreatedAtDesc(String userId); Optional<JobApplication> findByIdAndUserId(String id,String userId); long countByUserId(String userId); }
