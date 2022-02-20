const server_Error = require('../../errors/server_Error');
const database = require('../../services/db');
const utils = require('../../utils/common');

// INSERT
exports.createUser = (req, res, next) => {
  if (!req.body) {
    return next(new server_Error("No form data found", 404));
  }
  let sql = "INSERT INTO user SET ?"
  database.get().query(sql,utils.NewUser(req),function (err, data, fields) {
      if (err) return next(new server_Error(err, 500));
      res.status(201).json({
        status: "success",
        message: "User created!",
      });
    }
  );
};


// SELECT
exports.getAllUser = (req, res, next) => {
  let sql = "SELECT * FROM user"
  database.get().query(sql, function (err, data, fields) {
    if(err) return next(new server_Error(err))
    res.status(200).json({
      data: data,
    });
  });
};


exports.getUser_with_email = (req, res, next) => {
  if (!req.params.email) {
    return next(new server_Error("No User e-mail found", 404));
  }
  let sql = "SELECT * FROM user WHERE email=?"
  database.get().query(sql,[req.params.email], function (err, data, fields) {
    if(err) return next(new server_Error(err))
    res.status(200).json({
      data: data,
    });
  });
};


//Update
exports.updateScore = (req, res, next) => {
  if ((!req.params.email) | (!req.query.score)) {
    return next(new server_Error("No User e-mail found", 404));
  }
  var sql = "UPDATE user SET score=? WHERE email=?"
  database.get().query(sql,[req.query.score,req.params.email],function (err, data, fields) {
      if (err) return next(new server_Error(err, 500));
      res.status(201).json({
      status: "success",
      message: "Score updated!",
      });
    }
  );
};

//DELETE
exports.deleteUser = (req, res, next) => {
  if (!req.params.email) {
    return next(new server_Error("No User e-mail found", 404));
  }
  var sql = "DELETE FROM user WHERE email=?"
  database.get().query(sql,[req.params.email],function (err, fields) {{
      if (err) return next(new server_Error(err, 500));
      res.status(201).json({
        status: "success",
        message: "User deleted!",
      });
    };
  });
}
