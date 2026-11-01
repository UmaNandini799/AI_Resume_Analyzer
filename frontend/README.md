# AI Resume Analyzer & Optimizer — Frontend

Frontend built with HTML5, CSS3 and vanilla JavaScript. It is designed to connect to the Java Spring Boot REST API planned for the next phase.

## Run
1. Extract the ZIP.
2. Open the folder in VS Code.
3. Start a local static server from this folder, for example:
   - VS Code Live Server, or
   - `python -m http.server 5500`
4. Visit `http://localhost:5500`.

Do not open pages with `file://`; browser module/API requests work best over a local server.

## Backend connection
Edit `js/config.js` and set `API_BASE_URL` to your Spring Boot URL (default `http://localhost:8080/api`).
The frontend expects JWT authentication. It stores the access token in `sessionStorage` and sends it as `Authorization: Bearer <token>`.

## Included pages
Landing, login, registration, dashboard, resume upload, parsed preview, analysis, results, keyword analysis, AI suggestions, resume editor, comparison, resume management, application tracker, history, reports, profile and tips.

## Important
This is the frontend phase. API calls are wired to the documented endpoints, but the backend and Supabase database are not included in this ZIP yet. Until those phases are generated and configured, protected operations will show a clear connection/error message rather than pretending to save data.
