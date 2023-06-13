const { db } = require("../config/db.config");
const { buildResp } = require("../utils/utils");
const bcrypt = require("bcrypt");

class EmployerController {
  async getAll(req, res) {
    try {
      const employers = await db.query("SELECT * FROM employers;");
      res
        .status(200)
        .send(buildResp("Employers retrieved successfully", employers.rows));
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
      const employers = await db.query(
        `SELECT * FROM employers LIMIT ${size} OFFSET ${offset};`
      );
      const msg =
        employers.rows.length === 0
          ? "No employers found"
          : "Employers retrieved successfully";
      res.status(200).send(buildResp(msg, employers.rows));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }
  async getById(req, res) {
    const { employer_id } = req.params;
    try {
      const employer = await db.query(
        `SELECT * FROM employers WHERE employer_id = ${employer_id};`
      );
      const msg =
        employer.rows.length === 0
          ? "Employer not found"
          : "Employer retrieved successfully";
      res.status(200).send(buildResp(msg, employer.rows[0]));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async register(req, res) {
    const { full_name, company_name, email, password, address } = req.body;
    if (!full_name || !company_name || !email || !password || !address) {
      res.status(400).send(buildResp("Missing required fields"));
      return;
    }

    try {
      const hashedPassword = await bcrypt.hash(password, 10);
      const employer = await db.query(
        `INSERT INTO 
        employers (full_name, company_name, email, password, address) 
        VALUES ('${full_name}', '${company_name}', '${email}', '${hashedPassword}', '${address}') 
        RETURNING *;`
      );
      res
        .status(200)
        .send(buildResp("Employer created successfully", employer.rows[0]));
    } catch (err) {
      console.error(err.message);
      res
        .status(500)
        .send(buildResp("Server error, email might already exists"));
      return;
    }
  }

  async login(req, res) {
    const { email, password } = req.body;
    if (!email || !password) {
      res
        .status(400)
        .send(buildResp("Missing required fields", { login: false }, false));
      return;
    }

    try {
      const employer = await db.query(
        `SELECT * FROM employers WHERE email = '${email}';`
      );
      if (employer.rows.length === 0) {
        res
          .status(400)
          .send(buildResp("Employer not found", { login: false }, false));
        return;
      }
      const match = await bcrypt.compare(password, employer.rows[0].password);
      if (!match) {
        res
          .status(400)
          .send(buildResp("Invalid credentials", { login: false }, false));
        return;
      }
      res.status(200).send(
        buildResp("Login successful", {
          login: true,
          employer: employer.rows[0],
        })
      );
    } catch (err) {
      console.error(err.message);
      res.status(500).send(buildResp("Server error", { login: false }, false));
      return;
    }
  }

  async deleteById(req, res) {
    const { employer_id } = req.params;
    try {
      const employer = await db.query(
        `DELETE FROM employers WHERE employer_id = ${employer_id}
        RETURNING *;`
      );
      const msg =
        employer.rows.length === 0
          ? "Employer not found"
          : "Employer deleted successfully";
      res.status(200).send(buildResp(msg, employer.rows[0]));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }
}

module.exports = EmployerController;
