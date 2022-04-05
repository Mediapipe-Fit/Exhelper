import express from 'express';
import * as info_controllers from '../controllers/info.js';
const router = express.Router();

router
    .route('/')
    .post(info_controllers.createInfo);
info_controllers.

router //운동 정보
    .route('/:email')
    .get(info_controllers.getInfoWeek) // ip 주소/info/email
    .put(info_controllers.updateInfo) // ip 주소/info/email?name=TEST1&cur=10
    .delete(info_controllers.deleteInfo); // ip 주소/info/email?name=TEST1
router
    .route('/count/:email')
    .put(info_controllers.updateInfoCount);
/*
router // 이번 달 운동 정보 
    .route('/month/:email')
    .get(info_controllers.getInfo_with_month); // ip 주소/info/month/email

*/
export default router;