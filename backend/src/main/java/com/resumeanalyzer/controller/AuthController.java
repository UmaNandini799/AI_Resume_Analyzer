package com.resumeanalyzer.controller;
import com.resumeanalyzer.entity.AppUser; import com.resumeanalyzer.repository.UserRepository; import com.resumeanalyzer.security.JwtService; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users;private final PasswordEncoder encoder;private final JwtService jwt;
 public AuthController(UserRepository u,PasswordEncoder e,JwtService j){users=u;encoder=e;jwt=j;}
 public record RegisterRequest(@NotBlank String fullName,@Email @NotBlank String email,@Size(min=8) String password){}
 public record LoginRequest(@Email @NotBlank String email,@NotBlank String password){}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r){if(users.existsByEmailIgnoreCase(r.email()))return ResponseEntity.badRequest().body(Map.of("message","Email is already registered"));AppUser u=new AppUser();u.setFullName(r.fullName());u.setEmail(r.email().toLowerCase());u.setPasswordHash(encoder.encode(r.password()));users.save(u);return ResponseEntity.status(201).body(Map.of("token",jwt.generate(u.getId(),u.getEmail()),"userId",u.getId(),"fullName",u.getFullName()));}
 @PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody LoginRequest r){AppUser u=users.findByEmailIgnoreCase(r.email());if(u==null||!encoder.matches(r.password(),u.getPasswordHash()))return ResponseEntity.status(401).body(Map.of("message","Invalid email or password"));return ResponseEntity.ok(Map.of("token",jwt.generate(u.getId(),u.getEmail()),"userId",u.getId(),"fullName",u.getFullName()));}
}
