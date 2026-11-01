package com.resumeanalyzer.repository;
import com.resumeanalyzer.entity.JobDescription; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface JobDescriptionRepository extends JpaRepository<JobDescription,String> { List<JobDescription> findByUserIdOrderByCreatedAtDesc(String userId); Optional<JobDescription> findByIdAndUserId(String id,String userId); }
