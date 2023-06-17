const { db } = require("../config/db.config");
const { buildResp } = require("../utils/utils");
const bcrypt = require("bcrypt");

class UserController {
  async getAll(req, res) {
    try {
      const users = await db.query("SELECT * FROM users;");
      res
        .status(200)
        .send(buildResp("Users retrieved successfully", users.rows));
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
      const users = await db.query(
        `SELECT * FROM users LIMIT ${size} OFFSET ${offset};`
      );
      const msg =
        users.rows.length === 0
          ? "No users found"
          : "Users retrieved successfully";
      res.status(200).send(buildResp(msg, users.rows));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async getById(req, res) {
    const { user_id } = req.params;
    try {
      const user = await db.query(
        `SELECT * FROM users WHERE user_id = ${user_id};`
      );
      const msg =
        user.rows.length === 0
          ? "User not found"
          : "User retrieved successfully";
      res.status(200).send(buildResp(msg, user.rows[0]));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async register(req, res) {
    const { full_name, email, password } = req.body;
    if (!full_name || !email || !password) {
      res.status(400).send(buildResp("Missing required fields"));
      return;
    }

    try {
      // Hash password
      const saltRounds = 10;
      const hashedPassword = await bcrypt.hash(password, saltRounds);

      const user = await db.query(
        `INSERT INTO users (full_name, email, password) 
        VALUES ('${full_name}', '${email}', '${hashedPassword}') 
        RETURNING *;`
      );
      res
        .status(200)
        .send(buildResp("User created successfully", user.rows[0]));
    } catch (err) {
      console.error(err.message);
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
      const user = await db.query(
        `SELECT * FROM users WHERE email = '${email}';`
      );
      if (user.rows.length === 0) {
        res
          .status(400)
          .send(buildResp("User not found", { login: false }, false));
        return;
      }

      const match = await bcrypt.compare(password, user.rows[0].password);
      if (!match) {
        res
          .status(400)
          .send(buildResp("Incorrect password", { login: false }, false));
        return;
      }

      res.status(200).send(buildResp("Login successful", user.rows[0]));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }

  async deleteById(req, res) {
    const { user_id } = req.params;
    try {
      const user = await db.query(
        `DELETE FROM users WHERE user_id = ${user_id} RETURNING *;`
      );
      const msg =
        user.rows.length === 0 ? "User not found" : "User deleted successfully";
      res.status(200).send(buildResp(msg, user.rows[0]));
    } catch (err) {
      console.error(err.message);
      return;
    }
  }
}

module.exports = UserController;
