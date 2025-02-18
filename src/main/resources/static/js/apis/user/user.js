import UserResponse from "/js/apis/response/user/UserResponse.js";
import {FETCH} from "/js/common/util.js";

const tag = `[api/user]`;
const requestMapping = `/user`;

async function fetchAllUserInfo() {
    const {data} = await FETCH.get(`${requestMapping}/`);
    return data.map(userData => new UserResponse(userData));
}


export {fetchAllUserInfo}
