const { Router } = require("express");
const UserController = require("./users.controller");
const { validateUser } = require("./users.middleware");

const UsersRouter = Router();
const Users = new UserController();

// GET ROUTES
UsersRouter.get("/", Users.getAll);
UsersRouter.get("/:user_id", Users.getById);

// POST ROUTES
UsersRouter.post("/register", validateUser, Users.register);
UsersRouter.post("/login", Users.login);

// DELETE ROUTES
UsersRouter.delete("/:user_id", Users.deleteById);

module.exports = UsersRouter;
