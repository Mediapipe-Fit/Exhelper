import { DB } from '../database/db.js';

export async function createUpdateUser(user) {
    return DB
        .execute("Call CU_user(?,?,?,?,?)", [user])
        .then((result) => result[0][0].result);
}

export async function getAllUser() {
    return DB
        .execute("SELECT * FROM user")
        .then((result) => result);
}
export async function deleteUser(email) {

    return DB
        .execute("DELETE FROM user WHERE email=?", [email])
        .then((result) => result);
}

export async function getRank(email) {
    return DB
        .execute("Call Get_ranking(?)")
        .then((result) => result[0])
}