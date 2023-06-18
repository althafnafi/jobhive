# JobHive - A job search app

The Job Search App is a web application designed to help users search for job opportunities, create job listings, and manage the job application process. It allows two types of users: regular users and employers. Regular users can search for jobs, apply for positions, a while employers can create job listings, and find suitable candidates.

## API Documentation
https://documenter.getpostman.com/view/22652839/2s93sdaCoM

## Table Structure

The app utilizes a PostgreSQL database with the following tables:

### Users

The `Users` table stores information about regular users.

| Column     | Data Type         | Description                         |
|------------|------------------|-------------------------------------|
| user_id    | serial           | Primary key                         |
| full_name  | varchar(255)     | Full name of the user               |
| email      | varchar(255)     | Unique email address of the user    |
| password   | varchar(255)     | User's password                     |
| address    | text             | User's address                      |

### Employers

The `Employers` table stores information about employers.

| Column        | Data Type         | Description                              |
|---------------|------------------|------------------------------------------|
| employer_id   | serial           | Primary key                              |
| full_name     | varchar(255)     | Full name of the employer                 |
| company_name  | varchar(255)     | Name of the employer's company            |
| email         | varchar(255)     | Unique email address of the employer      |
| password      | varchar(255)     | Employer's password                      |
| address       | text             | Employer's address                       |

### Job_Listings

The `Job_Listings` table contains details about job listings.

| Column        | Data Type         | Description                              |
|---------------|------------------|------------------------------------------|
| job_id        | serial           | Primary key                              |
| job_title     | varchar(255)     | Title of the job listing                  |
| job_cat       | varchar(255)     | Category of the job                       |
| job_desc      | text             | Description of the job                    |
| salary_avg    | bigint           | Average salary for the job                |
| job_req       | text             | Job requirements and qualifications       |
| employer_id   | bigint           | Foreign key referencing Employers table   |
| city          | varchar(255)     | City where the job is located             |
| created_at    | timestamp        | Timestamp of job listing creation         |

### Job_Tags

The `Job_Tags` table stores tags associated with each job listing.

| Column    | Data Type    | Description                                 |
|-----------|--------------|---------------------------------------------|
| tag_id    | serial       | Primary key                                 |
| job_id    | bigint       | Foreign key referencing Job_Listings table   |
| tag       | varchar(255) | Tag associated with the job listing          |

### User_Prefs

The `User_Prefs` table contains user preferences for job categories.

| Column    | Data Type    | Description                               |
|-----------|--------------|-------------------------------------------|
| pref_id   | serial       | Primary key                               |
| user_id   | bigint       | Foreign key referencing Users table        |
| pref_cat  | varchar(255) | Preferred job category for the user        |

### Job_Applications

The `Job_Applications` table stores information about job applications.


| Column     | Data Type         | Description                               |
|------------|------------------|-------------------------------------------|
| app_id     | serial           | Primary key                               |
| job_id     | bigint           | Foreign key referencing Job_Listings table |
| user_id    | bigint           | Foreign key referencing Users table        |
| created_at | timestamp        | Timestamp of application submission        |
| message    | text             | Additional message from the applicant      |
| status     | app_status       | Status of the job application              |
