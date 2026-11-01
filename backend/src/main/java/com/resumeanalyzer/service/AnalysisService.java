package com.resumeanalyzer.service;
import com.fasterxml.jackson.databind.ObjectMapper; import com.resumeanalyzer.entity.*; import com.resumeanalyzer.repository.*; import org.springframework.stereotype.Service; import java.util.*; import java.util.regex.*;
@Service public class AnalysisService {
 private final ResumeRepository resumes; private final JobDescriptionRepository jobs; private final ResumeAnalysisRepository analyses; private final ObjectMapper mapper=new ObjectMapper();
 public AnalysisService(ResumeRepository r,JobDescriptionRepository j,ResumeAnalysisRepository a){resumes=r;jobs=j;analyses=a;}
 public ResumeAnalysis analyze(String user,String resumeId,String jobId){Resume r=resumes.findByIdAndUserId(resumeId,user).orElseThrow(()->new NoSuchElementException("Resume not found"));JobDescription j=jobs.findByIdAndUserId(jobId,user).orElseThrow(()->new NoSuchElementException("Job description not found"));
  String resume=Optional.ofNullable(r.getExtractedText()).orElse("").toLowerCase(), jd=Optional.ofNullable(j.getDescription()).orElse("").toLowerCase();
  Set<String> stop=Set.of("the","and","for","with","you","your","are","our","will","from","that","this","have","has","job","role","work","years","year","ability","skills","experience","team","using","knowledge","strong","good","required","preferred","must");
  LinkedHashSet<String> words=new LinkedHashSet<>();Matcher m=Pattern.compile("[a-zA-Z][a-zA-Z0-9+#.]{1,}").matcher(jd);while(m.find()){String w=m.group().toLowerCase();if(!stop.contains(w))words.add(w);}
  List<String> matched=new ArrayList<>(),missing=new ArrayList<>();for(String w:words){if(resume.contains(w))matched.add(w);else missing.add(w);}
  int match=words.isEmpty()?0:(int)Math.round(100.0*matched.size()/words.size());
  int sections=(resume.contains("education")?1:0)+(resume.contains("experience")?1:0)+(resume.contains("skills")?1:0)+(resume.contains("project")?1:0)+(resume.contains("summary")?1:0);
  int structure=sections*20;int formatting=resume.length()>250?85:50;int content=resume.length()>500?80:55;
  int ats=(int)Math.round(structure*.20+match*.30+Math.min(100,sections*20)*.20+formatting*.15+content*.15);
  List<String> issues=new ArrayList<>();if(sections<5)issues.add("Use clear standard headings for Summary, Experience, Education, Skills and Projects.");if(resume.contains("|")||resume.contains("\t"))issues.add("Review table-like or tabular formatting; use a simple single-column layout.");if(resume.length()<500)issues.add("Add relevant detail to demonstrate skills, projects and impact.");
  List<String> rec=new ArrayList<>(issues);if(!missing.isEmpty())rec.add("Review missing job keywords and include only those that accurately describe your skills: "+String.join(", ",missing.subList(0,Math.min(12,missing.size()))));
  ResumeAnalysis a=new ResumeAnalysis();a.setUserId(user);a.setResumeId(resumeId);a.setJobDescriptionId(jobId);a.setAtsScore(Math.max(0,Math.min(100,ats)));a.setMatchScore(match);
  try{a.setMatchedKeywords(mapper.writeValueAsString(matched));a.setMissingKeywords(mapper.writeValueAsString(missing));a.setFormattingIssues(mapper.writeValueAsString(issues));a.setRecommendations(mapper.writeValueAsString(rec));a.setSectionScores(mapper.writeValueAsString(Map.of("structure",structure,"keywordRelevance",match,"formatting",formatting,"contentQuality",content)));}catch(Exception e){throw new IllegalStateException(e);}
  return analyses.save(a);
 }
}
