const express = require("express");
const db = require("../startup/mysqlconnection");
const route = express.Router();
// route.use(express.json());

route.get("/getVersion", async (req, res) => {
  try {
    // const type = req.params.type;
    const item = await db.execute(" SELECT `version` FROM `version`");
    return res.status(200).send({ version: item[0][0].version });
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

route.get("/getAdUnitId", async (req, res) => {
  try {
    // const type = req.params.type;
    const item = await db.execute(" SELECT `adUnitId` FROM `addon`");
    return res.status(200).send({ addUnitId: item[0][0].adUnitId });
  } catch (error) {
    return res.status(401).send({ message: error });
  }
});

module.exports = route;
