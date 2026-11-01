package com.resumeanalyzer.controller;
import com.resumeanalyzer.repository.*;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/dashboard")
public class DashboardController{
 private final ResumeRepository resumes;private final ResumeAnalysisRepository analyses;private final JobApplicationRepository apps;
 public DashboardController(ResumeRepository r,ResumeAnalysisRepository a,JobApplicationRepository j){resumes=r;analyses=a;apps=j;}
 @GetMapping("/summary") public Map<String,Object> summary(Authentication a){var list=analyses.findByUserIdOrderByCreatedAtDesc(a.getName());double avg=list.stream().mapToInt(x->x.getAtsScore()==null?0:x.getAtsScore()).average().orElse(0);return Map.of("totalResumes",resumes.findByUserIdOrderByCreatedAtDesc(a.getName()).size(),"totalAnalyses",list.size(),"averageAtsScore",Math.round(avg),"totalApplications",apps.countByUserId(a.getName()));}
}
