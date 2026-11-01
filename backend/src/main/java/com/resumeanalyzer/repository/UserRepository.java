package com.resumeanalyzer.repository;
import com.resumeanalyzer.entity.AppUser;import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<AppUser,String>{AppUser findByEmailIgnoreCase(String email);boolean existsByEmailIgnoreCase(String email);}
