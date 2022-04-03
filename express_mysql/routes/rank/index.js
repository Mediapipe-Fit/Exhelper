import express from 'express';
import * as user_controllers from '../controllers/user.js'
const router = express.Router();
router
.route("/")
.get(user_controllers.getRank);
module.exports = router;