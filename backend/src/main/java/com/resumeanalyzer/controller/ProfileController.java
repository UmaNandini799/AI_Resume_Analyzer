package com.resumeanalyzer.controller;
import com.resumeanalyzer.repository.UserRepository;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/profile")
public class ProfileController{
 private final UserRepository users;public ProfileController(UserRepository r){users=r;}
 @GetMapping public Map<String,Object> get(Authentication a){var u=users.findById(a.getName()).orElseThrow();return Map.of("id",u.getId(),"fullName",u.getFullName(),"email",u.getEmail());}
 @PutMapping public Map<String,Object> update(@RequestBody Map<String,String> b,Authentication a){var u=users.findById(a.getName()).orElseThrow();if(b.containsKey("fullName"))u.setFullName(b.get("fullName"));users.save(u);return Map.of("id",u.getId(),"fullName",u.getFullName(),"email",u.getEmail());}
}
