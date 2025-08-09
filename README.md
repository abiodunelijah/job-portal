# Job Portal - Spring Boot Application

A comprehensive job portal built with Spring Boot, featuring user authentication, job posting, job searching, and application management.

## 🎯 Core Features

- **User Registration & Authentication** (Job Seekers & Employers)
- **Job Posting** (for Employers)
- **Job Browsing & Search** (for Job Seekers)
- **Job Application System**
- **Role-based Authorization** (JOB_SEEKER, EMPLOYER, ADMIN)

## 🚀 Quick Start

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Security**: Spring Security + JWT
- **Database**: MySQL
- **Build Tool**: Maven
- **Java Version**: 21


# 🔌 Key API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### Jobs (Public)
- `GET /api/jobs/public/search` - Search jobs
- `GET /api/jobs/public/{jobId}` - Get job details
- `GET /api/jobs/public/locations` - Get available locations

### Jobs (Employer Only)
- `POST /api/jobs/employer` - Create job
- `PUT /api/jobs/employer/{jobId}` - Update job
- `DELETE /api/jobs/employer/{jobId}` - Delete job

### Applications
- `POST /api/applications/apply` - Apply for job
- `GET /api/applications/my-applications` - Get my applications
- `GET /api/applications/job/{jobId}` - Get applications for job
- `PUT /api/applications/{applicationId}/status` - Update application status

## 🔐 Security

Include JWT token in Authorization header for protected endpoints:
```http
Authorization: Bearer {jwt_token}
```


## 🚀 Other Features

For production deployment:
1. Configure email notifications
2. Add file upload for resumes
3. Implement advanced search filters
4. Add admin dashboard 
