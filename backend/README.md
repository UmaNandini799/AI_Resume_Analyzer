# Spring Boot Backend

## Requirements
- Java 25+
- Maven 3.9+
- Supabase project with PostgreSQL and a private Storage bucket named `resumes` (or configure `SUPABASE_BUCKET`).

## Configure
1. Copy `.env.example` to `.env` and fill in the Supabase database connection details, JWT secret and optional AI API key.
2. Export those variables in your terminal/IDE run configuration. Spring Boot does not automatically load `.env` files.
3. Run the database script in `../database/schema.sql` in Supabase SQL Editor before starting the backend.
4. Start with `mvn spring-boot:run`.

The backend uses `spring.jpa.hibernate.ddl-auto=validate`; it does not create/modify the database schema.

## Implemented endpoints
Auth register/login; resume upload/list/get/rename/delete; job description create/list/get/delete; ATS analysis and history; AI summary/bullet/skills/action verbs/recommendations; dashboard; applications; profile; report downloads.

## Integration notes
- Frontend expects `POST /api/auth/login` and `/register` to return `{ "token": "..." }`.
- Resume upload accepts multipart field `file`.
- Analysis expects `{ "jobDescriptionId": "..." }`.
- The basic report endpoint currently returns a text report, not a PDF. PDF/DOCX export, email delivery, Google OAuth, public share links, benchmark dataset and tips feed are extension work and are not falsely represented as completed.
- Supabase Storage upload is enabled when `SUPABASE_URL` and `SUPABASE_SERVICE_ROLE_KEY` are configured. Keep the service-role key only on the server.
- The ATS score is a transparent starter heuristic, not a real ATS prediction. Validate and tune it before real-world use.
