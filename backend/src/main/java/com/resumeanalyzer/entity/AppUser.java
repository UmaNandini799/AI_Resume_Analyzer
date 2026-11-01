package com.resumeanalyzer.entity;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="users")
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private String id;
 @Column(name="full_name",nullable=false) private String fullName;
 @Column(nullable=false,unique=true) private String email;
 @Column(name="password_hash",nullable=false) private String passwordHash;
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
 public String getId(){return id;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public Instant getCreatedAt(){return createdAt;}
}
