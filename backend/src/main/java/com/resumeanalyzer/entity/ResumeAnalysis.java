package com.resumeanalyzer.entity;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="resume_analyses")
public class ResumeAnalysis {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private String id;
 @Column(name="user_id",nullable=false) private String userId; @Column(name="resume_id",nullable=false) private String resumeId;
 @Column(name="job_description_id",nullable=false) private String jobDescriptionId;
 @Column(name="ats_score") private Integer atsScore; @Column(name="match_score") private Integer matchScore;
 @Column(name="section_scores",columnDefinition="jsonb") private String sectionScores="{}";
 @Column(name="missing_keywords",columnDefinition="jsonb") private String missingKeywords="[]";
 @Column(name="matched_keywords",columnDefinition="jsonb") private String matchedKeywords="[]";
 @Column(name="formatting_issues",columnDefinition="jsonb") private String formattingIssues="[]";
 @Column(name="recommendations",columnDefinition="jsonb") private String recommendations="[]";
 @Column(name="created_at") private Instant createdAt=Instant.now();
 public String getId(){return id;} public String getUserId(){return userId;} public void setUserId(String v){userId=v;}
 public String getResumeId(){return resumeId;} public void setResumeId(String v){resumeId=v;} public String getJobDescriptionId(){return jobDescriptionId;} public void setJobDescriptionId(String v){jobDescriptionId=v;}
 public Integer getAtsScore(){return atsScore;} public void setAtsScore(Integer v){atsScore=v;} public Integer getMatchScore(){return matchScore;} public void setMatchScore(Integer v){matchScore=v;}
 public String getSectionScores(){return sectionScores;} public void setSectionScores(String v){sectionScores=v;} public String getMissingKeywords(){return missingKeywords;} public void setMissingKeywords(String v){missingKeywords=v;}
 public String getMatchedKeywords(){return matchedKeywords;} public void setMatchedKeywords(String v){matchedKeywords=v;} public String getFormattingIssues(){return formattingIssues;} public void setFormattingIssues(String v){formattingIssues=v;}
 public String getRecommendations(){return recommendations;} public void setRecommendations(String v){recommendations=v;} public Instant getCreatedAt(){return createdAt;}
}
