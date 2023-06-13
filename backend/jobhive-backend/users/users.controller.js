const { db } = require("../config/db.config");

class UserController {
  async getUsers(req, res) {
    try {
      const users = await db.query("SELECT * FROM users;");
      res.status(200).send(users.rows);
    } catch (err) {
      console.error(err);
      return;
    }
  }

  async getUserById(req, res) {
    const { user_id } = req.params;
    try {
      const users = await db.query(
        `SELECT * FROM users WHERE user_id = ${user_id};`
      );
      res.status(200).send(users.rows);
    } catch (err) {
      console.error(err);
      return;
    }
  }
}

module.exports = UserController;
