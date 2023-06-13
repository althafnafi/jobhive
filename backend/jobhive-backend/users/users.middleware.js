const { buildResp } = require("../utils/utils");

const validateUser = (req, res, next) => {
  const passRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[A-Za-z\d]{6,25}$/;
  const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$/;

  const { username, email, password } = req.body;
  if (!passRegex.exec(password) || !emailRegex.exec(email)) {
    return res.status(400).json(buildResp("Invalid email or password"));
  }
  next();
};

module.exports = { validateUser };
