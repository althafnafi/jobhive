const { Router } = require("express");
const EmployerController = require("./employers.controller");
const { validateEmployer } = require("./employers.middleware");

const EmployersRouter = Router();
const Employers = new EmployerController();

// GET ROUTES
EmployersRouter.get("/", Employers.paginate, Employers.getAll);
EmployersRouter.get("/:employer_id", Employers.getById);

// POST ROUTES
EmployersRouter.post("/register", validateEmployer, Employers.register);
EmployersRouter.post("/login", Employers.login);

// DELETE ROUTES
EmployersRouter.delete("/:employer_id", Employers.deleteById);

module.exports = EmployersRouter;
