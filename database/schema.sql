-- Run in Supabase SQL Editor before starting Spring Boot.
create extension if not exists pgcrypto;
create table if not exists users (
 id text primary key default gen_random_uuid()::text, full_name text not null,
 email text not null unique, password_hash text not null, created_at timestamptz not null default now()
);
create table if not exists resumes (
 id text primary key default gen_random_uuid()::text, user_id text not null references users(id) on delete cascade,
 resume_name text, original_file_name text not null, file_path text, extracted_text text, parsed_data jsonb default '{}'::jsonb,
 file_type text, created_at timestamptz default now(), updated_at timestamptz default now()
);
create table if not exists job_descriptions (
 id text primary key default gen_random_uuid()::text, user_id text not null references users(id) on delete cascade,
 job_title text, company_name text, description text, created_at timestamptz default now()
);
create table if not exists resume_analyses (
 id text primary key default gen_random_uuid()::text, user_id text not null references users(id) on delete cascade,
 resume_id text not null references resumes(id) on delete cascade,
 job_description_id text not null references job_descriptions(id) on delete cascade,
 ats_score integer, match_score integer, section_scores jsonb default '{}'::jsonb,
 missing_keywords jsonb default '[]'::jsonb, matched_keywords jsonb default '[]'::jsonb,
 formatting_issues jsonb default '[]'::jsonb, recommendations jsonb default '[]'::jsonb, created_at timestamptz default now()
);
create table if not exists job_applications (
 id text primary key default gen_random_uuid()::text, user_id text not null references users(id) on delete cascade,
 resume_id text references resumes(id) on delete set null, job_title text, company_name text,
 application_date date, application_status text default 'Applied', notes text, created_at timestamptz default now()
);
create index if not exists idx_resumes_user on resumes(user_id);
create index if not exists idx_jobs_user on job_descriptions(user_id);
create index if not exists idx_analyses_user on resume_analyses(user_id,created_at desc);
create index if not exists idx_applications_user on job_applications(user_id,created_at desc);
-- This starter uses Spring Boot + server-side service-role storage access. Do not expose the service role key.
-- Enable RLS if you later access tables directly from Supabase client; keep Spring server access separately secured.
