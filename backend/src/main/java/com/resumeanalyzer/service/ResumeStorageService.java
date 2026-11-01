package com.resumeanalyzer.service;
import org.springframework.beans.factory.annotation.Value; import org.springframework.http.*; import org.springframework.stereotype.Service; import org.springframework.web.client.RestClient; import java.net.URLEncoder; import java.nio.charset.StandardCharsets;
@Service public class ResumeStorageService {
 private final String url,key,bucket; private final RestClient client=RestClient.create();
 public ResumeStorageService(@Value("${supabase.url}") String url,@Value("${supabase.service-role-key}") String key,@Value("${supabase.bucket}") String bucket){this.url=url;this.key=key;this.bucket=bucket;}
 public String upload(String path,byte[] bytes,String contentType){if(url.isBlank()||key.isBlank())return "local-not-configured/"+path;String encoded=java.util.Arrays.stream(path.split("/")).map(x->URLEncoder.encode(x,StandardCharsets.UTF_8)).collect(java.util.stream.Collectors.joining("/"));client.put().uri(url+"/storage/v1/object/"+bucket+"/"+encoded).header("Authorization","Bearer "+key).header("apikey",key).contentType(MediaType.parseMediaType(contentType)).body(bytes).retrieve().toBodilessEntity();return path;}
}
