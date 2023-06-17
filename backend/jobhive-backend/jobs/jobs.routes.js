const { Router } = require("express");
const JobController = require("./jobs.controller");

const JobsRouter = Router();
const Jobs = new JobController();

// GET ROUTES
JobsRouter.get("/", Jobs.paginate, Jobs.getJobsByEmployerId, Jobs.getAll);
JobsRouter.get("/:job_id", Jobs.getById);

// POST ROUTES
JobsRouter.post("/", Jobs.create);
JobsRouter.post("/apply", Jobs.apply);

// PATCH ROUTES
JobsRouter.patch("/:job_id", Jobs.updateById);

// DELETE ROUTES
JobsRouter.delete("/:job_id", Jobs.deleteById);

module.exports = JobsRouter;
