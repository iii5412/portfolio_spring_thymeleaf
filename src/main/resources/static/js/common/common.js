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
    await FETCH.getCheck(url);
    return true;
}

/**
 * @param {Menu} menu
 * @return {HTMLElement}
 */
const createContentHeader = (menu) => {
    const headerDiv = createEl('div');
    const titleDiv = createEl('div');
    const refreshDiv = createEl('div');
    const h = createEl('h3', {innerText: menu.getMenuName()});
    const a = createEl('a', {innerText: '새로고침'});

    headerDiv.classList.add('contentHeader');
    titleDiv.classList.add('title');
    refreshDiv.classList.add('refresh');

    titleDiv.appendChild(h);
    refreshDiv.appendChild(a);

    a.addEventListener('click', (event) => {
        event.preventDefault();
        loadContent(menu);
    })

    headerDiv.appendChild(titleDiv);
    headerDiv.appendChild(refreshDiv);
    return headerDiv;
}
/**
 * @param {Menu} menu
 * @return {Promise<void>}
 */
const loadContent = async menu => {
    if(!menu.getProgramUrl()) {
        errorAlert('연결된 URL이 없습니다.');
        return;
    }

    if(!await checkContentUrlStatus(menu.getProgramUrl()))
        return;

    const iframe = createEl('iframe');
    iframe.border = 'none';
    iframe.src = menu.getProgramUrl();

    const headerDiv = createContentHeader(menu);

    const contentArea = qs(document, '#ifr_content');
    contentArea.innerHTML = '';
    contentArea.appendChild(headerDiv);
    contentArea.appendChild(iframe);

    iframe.onerror = (e) => {
        errorAlert('프로그램 로드에 실패하였습니다.');
        throw new Error(e.message);
    }
}

export {loadContent};
