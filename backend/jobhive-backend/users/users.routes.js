const { Router } = require("express");
const UserController = require("./users.controller");

const UsersRouter = Router();
const Users = new UserController();

UsersRouter.get("/", Users.getUsers);
UsersRouter.get("/:user_id", Users.getUserById);

module.exports = UsersRouter;
