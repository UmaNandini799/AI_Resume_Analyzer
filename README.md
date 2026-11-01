# AI Resume Analyzer & Optimizer

This combined package contains:
- `frontend/`: HTML, CSS and vanilla JavaScript UI
- `backend/`: Java 25 + Spring Boot REST API
- `database/`: Supabase PostgreSQL starter schema

## Setup order
1. Create a Supabase project and run `database/schema.sql` in SQL Editor.
2. Create a private Supabase Storage bucket named `resumes`.
3. Configure environment variables using `backend/.env.example`.
4. Run backend: `cd backend` then `mvn spring-boot:run`.
5. Run frontend using VS Code Live Server or `python -m http.server 5500` from `frontend/`.
6. Set `frontend/js/config.js` API URL if needed.

This is an integrated starter implementation of the core workflow. See `backend/README.md` for implemented endpoints and limitations. Do not use production data until you have reviewed security, tested database mappings and added production-grade export/OAuth/email/share features.
