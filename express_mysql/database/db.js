import mysql from 'mysql2';
// 쿼리 할때마다 연결하지 않고 서버 연결시 미리 풀을 생성해 둔다.
import { DBInfo } from "../config/config.js"

const pool = mysql.createPool({
    connectionLimit: 100,
    host: DBInfo.DB.HOST,
    user: DBinfo.DB.USER,
    password: DBinfo.DB.PASSWORD,
    port: DBinfo.DB.PORT,
    database: DBinfo.DB.DB
});
//db pool에 접근할 수 있도록 한다
export const DB = pool.promise();