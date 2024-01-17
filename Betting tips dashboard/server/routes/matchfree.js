const express = require("express");
const db = require("../startup/mysqlconnection");
const route = express.Router();

// get all items
route.get("/getAll", async (req, res) => {
  try {
    const item = await db.execute(" SELECT * FROM `match` ORDER BY DATE DESC");
    return res.status(200).send(item[0]);
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

// get date lists
route.get("/getDateLists", async (req, res) => {
  try {
    // const type = req.params.type;
    const item = await db.execute(
      " SELECT date as `date` FROM `match` where `type`=?   GROUP BY DATE ORDER BY DATE DESC; ",
      ["free"]
    );
    return res.status(200).send(item[0]);
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

// get date lists
route.get("/getDateLists/:catagory", async (req, res) => {
  try {
    const type = "free";
    const catagory = req.params.catagory;

    //console.log(catagory);
    if (!type) {
      console.log("type undifined");
      return res.send({ error: "type id is invalid." });
    }
    if (!catagory) {
      console.log("catagory undifined");

      return res.send({ error: "catagory id is invalid." });
    }
    const item = await db.execute(
      " SELECT date as `date` FROM `match` where `type`=? and `catagory`=?   GROUP BY DATE ORDER BY DATE DESC; ",
      [type, catagory]
    );
    return res.status(200).send(item[0]);
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

// get leags lists
route.get("/getLeagLists/", async (req, res) => {
  try {
    const item = await db.execute(
      " SELECT league as `league` FROM `match`  GROUP BY league; "
    );
    return res.status(200).send(item[0]);
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});
// get club lists
route.get("/getPredictionLists/", async (req, res) => {
  try {
    const item = await db.execute(
      " SELECT prediction as `prediction` FROM `match`  GROUP BY prediction; "
    );
    return res.status(200).send(item[0]);
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});
// get club lists
route.get("/getClubLists/", async (req, res) => {
  try {
    const item = await db.execute(
      " SELECT club1 as `club1` FROM `match`  GROUP BY club1; "
    );
    return res.status(200).send(item[0]);
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

// get one item
route.get("/getOne/:idbetting", async (req, res) => {
  try {
    const id = req.params.idbetting;
    console.log;
    if (!id) return res.send({ error: "betting id is invalid." });
    const match = await db.execute(
      " SELECT * FROM `match` where idbetting=? ",
      [id]
    );
    if (!match[0][0])
      return res.send({ error: "match is not found in the given id" });
    return res.status(200).send(match[0][0]);
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});
// get one item by date
route.post("/getByDate/", async (req, res) => {
  try {
    const date = req.body.date;
    if (!date) return res.send({ error: "date id is invalid." });
    const match = await db.execute(
      " SELECT * FROM `match` where `date`=? and `type`=?",
      [date, "free"]
    );
    if (!match[0])
      return res.send({ error: "match is not found in the given date" });
    return res.status(200).send(match[0]);
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

// get one item by date and category
route.post("/getByDateAndCat/", async (req, res) => {
  try {
    const date = req.body.date;
    const catagory = req.body.catagory;
    if (!date) {
      console.log("date undifined");
      return res.send({ error: "date id is invalid." });
    }
    if (!catagory) {
      console.log("catagory undifined");

      return res.send({ error: "catagory id is invalid." });
    }
    const match = await db.execute(
      " SELECT * FROM `match` where `date`=? and `type`=? and `catagory`=? ",
      [date, "free", catagory]
    );
    if (!match[0])
      return res.send({ error: "match is not found in the given date" });
    return res.status(200).send(match[0]);
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

// add item
// add item
route.post("/post/", async (req, res) => {
  try {
    const date = req.body.date;
    const time = req.body.time;
    const catagory = req.body.catagory;
    const league = req.body.league;
    const club1 = req.body.club1;
    const club2 = req.body.club2;
    const club1_score = req.body.club1_score;
    const club2_score = req.body.club2_score;
    const prediction = req.body.prediction;
    const result = req.body.result;
    const type = req.body.type;
    const odd = req.body.odd;
    const sqlStatment =
      "INSERT INTO `match` (`date`,`time`,`catagory`,`league`,`club1`, `club2`,`club1_score`, `club2_score`,`prediction`,`result`,`type`,`odd`) VALUES " +
      `(?,?,?,?,?,?,?,?,?,?,?,?)`;
    await db.execute(sqlStatment, [
      date,
      time,
      catagory,
      league,
      club1,
      club2,
      club1_score,
      club2_score,
      prediction,
      result,
      type,
      odd,
    ]);
    return res.status(200).send({ message: "true" });
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

// update item
route.put("/put/", async (req, res) => {
  try {
    const date1 = req.body.date;
    const time = req.body.time;
    const catagory = req.body.catagory;
    const league = req.body.league;
    const club1 = req.body.club1;
    const club2 = req.body.club2;
    const club1_score = req.body.club1_score;
    const club2_score = req.body.club2_score;
    const prediction = req.body.prediction;
    const result = req.body.result;
    const type = req.body.type;
    const idbetting = req.body.idbetting;
    const odd = req.body.odd;

    console.log("error");
    const sqlStatment =
      "UPDATE `match` SET `date`  =?,`time`  =?,`catagory`  =?,`league`  =?,`club1`  =?,`club2`  =?,`club1_score`  =?,`club2_score` =?,`prediction`=?,`result`=? ,`type`=? ,`odd`=? WHERE `idbetting` =" +
      idbetting +
      " LIMIT 1 ";
    await db.execute(sqlStatment, [
      date1,
      time,
      catagory,
      league,
      club1,
      club2,
      club1_score,
      club2_score,
      prediction,
      result,
      type,
      odd,
    ]);
    return res.status(200).send({ message: "true" });
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

//////////// delete order
route.delete("/delete/:idbetting", async (req, res) => {
  try {
    await db.execute("delete from `match` where `idbetting`=? LIMIT 1", [
      req.params.idbetting,
    ]);
    return res.send({ message: "match delete success" });
  } catch (error) {
    console.log(error);
    return res.status(401).send({ message: error });
  }
});

module.exports = route;
