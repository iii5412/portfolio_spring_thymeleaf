import {createEl, FETCH, qs, HTTP_STATUS} from '/js/common/util.js';
import {errorAlert} from "/js/common/alert.js";
import {goLoginPage, goMainPage} from "/js/common/route.js";
import {FetchError} from "/js/error/fetchError.js";

const tag = '[common]'
/**
 * @param {string} url
 * @return {HTTP_STATUS}
 */
const checkContentUrlStatus = async url => {
    try {
        await FETCH.get(url);
        return true;
    } catch(e) {
        if(e instanceof FetchError) {

            switch(e.status) {
                case HTTP_STATUS.UNAUTHORIZED :
                    errorAlert("인증을 확인해주세요.");
                    goLoginPage();
                    break;
                case HTTP_STATUS.FORBIDDEN :
                    errorAlert("권한이 없습니다.");
                    break;
            }
        }
    }
}

const loadContent = async url => {
    if(!await checkContentUrlStatus(url))
        return;

    const iframe = createEl('iframe');
    iframe.border = 'none';
    iframe.style.width = '100%';
    iframe.src = url;

    const contentArea = qs(document, '.content');
    contentArea.innerHTML = '';
    contentArea.appendChild(iframe);

    iframe.onerror = (e) => {
        errorAlert('프로그램 로드에 실패하였습니다.');
        throw new Error(e);
    }
}

export {loadContent};
