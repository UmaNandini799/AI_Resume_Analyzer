package com.resumeanalyzer.controller;
import com.resumeanalyzer.repository.*;import org.springframework.http.*;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.nio.charset.StandardCharsets;import java.util.*;
@RestController @RequestMapping("/api/reports")
public class ReportController{
 private final ResumeAnalysisRepository analyses;private final ResumeRepository resumes;
 public ReportController(ResumeAnalysisRepository a,ResumeRepository r){analyses=a;resumes=r;}
 @GetMapping(value="/{id}/pdf",produces="text/plain") public ResponseEntity<byte[]> report(@PathVariable String id,Authentication auth){var a=analyses.findByIdAndUserId(id,auth.getName()).orElseThrow();String body="Resume Analysis Report\nATS score: "+a.getAtsScore()+"\nJob match: "+a.getMatchScore()+"\n\nRecommendations:\n"+a.getRecommendations();return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=resume-analysis-report.txt").contentType(MediaType.TEXT_PLAIN).body(body.getBytes(StandardCharsets.UTF_8));}
 @GetMapping("/{id}/optimized-resume") public ResponseEntity<byte[]> optimized(@PathVariable String id,Authentication auth){var a=analyses.findByIdAndUserId(id,auth.getName()).orElseThrow();var r=resumes.findByIdAndUserId(a.getResumeId(),auth.getName()).orElseThrow();return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=optimized-resume.txt").contentType(MediaType.TEXT_PLAIN).body(r.getExtractedText().getBytes(StandardCharsets.UTF_8));}
}
