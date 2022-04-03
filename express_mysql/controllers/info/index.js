import * as server_Error from '../errors/server_Error.js';
import * as utils from '../utils/common.js';
import * as infoRepository from '../data/info.js';

// INSERT
exports.createInfo = (req, res, next) => {
  if (!req.body) {
    return next(new server_Error("No form data found", 404));
  }
  let sql = "INSERT INTO info SET ?"
  database.get().query(sql,utils.Newinfo(req),function (err, data, fields) {
      if (err) return next(new server_Error(err, 500));
      res.status(201).json({
        status: "success",
        message: "Info created!",
      });
    }
  );
};

// SELECT
exports.getInfo_with_month = (req, res, next) => {
  if ((!req.params.email) | (req.query.month)) {
    return next(new server_Error("No model ID found", 404));
  }
  let sql = "SELECT * FROM info WHERE email=? and left(date,7) = ?"
  database.get().query(sql,[req.params.email,req.query.month], function (err, data, fields) {
    if(err) return next(new server_Error(err))
    res.status(200).json({
      status: "success",
      length: data.length,
      data: data,
    });
  });
};

exports.getInfo_Today = (req, res, next) => {
  if ((!req.params.email) | (req.query.date)){
    return next(new server_Error("No model ID found", 404));
  }
  let sql = "SELECT * FROM info WHERE email=? and date = ?"
  database.get().query(sql,[req.params.email,req.query.date], function (err, data, fields) {
    if(err) return next(new server_Error(err))
    res.status(200).json({
      status: "success",
      length: data.length,
      data: data,
    });
  });
};


//UPDATE

// 갯수 업데이트 
exports.updateInfo_cur = (req, res, next) => {
  if ((!req.params.email) | (!req.query.name) | (!req.query.cur)) {
    return next(new server_Error("No model ID found", 404));
  }
  var sql = "UPDATE info SET current=? WHERE email=? and exername=? and date=?"
  database.get().query(sql,[req.query.cur,req.params.email,req.query.name,utils.getToday()],function (err, data, fields) {
      if (err) return next(new server_Error(err, 500));
      res.status(201).json({
      status: "success",
      message: "count updated!",
      requst: req.query.name,
      });
    }
  );
};
// 운동 종류 업데이트
exports.updateInfo = (req, res, next) => {
  if (!req.body) {
    return next(new server_Error("No form data found", 404));
  }
  let sql = "CALL Update_info(?,?,?,?,?)"
  database.get().query(sql,utils.Updateinfo(req),function (err, data, fields) {
      if (err) return next(new server_Error(err, 500));
      res.status(201).json({
        status: "success",
        message: "Info created!",
      });
    }
  );
};


//DELETE
exports.deleteInfo = (req, res, next) => {
  if ((!req.params.email) | (!req.query.name) | (!req.query.date)){
    return next(new server_Error("No model ID found", 404));
  }
  var sql = "DELETE FROM info WHERE User_Email=? and exer_name=? and date=?"
  database.get().query(sql,[req.params.email,req.query.name,req.query.date],function (err, fields) {{
      if (err) return next(new server_Error(err, 500));
      res.status(201).json({
        status: "success",
        message: "Info deleted!",
      });
    };
  });
}
