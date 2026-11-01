package com.resumeanalyzer.entity;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="job_descriptions")
public class JobDescription {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private String id;
 @Column(name="user_id",nullable=false) private String userId;
 @Column(name="job_title") private String jobTitle; @Column(name="company_name") private String companyName;
 @Column(columnDefinition="text") private String description; @Column(name="created_at") private Instant createdAt=Instant.now();
 public String getId(){return id;} public String getUserId(){return userId;} public void setUserId(String v){userId=v;}
 public String getJobTitle(){return jobTitle;} public void setJobTitle(String v){jobTitle=v;} public String getCompanyName(){return companyName;} public void setCompanyName(String v){companyName=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;} public Instant getCreatedAt(){return createdAt;}
}
