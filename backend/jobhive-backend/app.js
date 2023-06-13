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
    console.error(err);
    return;
  }
  console.log("Connected to database");
});

/* API Endpoints */
const UsersRouter = require("./users/users.routes");
const JobsRouter = require("./jobs/jobs.routes");

app.use("/api/jobs", JobsRouter);
app.use("/api/users", UsersRouter);

module.exports = app;
