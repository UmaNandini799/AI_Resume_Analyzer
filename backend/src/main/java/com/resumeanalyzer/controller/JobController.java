package com.resumeanalyzer.controller;
import com.resumeanalyzer.entity.JobDescription;import com.resumeanalyzer.repository.JobDescriptionRepository;import jakarta.validation.Valid;import jakarta.validation.constraints.NotBlank;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/jobs")
public class JobController{
 private final JobDescriptionRepository repo;public JobController(JobDescriptionRepository r){repo=r;}
 public record JobRequest(@NotBlank String jobTitle,String companyName,@NotBlank String description){}
 @PostMapping public JobDescription create(@Valid @RequestBody JobRequest q,Authentication a){JobDescription j=new JobDescription();j.setUserId(a.getName());j.setJobTitle(q.jobTitle());j.setCompanyName(q.companyName());j.setDescription(q.description());return repo.save(j);}
 @GetMapping public List<JobDescription> all(Authentication a){return repo.findByUserIdOrderByCreatedAtDesc(a.getName());}
 @GetMapping("/{id}") public JobDescription one(@PathVariable String id,Authentication a){return repo.findByIdAndUserId(id,a.getName()).orElseThrow(()->new NoSuchElementException("Job not found"));}
 @DeleteMapping("/{id}") public void delete(@PathVariable String id,Authentication a){repo.delete(one(id,a));}
}
