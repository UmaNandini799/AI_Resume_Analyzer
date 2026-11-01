package com.resumeanalyzer.controller;
import com.resumeanalyzer.entity.JobApplication;import com.resumeanalyzer.repository.JobApplicationRepository;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/applications")
public class ApplicationController{
 private final JobApplicationRepository repo;public ApplicationController(JobApplicationRepository r){repo=r;}
 @PostMapping public JobApplication create(@RequestBody JobApplication q,Authentication a){q.setUserId(a.getName());return repo.save(q);}
 @GetMapping public List<JobApplication> all(Authentication a){return repo.findByUserIdOrderByCreatedAtDesc(a.getName());}
 @PutMapping("/{id}") public JobApplication update(@PathVariable String id,@RequestBody JobApplication q,Authentication a){JobApplication old=repo.findByIdAndUserId(id,a.getName()).orElseThrow(()->new NoSuchElementException("Application not found"));old.setJobTitle(q.getJobTitle());old.setCompanyName(q.getCompanyName());old.setApplicationDate(q.getApplicationDate());old.setApplicationStatus(q.getApplicationStatus());old.setNotes(q.getNotes());old.setResumeId(q.getResumeId());return repo.save(old);}
 @DeleteMapping("/{id}") public void delete(@PathVariable String id,Authentication a){repo.delete(repo.findByIdAndUserId(id,a.getName()).orElseThrow(()->new NoSuchElementException("Application not found")));}
}
