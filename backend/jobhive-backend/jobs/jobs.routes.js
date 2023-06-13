const { Router } = require("express");
const JobController = require("./jobs.controller");

const JobsRouter = Router();
const Jobs = new JobController();

JobsRouter.get("/", Jobs.getJobs);
JobsRouter.get("/:job_id", Jobs.getJobById);

module.exports = JobsRouter;
