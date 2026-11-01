package com.resumeanalyzer.controller;
import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler{
 @ExceptionHandler(NoSuchElementException.class) public ResponseEntity<?> notFound(Exception e){return ResponseEntity.status(404).body(Map.of("message",e.getMessage()));}
 @ExceptionHandler(IllegalArgumentException.class) public ResponseEntity<?> bad(Exception e){return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));}
 @ExceptionHandler(Exception.class) public ResponseEntity<?> other(Exception e){return ResponseEntity.status(500).body(Map.of("message","An unexpected server error occurred")); }
}
