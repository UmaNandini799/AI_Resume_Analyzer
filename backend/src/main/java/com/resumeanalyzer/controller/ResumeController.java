package com.resumeanalyzer.controller;
import com.fasterxml.jackson.databind.ObjectMapper; import com.resumeanalyzer.entity.Resume; import com.resumeanalyzer.repository.ResumeRepository; import com.resumeanalyzer.service.*; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import org.springframework.web.multipart.MultipartFile; import java.util.*;
@RestController @RequestMapping("/api/resumes")
public class ResumeController {
 private final ResumeRepository repo;private final ResumeTextExtractor extractor;private final ResumeStorageService storage;private final ObjectMapper mapper=new ObjectMapper();
 public ResumeController(ResumeRepository r,ResumeTextExtractor e,ResumeStorageService s){repo=r;extractor=e;storage=s;}
 @PostMapping(value="/upload",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,Authentication auth)throws Exception{
  if(file.isEmpty())return ResponseEntity.badRequest().body(Map.of("message","Select a file"));if(file.getSize()>10*1024*1024)return ResponseEntity.badRequest().body(Map.of("message","Maximum file size is 10 MB"));
  String name=Objects.requireNonNullElse(file.getOriginalFilename(),"resume").replaceAll("[^a-zA-Z0-9._-]","_");String ext=name.substring(name.lastIndexOf('.')+1).toLowerCase();if(!Set.of("pdf","docx","txt").contains(ext))return ResponseEntity.badRequest().body(Map.of("message","Only PDF, DOCX and TXT are supported"));
  String text=extractor.extract(file);Resume r=new Resume();r.setUserId(auth.getName());r.setResumeName(name);r.setOriginalFileName(name);r.setFileType(ext.toUpperCase());r.setExtractedText(text);r.setParsedData(mapper.writeValueAsString(Map.of("contactInfo",extractEmail(text),"summary",section(text,"summary"),"experience",section(text,"experience"),"education",section(text,"education"),"skills",section(text,"skills"),"certifications",section(text,"certifications"),"projects",section(text,"projects"))));
  r.setFilePath(storage.upload(auth.getName()+"/"+UUID.randomUUID()+"-"+name,file.getBytes(),Objects.requireNonNullElse(file.getContentType(),"application/octet-stream")));return ResponseEntity.status(201).body(repo.save(r));
 }
 private String extractEmail(String t){var m=java.util.regex.Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}").matcher(t);return m.find()?m.group():"";}
 private String section(String t,String h){String[] lines=t.split("\\R");boolean on=false;StringBuilder b=new StringBuilder();for(String line:lines){String l=line.trim().toLowerCase();if(l.matches(".*\\b"+h+"\\b.*")){on=true;continue;}if(on&&l.matches("^(education|experience|skills|summary|projects|certifications|work experience|technical skills)$"))break;if(on)b.append(line).append("\\n");}return b.toString().trim();}
 @GetMapping public List<Resume> all(Authentication a){return repo.findByUserIdOrderByCreatedAtDesc(a.getName());}
 @GetMapping("/{id}") public Resume one(@PathVariable String id,Authentication a){return repo.findByIdAndUserId(id,a.getName()).orElseThrow(()->new NoSuchElementException("Resume not found"));}
 @PutMapping("/{id}") public Resume rename(@PathVariable String id,@RequestBody Map<String,String> body,Authentication a){Resume r=one(id,a);r.setResumeName(body.getOrDefault("resumeName",r.getResumeName()));return repo.save(r);}
 @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable String id,Authentication a){repo.delete(one(id,a));return ResponseEntity.noContent().build();}
}
