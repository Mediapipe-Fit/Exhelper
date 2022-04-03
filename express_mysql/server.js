#!/usr/bin/node

import express from 'express';
import cors from 'cors';
import fs from 'fs';
import https from 'https';
import router_rank from './routes/rank.js';
import router_user from './routes/user.js';
import router_info from './routes/info.js';
import router_model from './routes/model.js';
import { secure } from './config/security.js';
import errorHandler from './errors/errorHandler.js';
import { DB as Database } from './database/db.js';

const app = express();


const options = {
    ca: fs.readFileSync(secure.ca),
    key: fs.readFileSync(secure.key),
    cert: fs.readFileSync(secure.cert),
};

https.createServer(options, app).listen(secure.port, () => {
    console.log('{0}번 포트에서 대기중입니다.'.format(secure.port));
});

app.use(cors()); // cors 이슈 해결 
app.use(express.json()) // json 형식으로 데이터 받기
app.use(errorHandler);
app.use('/rank', router_rank);
app.use('/user', router_user);
app.use('/info', router_info);
//app.use('/model', router_model); 현재 사용 안함 
/*
app.listen(secure.Info.port, () => {
    console.log(`server running on port ${secure.Info.port}`);
});*/
Database.connect(function(err) {
    if (err) {
        console.log('데이터베이스에 접속할 수 없습니다');
        process.exit(1);
    }
});

app.use('', (req, res) => {
    res.status(401).send("Not Found!!");
})

export default app;