const { db } = require("../config/db.config");
const { buildResp, cleanStr } = require("../utils/utils");

class JobController {
  async getAll(req, res) {
    try {
      const jobs = await db.query(
        `SELECT 
              J.job_id,
              J.job_title,
              J.job_cat,
              J.job_desc,
              J.salary_avg,
              J.job_req,
              J.employer_id,
              J.city,
              J.created_at,
              E.company_name
              FROM job_listings AS J 
              INNER JOIN
              employers AS E
              ON J.employer_id = E.employer_id;`
      );
      console.log(jobs.rows);
      res.status(200).send(buildResp("Jobs retrieved successfully", jobs.rows));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async paginate(req, res, next) {
    if (!req.query.page || !req.query.size) {
      next();
      return;
    }

    const { page, size } = req.query;
    const offset = (page - 1) * size;
    try {
      const jobs = await db.query(
        `SELECT * FROM job_listings LIMIT ${size} OFFSET ${offset};`
      );
      const msg =
        jobs.rows.length === 0
          ? "No jobs found"
          : "Jobs retrieved successfully";
      res.status(200).send(buildResp(msg, jobs.rows));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async getById(req, res) {
    const { job_id } = req.params;
    try {
      const job = await db.query(
        `SELECT 
        J.job_id,
        J.job_title,
        J.job_cat,
        J.job_desc,
        J.salary_avg,
        J.job_req,
        J.employer_id,
        J.city,
        J.created_at,
        E.company_name
        FROM job_listings AS J 
        INNER JOIN
        employers AS E
        ON J.employer_id = E.employer_id
        WHERE job_id = ${job_id};`
      );
      const msg =
        job.rows.length === 0 ? "Job not found" : "Job retrieved successfully";
      res.status(200).send(buildResp(msg, job.rows[0]));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async updateById(req, res) {
    const { job_id } = req.params;
    const { job_title, job_cat, job_desc, salary_avg, job_req } = req.body;

    const values = [];
    let updateFieldStr = "";
    let updateFieldLen = 0;
    const updateFieldArr = [];

    if (job_title !== undefined) {
      updateFieldArr.push(`job_title = $${++updateFieldLen}`);
      values.push(cleanStr(job_title));
    }

    if (job_cat !== undefined) {
      updateFieldArr.push(`job_cat = $${++updateFieldLen}`);
      values.push(cleanStr(job_cat));
    }

    if (job_desc !== undefined) {
      updateFieldArr.push(`job_desc = $${++updateFieldLen}`);
      values.push(cleanStr(job_desc));
    }

    if (salary_avg !== undefined) {
      updateFieldArr.push(`salary_avg = $${++updateFieldLen}`);
      values.push(Number(cleanStr(salary_avg)));
    }

    if (job_req !== undefined) {
      updateFieldArr.push(`job_req = $${++updateFieldLen}`);
      values.push(cleanStr(job_req));
    }

    if (updateFieldArr.length === 0) {
      res.status(400).send(buildResp("No fields to update"));
      return;
    }

    const updateFields = updateFieldArr.join(", ");

    try {
      const job = await db.query(
        `UPDATE job_listings 
        SET ${updateFields} 
        WHERE job_id = ${job_id} 
        RETURNING *;`,
        values
      );
      res.status(200).send(buildResp("Job updated successfully", job.rows[0]));
    } catch (err) {
      res.status(400).send(buildResp("Job update failed"));
      console.error(err.message);
      return;
    }
  }

  async create(req, res) {
    let {
      job_title,
      job_cat,
      job_desc,
      salary_avg,
      job_req,
      employer_id,
      city,
    } = req.body;
    if (
      !job_title ||
      !job_cat ||
      !job_desc ||
      !salary_avg ||
      !job_req ||
      !employer_id ||
      !city
    ) {
      res.status(400).send(buildResp("Missing required fields"));
      return;
    }
    job_desc = cleanStr(job_desc);
    job_req = cleanStr(job_req);
    job_cat = cleanStr(job_cat);
    job_title = cleanStr(job_title);

    try {
      const query = `INSERT INTO job_listings 
        (job_title, job_cat, job_desc, salary_avg, job_req, employer_id, city) 
        VALUES 
        ('${job_title}', '${job_cat}', '${job_desc}', 
        ${Number(salary_avg)}, '${job_req}', ${employer_id}, '${city}') 
        RETURNING *;`;
      // console.log(query);
      const job = await db.query(query);
      res.status(201).send(buildResp("Job created successfully", job.rows[0]));
    } catch (err) {
      res.status(400).send(buildResp("Job creation failed"));
      console.error(err.message);
      return;
    }
  }

  async deleteById(req, res) {
    const { job_id } = req.params;
    try {
      const job = await db.query(
        `DELETE FROM job_listings WHERE job_id = ${job_id}
        RETURNING *;`
      );
      const msg =
        job.rows.length === 0 ? "Job not found" : "Job deleted successfully";
      res.status(200).send(buildResp(msg, job.rows[0]));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async apply(req, res) {
    const { job_id, user_id, message } = req.body;

    try {
      // Check if the job listing exists
      const jobQuery = `SELECT * FROM job_listings WHERE job_id = ${job_id}`;
      const jobRes = await db.query(jobQuery);
      const jobListing = jobRes.rows[0];
      if (!jobListing) {
        res.status(404).json(buildResp("Job listing not found"));
        return;
      }

      // Check if the user exists
      const userQuery = `SELECT * FROM users WHERE user_id = ${user_id}`;
      const userRes = await db.query(userQuery);
      const user = userRes.rows[0];
      if (!user) {
        res.status(404).json(buildResp("User not found"));
        return;
      }

      // try {
      //   // Check if the user has already applied for this job
      //   const applicationQuery = `SELECT * FROM job_applications WHERE job_id = ${job_id} AND user_id = ${user_id}`;
      //   const applicationRes = await db.query(applicationQuery);
      //   if (applicationRes.rows.length > 0) {
      //     res
      //       .status(400)
      //       .json(buildResp("User has already applied for this job"));
      //     return;
      //   }
      // } catch (err) {
      //   console.error(err.message);
      //   res.status(400).json(buildResp("Job application failed"));
      //   return;
      // }

      // Insert the job application
      const insertQuery = `INSERT INTO job_applications 
                            (job_id, user_id, message) 
                            VALUES 
                            (${job_id}, ${user_id}, '${message}')
                            RETURNING *;`;
      const application = await db.query(insertQuery);
      res
        .status(201)
        .json(
          buildResp("Job application created successfully", application.rows[0])
        );
    } catch (error) {
      console.error(error.message);
      res.status(400).json(buildResp("Job application failed"));
    }
  }

  async getJobsByEmployerId(req, res, next) {
    const { employer_id } = req.query;

    if (!employer_id) {
      next();
      return;
    }
    console.log(employer_id);
    try {
      const jobs = await db.query(
        `SELECT * FROM job_listings WHERE employer_id = ${employer_id};`
      );
      res.status(200).json(buildResp("Jobs retrieved successfully", jobs.rows));
    } catch (err) {
      console.error(err.message);
    }
  }
}

module.exports = JobController;
