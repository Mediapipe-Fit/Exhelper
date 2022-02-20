const express = require("express");
const model_controllers = require("../../controllers/csv_model");
const router = express.Router();

router
 .route("/")
 .get(model_controllers.getAllModel); 

router
 .route("/:id")
 .put(model_controllers.updateFname) // ip 주소/model/id?file_name=deadlift
 .get(model_controllers.getModel_with_id) // ip 주소/model/id
 .delete(model_controllers.deleteModel); // ip 주소/model/id


module.exports = router;
