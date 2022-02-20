const mysql = require('mysql');
const dbconfig = require('../config/db.config.json')
var pool;
// 쿼리 할때마다 연결하지 않고 서버 연결시 미리 풀을 생성해 둔다.
exports.connect = function(done) {
    pool = mysql.createPool({
        connectionLimit: 100,
        host: dbconfig.mysql.HOST,
        user: dbconfig.mysql.USER, 
        password: dbconfig.mysql.PASSWORD, 
        port:dbconfig.mysql.PORT, 
        database:dbconfig.mysql.DB
    });
}
// get() 함수를 통해 db pool에 접근할 수 있도록 한다
exports.get = function() {
  return pool;
}
