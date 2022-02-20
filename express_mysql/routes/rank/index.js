const express = require("express");
const user_controllers = require("../../controllers/user");
const router = express.Router();
router
.route("/")
.get(user_controllers.getRank);
module.exports = router;