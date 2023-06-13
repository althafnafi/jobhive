const app = require("./app");
const { SERVER_PORT } = require("./config/db.config");

app.listen(SERVER_PORT, () => {
  console.log(`Server is running on port ${SERVER_PORT}`);
});
