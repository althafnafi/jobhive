const { Router } = require("express");
const JobController = require("./jobs.controller");

const JobsRouter = Router();
const Jobs = new JobController();

// GET ROUTES
JobsRouter.get("/", Jobs.paginate, Jobs.getAll);
JobsRouter.get("/:job_id", Jobs.getById);

// POST ROUTES
JobsRouter.post("/", Jobs.create);

// PATCH ROUTES
JobsRouter.patch("/:job_id", Jobs.updateById);

// DELETE ROUTES
JobsRouter.delete("/:job_id", Jobs.deleteById);

module.exports = JobsRouter;
