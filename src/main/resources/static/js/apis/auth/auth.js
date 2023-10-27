import {FETCH} from "/js/util.js";

function logout() {
    FETCH.post('/account/logout')
        .then(response => {
            if (response.ok)
                location.href = "/";
        })
        .catch(e => {
            throw "로그아웃에 실패하였습니다.";
        });
}

function login(username, password) {
    if (username && password) {
        return FETCH.post('/account/login', {username, password});
    }
}

function goLoginPage() {
    location.href = '/loginPage';
}

function goSignupPage() {
    location.href = '/signup';
}

function goMainPage() {
    location.href = '/';
}


export {logout, login, goLoginPage, goSignupPage, goMainPage};
