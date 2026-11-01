package com.resumeanalyzer.controller;
import com.resumeanalyzer.entity.*;import com.resumeanalyzer.repository.*;import com.resumeanalyzer.service.AnalysisService;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api")
public class AnalysisController{
 private final AnalysisService service;private final ResumeAnalysisRepository analyses;private final JobDescriptionRepository jobs;
 public AnalysisController(AnalysisService s,ResumeAnalysisRepository a,JobDescriptionRepository j){service=s;analyses=a;jobs=j;}
 public record AnalyzeRequest(String jobDescriptionId){}
 @PostMapping("/analysis/{resumeId}") public ResumeAnalysis analyze(@PathVariable String resumeId,@RequestBody AnalyzeRequest req,Authentication auth){return service.analyze(auth.getName(),resumeId,req.jobDescriptionId());}
 @GetMapping("/analysis/{analysisId}") public ResumeAnalysis get(@PathVariable String analysisId,Authentication auth){return analyses.findByIdAndUserId(analysisId,auth.getName()).orElseThrow(()->new NoSuchElementException("Analysis not found"));}
 @GetMapping("/analysis/resume/{resumeId}") public List<ResumeAnalysis> byResume(@PathVariable String resumeId,Authentication auth){return analyses.findByUserIdOrderByCreatedAtDesc(auth.getName()).stream().filter(x->x.getResumeId().equals(resumeId)).toList();}
 @GetMapping("/dashboard/recent-analyses") public List<ResumeAnalysis> recent(Authentication a){return analyses.findByUserIdOrderByCreatedAtDesc(a.getName());}
}
