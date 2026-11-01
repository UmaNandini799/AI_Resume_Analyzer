package com.resumeanalyzer.repository;
import com.resumeanalyzer.entity.Resume; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface ResumeRepository extends JpaRepository<Resume,String> { List<Resume> findByUserIdOrderByCreatedAtDesc(String userId); Optional<Resume> findByIdAndUserId(String id,String userId); }
