import {FETCH} from "/js/common/util.js";
import {errorAlert} from "/js/common/alert.js";

const tag = '[auth]';

async function logout() {
    try {
        const response = await FETCH.post('/account/logout');
        if (location.pathname === '/')
            location.reload();
        else
            location.href = '/';
    } catch (e) {
        errorAlert("로그아웃에 실패하였습니다.");
    }
}

async function login(loginId, loginPw) {
    const response = await FETCH.post('/account/login', {loginId, loginPw});
    return response;
}


export {logout, login};
