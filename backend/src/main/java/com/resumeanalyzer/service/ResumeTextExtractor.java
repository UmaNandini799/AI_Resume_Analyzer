package com.resumeanalyzer.service;
import org.apache.pdfbox.Loader; import org.apache.pdfbox.text.PDFTextStripper; import org.apache.poi.xwpf.usermodel.XWPFDocument; import org.springframework.stereotype.Service; import org.springframework.web.multipart.MultipartFile; import java.io.*;
@Service public class ResumeTextExtractor {
 public String extract(MultipartFile f)throws IOException{String n=f.getOriginalFilename()==null?"":f.getOriginalFilename().toLowerCase();if(n.endsWith(".txt"))return new String(f.getBytes(),java.nio.charset.StandardCharsets.UTF_8);if(n.endsWith(".pdf")){try(var doc=Loader.loadPDF(f.getBytes())){return new PDFTextStripper().getText(doc);}}if(n.endsWith(".docx")){try(var doc=new XWPFDocument(f.getInputStream())){StringBuilder b=new StringBuilder();doc.getParagraphs().forEach(p->b.append(p.getText()).append("\n"));return b.toString();}}throw new IllegalArgumentException("Only PDF, DOCX and TXT are supported");}
}
