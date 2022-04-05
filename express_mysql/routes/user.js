import express from 'express';
import * as user_controllers from '../controllers/user.js'
const router = express.Router();
router
    .route('/')
    .post(user_controllers.createUpdateUser); // 함수만 연결하는 것 

router
    .route('/:email')
    //.get(user_controllers.getUser_with_email)
    //.put(user_controllers.updateScore)
    .delete(user_controllers.deleteUser);

export default router;