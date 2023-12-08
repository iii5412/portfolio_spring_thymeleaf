import {FETCH} from "/js/common/util.js";
import {errorAlert} from "/js/common/alert.js";

const tag = '[auth]';

function logout() {
    console.log(tag, 'logout');
    FETCH.post('/account/logout')
        .then(response => {
            if (location.pathname === '/')
                location.reload();
            else
                location.href = '/';
        })
        .catch(e => {
            errorAlert("로그아웃에 실패하였습니다.");
        });
}

function login(loginId, loginPw) {
    return FETCH.post('/account/login', {loginId, loginPw});
}


export {logout, login};
