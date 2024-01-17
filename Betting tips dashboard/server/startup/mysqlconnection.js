const sql = require("mysql2");
const con = sql
  .createPool({
    host: "localhost",
    user: "root",
    database: "betting",
    password: "",
    port: "3306",
  })
  .promise();
module.exports = con;
