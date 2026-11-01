package com.resumeanalyzer.entity;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="resumes")
public class Resume {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private String id;
 @Column(name="user_id",nullable=false) private String userId;
 @Column(name="resume_name") private String resumeName;
 @Column(name="original_file_name",nullable=false) private String originalFileName;
 @Column(name="file_path") private String filePath;
 @Column(name="extracted_text",columnDefinition="text") private String extractedText;
 @Column(name="parsed_data",columnDefinition="jsonb") private String parsedData="{}";
 @Column(name="file_type") private String fileType;
 @Column(name="created_at") private Instant createdAt=Instant.now();
 public String getId(){return id;} public String getUserId(){return userId;} public void setUserId(String v){userId=v;}
 public String getResumeName(){return resumeName;} public void setResumeName(String v){resumeName=v;}
 public String getOriginalFileName(){return originalFileName;} public void setOriginalFileName(String v){originalFileName=v;}
 public String getFilePath(){return filePath;} public void setFilePath(String v){filePath=v;}
 public String getExtractedText(){return extractedText;} public void setExtractedText(String v){extractedText=v;}
 public String getParsedData(){return parsedData;} public void setParsedData(String v){parsedData=v;}
 public String getFileType(){return fileType;} public void setFileType(String v){fileType=v;}
 public Instant getCreatedAt(){return createdAt;}
}
