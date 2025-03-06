import { errorAlert } from '/js/common/alert.js';
import { FETCH } from '/js/common/util.js';

const tag = '[auth]';

/**
 * 로그아웃
 * @returns {Promise<void>}
 */
async function logout() {
    try {
        await FETCH.post('/account/logout');
        if (location.pathname === '/')
            location.reload();
        else
            location.href = '/';
    } catch (e) {
        errorAlert('로그아웃에 실패하였습니다.');
    }
}

/**
 * 로그인
 * @param {string} loginId
 * @param {string} loginPw
 * @returns {Promise<Object>}
 */
async function login(loginId, loginPw) {
    return await FETCH.post('/account/login', { loginId, loginPw });
}

export { logout, login };
