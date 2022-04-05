import { DB } from '../database/db.js';

export async function createInfo(Info) {
    return DB
        .execute("INSERT INTO info SET ?", [Info])
        .then((result) => result);
}

export async function getInfoWeek(email, date) {
    return DB
        .execute("Call Get_weekInfo(?,?)", [email, date])
        .then((result) => result[0]);
}
export async function updateInfoCount(significantInfo, completeSetNum, current) {
    const array = [];
    return DB
        .execute("Call UpdateInfoCount(?,?,?,?,?,?)", array.concat(significantInfo, completeSetNum, current))
        .then()
}
export async function updateInfo(Info) {
    return DB
        .execute("CALL Update_info(?,?,?,?,?,?,?,?,?)", [Info])
        .then((result) => result);
}

export async function deleteInfo(significantInfo) {
    return DB
        .execute("DELETE FROM info WHERE date=? and email=? and exername=? and sequence = ?", [significantInfo])
        .then((result) => result);
}