package com.resumeanalyzer.entity;
import jakarta.persistence.*; import java.time.Instant; import java.time.LocalDate;
@Entity @Table(name="job_applications")
public class JobApplication {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private String id;
 @Column(name="user_id",nullable=false) private String userId; @Column(name="resume_id") private String resumeId;
 @Column(name="job_title") private String jobTitle; @Column(name="company_name") private String companyName;
 @Column(name="application_date") private LocalDate applicationDate; @Column(name="application_status") private String applicationStatus="Applied";
 @Column(columnDefinition="text") private String notes; @Column(name="created_at") private Instant createdAt=Instant.now();
 public String getId(){return id;} public String getUserId(){return userId;} public void setUserId(String v){userId=v;} public String getResumeId(){return resumeId;} public void setResumeId(String v){resumeId=v;}
 public String getJobTitle(){return jobTitle;} public void setJobTitle(String v){jobTitle=v;} public String getCompanyName(){return companyName;} public void setCompanyName(String v){companyName=v;}
 public LocalDate getApplicationDate(){return applicationDate;} public void setApplicationDate(LocalDate v){applicationDate=v;} public String getApplicationStatus(){return applicationStatus;} public void setApplicationStatus(String v){applicationStatus=v;}
 public String getNotes(){return notes;} public void setNotes(String v){notes=v;} public Instant getCreatedAt(){return createdAt;}
}
