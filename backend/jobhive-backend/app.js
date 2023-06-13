const express = require("express");
const bodyParser = require("body-parser");
const { Client } = require("pg");
const dotenv = require("dotenv");
const { db } = require("./config/db.config");

// Express
const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
// Dotenv
dotenv.config();

// Connect to dataase
db.connect((err) => {
  if (err) {
    console.error(err.message);
    return;
  }
  console.log("Connected to database");
});

/* API Endpoints */
const UsersRouter = require("./users/users.routes");
const JobsRouter = require("./jobs/jobs.routes");
const EmployersRouter = require("./employers/employers.routes");

app.use("/api/jobs", JobsRouter);
app.use("/api/users", UsersRouter);
app.use("/api/employers", EmployersRouter);

module.exports = app;
