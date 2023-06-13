const { db } = require("../config/db.config");
const { buildResp, cleanStr } = require("../utils/utils");

class ApplicationController {
  async getAll(req, res) {
    try {
      const applications = await db.query("SELECT * FROM job_applications;");
      res
        .status(200)
        .send(
          buildResp("Applications retrieved successfully", applications.rows)
        );
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
      const applications = await db.query(
        `SELECT * FROM job_applications LIMIT ${size} OFFSET ${offset};`
      );
      const msg =
        applications.rows.length === 0
          ? "No applications found"
          : "Applications retrieved successfully";
      res.status(200).send(buildResp(msg, applications.rows));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async getById(req, res, next) {
    const { application_id } = req.params;

    try {
      const application = await db.query(
        `SELECT * FROM job_applications WHERE app_id = ${application_id};`
      );
      const msg =
        application.rows.length === 0
          ? "Application not found"
          : "Application retrieved successfully";
      res.status(200).send(buildResp(msg, application.rows[0]));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async getByJobId(req, res, next) {
    const { job_id } = req.query;
    if (!job_id) {
      next();
      return;
    }

    try {
      const applications = await db.query(
        `SELECT * FROM job_applications WHERE job_id = ${job_id};`
      );
      const msg =
        applications.rows.length === 0
          ? "No applications found"
          : "Applications retrieved successfully";
      res
        .status(200)
        .send(
          buildResp(
            msg,
            applications.rows.length == 0 ? undefined : applications.rows
          )
        );
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async getByUserId(req, res, next) {
    const { user_id } = req.query;
    if (!user_id) {
      next();
      return;
    }
    try {
      const applications = await db.query(
        `SELECT * FROM job_applications WHERE user_id = ${user_id};`
      );
      const msg =
        applications.rows.length === 0
          ? "No applications found"
          : "Applications retrieved successfully";
      res
        .status(200)
        .send(
          buildResp(
            msg,
            applications.rows.length == 0 ? undefined : applications.rows
          )
        );
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async deleteById(req, res) {
    const { application_id } = req.params;
    try {
      const application = await db.query(
        `DELETE FROM job_applications WHERE app_id = ${application_id};`
      );
      res.status(200).send(buildResp("Application deleted successfully"));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }
}

module.exports = ApplicationController;