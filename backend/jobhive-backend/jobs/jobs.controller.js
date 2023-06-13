const { db } = require("../config/db.config");

class JobController {
  async getJobs(req, res) {
    try {
      const jobs = await db.query("SELECT * FROM job_listings;");
      res.status(200).send(jobs.rows);
    } catch (err) {
      console.error(err);
      return;
    }
  }

  async getJobById(req, res) {
    const { job_id } = req.params;
    try {
      const job = await db.query(
        `SELECT * FROM job_listings WHERE job_id = ${job_id};`
      );
      res.status(200).send(job.rows);
    } catch (err) {
      console.error(err);
      return;
    }
  }

  async createJobs(req, res) {
    const { job_title, job_desc, job_, employer_idd } = req.body;
  }
}

module.exports = JobController;
