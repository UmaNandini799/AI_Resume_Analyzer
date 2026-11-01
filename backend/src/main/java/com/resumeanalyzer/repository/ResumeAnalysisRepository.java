package com.resumeanalyzer.repository;
import com.resumeanalyzer.entity.ResumeAnalysis; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysis,String> { List<ResumeAnalysis> findByUserIdOrderByCreatedAtDesc(String userId); Optional<ResumeAnalysis> findByIdAndUserId(String id,String userId); long countByUserId(String userId); }
