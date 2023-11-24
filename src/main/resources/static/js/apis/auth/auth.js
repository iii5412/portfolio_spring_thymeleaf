import {FETCH} from "/js/common/util.js";

const tag = '[auth]';

function logout() {
    console.log(tag, 'logout');
    FETCH.post('/account/logout')
        .then(response => {
            if (response.ok)
                if(location.pathname === '/')
                    location.reload();
                else
                    location.href = '/';
        })
        .catch(e => {
            throw "로그아웃에 실패하였습니다.";
        });
}

function login(loginId, loginPw) {
    console.log(tag, 'login');
    if (loginId && loginPw) {
        return FETCH.post('/account/login', {loginId, loginPw});
    }
}


export {logout, login};
