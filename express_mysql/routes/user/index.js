const express = require("express");
const user_controllers = require("../../controllers/user");
const router = express.Router();

router
 .route("/")
 .get(user_controllers.getAllUser)
 .post(user_controllers.createUser);
router
 .route("/:email")
 .get(user_controllers.getUser_with_email)
 .put(user_controllers.updateScore)
 .delete(user_controllers.deleteUser);
module.exports = router;
