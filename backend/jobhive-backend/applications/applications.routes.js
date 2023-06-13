const { Router } = require("express");
const ApplicationController = require("./applications.controller");

const ApplicationsRouter = Router();
const Applications = new ApplicationController();

// GET ROUTES
ApplicationsRouter.get(
  "/",
  Applications.getByJobId,
  Applications.getByUserId,
  Applications.getAll
);
ApplicationsRouter.get("/:application_id", Applications.getById);

// POST ROUTES
// ApplicationsRouter.post("/", Applications.apply);

// PATCH ROUTES

// DELETE ROUTES
ApplicationsRouter.delete("/:application_id", Applications.deleteById);

module.exports = ApplicationsRouter;
