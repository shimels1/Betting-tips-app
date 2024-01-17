const matchfree = require("../routes/matchfree");
const matchvip = require("../routes/matchvip");
const user = require("../routes/user");
const others = require("../routes/others");
var bodyParser = require("body-parser");
const express = require("express");
var bodyParser = require("body-parser");

var cors = require("cors");

module.exports = function (app) {
  // app.use(cors());
  app.use(bodyParser.json());
  app.use(express.urlencoded({ extended: true }));
  app.use(express.static("public"));

  app.use("/api/free", matchfree);
  app.use("/api/vip", matchvip);
  app.use("/api/user", user);
  app.use("/api/others", others);
};
