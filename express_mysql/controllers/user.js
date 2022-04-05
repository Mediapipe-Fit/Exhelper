import * as server_Error from '../errors/server_Error.js';
import * as utils from '../utils/common.js';
import * as infoRepository from '../data/info.js';

// INSERT
export async function createInfo(req, res, next) {
    if (!req.body) { // 추후에 express-validator로 변경 예정
        return next(new server_Error("No form data found", 404));
    }
    const info = await infoRepository.createInfo(utils.newinfo(req));
    res.status(201).json({
        status: "success",
        message: info,
    });
};
/*
// SELECT
export async function getInfo_with_month(req, res, next) {
    if ((!req.params.email) | (req.query.month)) {
        return next(new server_Error("No model ID found", 404));
    }
    let sql = "SELECT * FROM info WHERE email=? and left(date,7) = ?"
    DB.get().query(sql, [req.params.email, req.query.month], function(err, data, fields) {
        if (err) return next(new server_Error(err))
        res.status(200).json({
            status: "success",
            length: data.length,
            data: data,
        });
    });
};

export async function getInfo_Today(req, res, next) {
    if ((!req.params.email) | (req.query.date)) {
        return next(new server_Error("No model ID found", 404));
    }
    let sql = "SELECT * FROM info WHERE email=? and date = ?"
    DB.get().query(sql, [req.params.email, req.query.date], function(err, data, fields) {
        if (err) return next(new server_Error(err))
        res.status(200).json({
            status: "success",
            length: data.length,
            data: data,
        });
    });
};*/

export async function getInfoWeek(req, res, next) {
    if ((!req.params.email) | (req.query.date)) {
        return next(new server_Error("No model ID found", 404));
    }
    const weekInfo = await infoRepository.getInfoWeek(req.params.email, req.query.date);
    res.status(200).json({
        data: weekInfo,
    });
};


//UPDATE

// 갯수 업데이트 
export async function updateInfoCount(req, res, next) {
    if ((!req.params.email) | (!req.query.name) | (!req.query.cur)) {
        return next(new server_Error("No model ID found", 404));
    }
    const info = await infoRepository.updateInfoCount(utils.significantInfo(req), req.query.completeSetNum, req.query.current)
    res.status(201).json({
        status: "success",
        message: info,
    });
};

// 운동 종류 업데이트
export async function updateInfo(req, res, next) {
    if (!req.body) {
        return next(new server_Error("No form data found", 404));
    }
    const info = await infoRepository.updateInfo(utils.updateInfo(req))
    res.status(201).json({
        status: "success",
        message: info,
    });
};


//DELETE
export async function deleteInfo(req, res, next) {
    if ((!req.params.email) | (!req.query.exername) | (!req.query.date) | (!req.query.sequence)) {
        return next(new server_Error("email : {0}, date : {1}, exername : {2}, sequence : {3} ".format(req.params.email, req.query.date, req.query.exername, req.query.sequence), 404));
    }
    const info = await infoRepository.deleteInfo(utils.significantInfo(req));
    res.status(201).json({
        status: "success",
        message: info,
    });
}