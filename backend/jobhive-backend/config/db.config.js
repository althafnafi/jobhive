const { Pool } = require("pg");
const dotenv = require("dotenv");

dotenv.config();

// PostgreSQL
const { PGHOST, PGDATABASE, PGUSER, PGPASSWORD, PGPORT, SERVER_PORT } =
  process.env;

const db = new Pool({
  user: PGUSER,
  host: PGHOST,
  database: PGDATABASE,
  port: Number(PGPORT),
  password: PGPASSWORD,
  ssl: true,
});

module.exports = { db, SERVER_PORT };
