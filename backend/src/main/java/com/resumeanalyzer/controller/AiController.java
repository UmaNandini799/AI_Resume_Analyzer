package com.resumeanalyzer.controller;
import com.resumeanalyzer.service.AiService;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/ai")
public class AiController{
 private final AiService ai;public AiController(AiService ai){this.ai=ai;}
 public record SuggestRequest(String content,String jobDescription){}
 @PostMapping("/summary") public Map<String,Object> summary(@RequestBody SuggestRequest r,Authentication a){return ai.suggest("Write a professional summary",r.content(),r.jobDescription());}
 @PostMapping("/rewrite-bullet") public Map<String,Object> bullet(@RequestBody SuggestRequest r,Authentication a){return ai.suggest("Rewrite this resume bullet using truthful STAR-style phrasing",r.content(),r.jobDescription());}
 @PostMapping("/skills") public Map<String,Object> skills(@RequestBody SuggestRequest r,Authentication a){return ai.suggest("Suggest relevant skills. Separate confirmed skills from skills to learn",r.content(),r.jobDescription());}
 @PostMapping("/action-verbs") public Map<String,Object> verbs(@RequestBody SuggestRequest r,Authentication a){return ai.suggest("Suggest stronger action verbs for these bullets",r.content(),r.jobDescription());}
 @PostMapping("/recommendations") public Map<String,Object> rec(@RequestBody SuggestRequest r,Authentication a){return ai.suggest("Give resume improvement recommendations",r.content(),r.jobDescription());}
}
