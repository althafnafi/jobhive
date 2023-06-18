[create table]

CREATE TYPE app_status AS ENUM('pending', 'interview', 'waiting', 'accepted');
CREATE TABLE Users (
  user_id serial PRIMARY KEY,
  full_name varchar(255),
  email varchar(255) UNIQUE NOT NULL,
  password varchar(255) NOT NULL,
  address text
);

CREATE TABLE Employers (
  employer_id serial PRIMARY KEY,
  full_name varchar(255) NOT NULL,
  company_name varchar(255) NOT NULL,
  email varchar(255) UNIQUE NOT NULL,
  password varchar(255) NOT NULL,
  address text NOT NULL
);

CREATE TABLE Job_Listings (
  job_id serial PRIMARY KEY,
  job_title varchar(255) NOT NULL,
  job_cat varchar(255) NOT NULL,
  job_desc text NOT NULL,
  salary_avg bigint NOT NULL,
  job_req text NOT NULL,
  employer_id bigint,
  city varchar(255) NOT NULL,
  created_at timestamp DEFAULT (now())
);

CREATE TABLE Job_Tags (
  tag_id serial PRIMARY KEY,
  job_id bigint,
  tag varchar(255) NOT NULL
);

CREATE TABLE User_Prefs (
	pref_id serial,
	user_id bigint,
	pref_cat varchar(255)
);

CREATE TABLE Job_Applications (
  app_id serial PRIMARY KEY,
  job_id bigint,
  user_id bigint,
  created_at timestamp DEFAULT (now()),
  message text,
  status app_status NOT NULL DEFAULT ('pending')
);

ALTER TABLE Job_Listings ADD FOREIGN KEY (employer_id) REFERENCES Employers (employer_id) ON DELETE CASCADE;
ALTER TABLE Job_Tags ADD FOREIGN KEY (job_id) REFERENCES Job_Listings (job_id) ON DELETE CASCADE;
ALTER TABLE Job_Applications ADD FOREIGN KEY (job_id) REFERENCES Job_Listings (job_id) ON DELETE SET NULL;
ALTER TABLE Job_Applications ADD FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE;
ALTER TABLE User_Prefs ADD FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE;