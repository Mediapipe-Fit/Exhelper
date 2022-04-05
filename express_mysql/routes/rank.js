import express from 'express';
import * as user_controllers from '../controllers/user.js'
const router = express.Router();
router
    .route('/:email')
    .get(user_controllers.getRank);

export default router;