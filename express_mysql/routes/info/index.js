const express = require("express");
const info_controllers = require("../../controllers/info");
const router = express.Router();

router
 .route("/")
 .post(info_controllers.createInfo);

router // 오늘 운동 정보
 .route("/:email")
 .get(info_controllers.getInfo_Today) // ip 주소/info/email
 .put(info_controllers.updateInfo_cur) // ip 주소/info/email?name=TEST1&cur=10
 .delete(info_controllers.deleteInfo); // ip 주소/info/email?name=TEST1

router // 이번 달 운동 정보 
 .route("/month/:email")
 .get(info_controllers.getInfo_with_month); // ip 주소/info/month/email

module.exports = router;
