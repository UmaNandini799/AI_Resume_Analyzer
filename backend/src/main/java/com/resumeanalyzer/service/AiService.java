package com.resumeanalyzer.service;
import org.springframework.beans.factory.annotation.Value; import org.springframework.http.MediaType; import org.springframework.stereotype.Service; import org.springframework.web.client.RestClient; import java.util.*;
@Service public class AiService {
 private final String key,url,model; private final RestClient client=RestClient.create();
 public AiService(@Value("${ai.api-key}")String key,@Value("${ai.chat-completions-url}")String url,@Value("${ai.model}")String model){this.key=key;this.url=url;this.model=model;}
 public Map<String,Object> suggest(String task,String content,String jd){if(key.isBlank())return Map.of("suggestion","AI service is not configured. Set AI_API_KEY in the backend environment. Your original content has not been changed.","task",task);
  String prompt="You are a careful resume writing assistant. Do not invent employers, qualifications, metrics or achievements. If a metric is missing, suggest a placeholder. Task: "+task+"\nResume content:\n"+content+"\nTarget job description:\n"+jd+"\nReturn concise useful suggestions.";
  Map<?,?> response=client.post().uri(url).contentType(MediaType.APPLICATION_JSON).header("Authorization","Bearer "+key).body(Map.of("model",model,"messages",List.of(Map.of("role","system","content","Give truthful resume feedback. Never fabricate candidate facts."),Map.of("role","user","content",prompt)),"temperature",0.3)).retrieve().body(Map.class);
  Object choices=response==null?null:response.get("choices");if(choices instanceof List<?> l&&!l.isEmpty()&&l.get(0) instanceof Map<?,?> c&&c.get("message") instanceof Map<?,?> msg)return Map.of("suggestion",String.valueOf(msg.get("content")),"task",task);return Map.of("suggestion","AI provider returned no suggestion.","task",task);
 }
}
