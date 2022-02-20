#!/usr/bin/node

const express = require('express');
const cors = require('cors');
const httpconfig = require('./config/https.config.json')
const router_user = require('./routes/user');
const router_info = require('./routes/info');
const router_model = require('./routes/csv_model');
const errorHandler = require('./errors/errorHandler');
const Database = require('./services/db');
const fs = require('fs');

const app = express();
const PORT = httpconfig.secure.port;
const https = require('https'); // https 보안 설정 
const options = {
  ca: fs.readFileSync(httpconfig.secure.ca),
  key: fs.readFileSync(httpconfig.secure.key),
  cert: fs.readFileSync(httpconfig.secure.cert),
};
https.createServer(options, app).listen(PORT, () => {
  console.log('---번 포트에서 대기중입니다.');
});

app.use(cors()); // cors 이슈 해결 
app.use(express.json()) // json 형식으로 데이터 받기
app.use(errorHandler);
app.use('/user',router_user);
app.use('/info',router_info);
app.use('/model',router_model);

//app.listen(PORT, () => {
//    console.log(`server running on port ${PORT}`);/
//});

Database.connect(function(err){
    if(err){
      console.log('데이터베이스에 접속할 수 없습니다');
      process.exit(1);
    }
});

app.get('', (req, res)=>{
    res.status(200).json({
        status: "success",
        message: "서버가 정상적으로 실행중입니다.",
    });
})

module.exports = app



