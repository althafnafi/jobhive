const express = require("express");
const bodyParser = require("body-parser");
const { Client } = require("pg");
const dotenv = require("dotenv");

// Express
const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
// Dotenv
dotenv.config();
// PostgreSQL
const { PGHOST, PGDATABASE, PGUSER, PGPASSWORD, PGPORT, SERVER_PORT } =
  process.env;
const db = new Client({
  user: PGUSER,
  host: PGHOST,
  database: PGDATABASE,
  port: Number(PGPORT),
  password: PGPASSWORD,
  sslmode: "require",
  ssl: true,
});

// Connect to dataase
db.connect((err) => {
  if (err) {
    console.error(err);
    return;
  }
  console.log("Connected to database");
});

/* API Endpoints */
// "/jobs" -> get all available jobs
app.get("/jobs", (req, res) => {
  db.query("SELECT * FROM job_listings;", (err, result) => {
    if (err) {
      console.error(err);
      return;
    }
    res.status(200).send(result.rows);
  });
});

// "/jobs/:job_id"-> get job by id
app.get("/jobs/:job_id", (req, res) => {
  const { job_id } = req.params;
  db.query(
    `SELECT * FROM job_listings WHERE job_id = ${job_id};`,
    (err, result) => {
      if (err) {
        console.error(err);
        return;
      }
      res.status(200).send(result.rows);
    }
  );
});

// "/jobs/:job_id"-> get job by id
app.get("/users", (req, res) => {
  db.query(`SELECT * FROM users;`, (err, result) => {
    if (err) {
      console.error(err);
      return;
    }
    res.status(200).send(result.rows);
  });
});

// "/jobs/:job_id"-> get job by id
app.get("/users/:user_id", (req, res) => {
  const { user_id } = req.params;
  db.query(
    `SELECT * FROM job_listings WHERE user_id = ${user_id};`,
    (err, result) => {
      if (err) {
        console.error(err);
        return;
      }
      res.status(200).send(result.rows);
    }
  );
});

app.listen(SERVER_PORT, () => {
  console.log(`Server is running on port ${SERVER_PORT}`);
});
