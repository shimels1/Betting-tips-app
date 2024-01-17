const express = require("express");
const db = require("../startup/mysqlconnection");
const route = express.Router();
route.use(express.json());

route.post("/signup/", async (req, res) => {
  try {
    const fname = req.body.fname;
    const lname = req.body.lname;
    const role = req.body.role;
    const email = req.body.email;
    var password = req.body.password;
    const isExisBefor = await db.execute("select * from user where email=? ", [
      email,
    ]);
    if (isExisBefor[0] != "")
      return res.send({ message: "email number is already exist" });

    const sqlStatment =
      "INSERT INTO `user`( `fname`, `lname`,`email`,`password`,`role`) values (?,?,?,?,?)";
    await db.execute(sqlStatment, [fname, lname, email, password, role]);
    return res.send({ message: "true" });
  } catch (err) {
    console.log(err);
    return res.send({ message: "false" });
  }
});

route.post("/login", async (req, res) => {
  var email = req.body.email + "";
  var password = req.body.password + "";
  const user = await db.execute(
    "SELECT * FROM user where email=? and password=?",
    [email, password]
  );
  if (user[0] != "") {
    let user2 = {
      fname: user[0][0].fname,
      lname: user[0][0].lname,
      email: user[0][0].email,
      role: user[0][0].role,
    };
    return res.send(user2);
  } else {
    return res.send(404, { error: `email or password is not correct` });
  }
});

route.get("/getuser/:email", async (req, res) => {
  if (!req.params.email) return res.send("email require.");
  const user = await db.execute("select * from user where email=? limit 1", [
    req.params.email,
  ]);
  if (user[0] === "")
    return res.status(200).send({ message: "there is no user yet" });
  return res.status(200).send(user[0][0]);
});

route.get("/getVersion", async (req, res) => {
  try {
    // const type = req.params.type;
    const item = await db.execute(" SELECT `version` FROM `version`");
    return res.status(200).send(item[0][0].version);
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

module.exports = route;
