const server_Error = require('../../errors/server_Error');
const database = require('../../services/db');
const utils = require('../../utils/common');

// INSERT
exports.createModel = (req, res, next) => {
    if (!req.body) {
      return next(new server_Error("No form data found", 404));
    }
    let sql = "INSERT INTO csv_model SET ?"
    database.get().query(sql,utils.NewModel(req),function (err, data, fields) {
        if (err) return next(new server_Error(err, 500));
        res.status(201).json({
          status: "success",
          message: "Model created!",
        });
      }
    );
  };

// SELECT
exports.getAllModel = (req, res, next) => {
  let sql = "SELECT * FROM csv_model"
  database.get().query(sql, function (err, data, fields) {
    if(err) return next(new server_Error(err))
    res.status(200).json({
      status: "success",
      length: data.length,
      data: data,
    });
  });
};


exports.getModel_with_id = (req, res, next) => {
  if (!req.params.id) {
    return next(new server_Error("No model ID found", 404));
  }
  let sql = "SELECT * FROM csv_model WHERE id=?"
  database.get().query(sql,[req.params.email], function (err, data, fields) {
    if(err) return next(new server_Error(err))
    res.status(200).json({
      status: "success",
      length: data.length,
      data: data,
    });
  });
};

//UPDATE
exports.updateFname = (req, res, next) => {
  if (!req.params.id) {
    return next(new server_Error("No model ID found", 404));
  }
  var sql = "UPDATE csv_model SET file_name=? WHERE id=?"
  database.get().query(sql,[req.params.file_name,req.params.id],function (err, data, fields) {
      if (err) return next(new server_Error(err, 500));
      res.status(201).json({
      status: "success",
      message: "Model updated!",
      });
    }
  );
};

//DELETE
exports.deleteModel = (req, res, next) => {
  if (!req.params.id) {
    return next(new server_Error("No model ID found", 404));
  }
  var sql = "DELETE FROM csv_model WHERE id=?"
  database.get().query(sql,req.params.id,function (err, fields) {{
      if (err) return next(new server_Error(err, 500));
      res.status(201).json({
        status: "success",
        message: "Model deleted!",
      });
    };
  });
}
