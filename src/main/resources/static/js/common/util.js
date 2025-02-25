import {FetchError} from "/js/error/fetchError.js";

/**
 * 일반적인 HTTP 상태 코드를 나타내는 개체입니다.
 *
 * @type {Object}
 * @property {number} OK - 상태 코드가 200인 성공적인 HTTP 응답입니다.
 * @property {number} UNAUTHORIZED - 요청에 사용자 인증이 필요하거나 제공된 사용자 자격 증명이 유효하지 않으며 상태 코드는 401입니다.
 * @property {number} FORBIDDEN - 서버가 요청을 이해했지만 상태 코드 403으로 승인을 거부했습니다.
 * @property {number} NOT_FOUND - 상태 코드 404로 인해 요청한 리소스를 서버에서 찾을 수 없습니다.
 * @property {number} CONFLICT - 서버 리소스의 현재 상태와 충돌하여 요청을 완료할 수 없습니다. code of 409.
 */
const HTTP_STATUS = {
    OK: 200,
    UNAUTHORIZED: 401,
    FORBIDDEN: 403,
    NOT_FOUND: 404,
    CONFLICT: 409,
}
/**
 * Fetch API를 사용하여 HTTP 요청을 만들기 위한 Fetch 유틸리티 클래스를 나타냅니다.
 */
class Fetch {
    /**
     * GET 메소드로 URL을 미리 호출하여 인증 상태 및 권한이 충분한지 확인합니다.
     *
     * @param {string} url - GET 메소드를 사용하여 호출할 URL입니다.
     * @return {Promise<boolean | undefined>} - 인증 상태 및 권한이 충분하면 true로, 그렇지 않으면 정의되지 않은 것으로 확인되는 Promise입니다.
     */
    getCheck(url) {
        return fetch(url, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            }
        }).then(async response => {
            if(response.ok)
                return true;
            else {
                const responseJson = await response.json();
                throw new FetchError(responseJson);
            }
        })
            .catch(error => {
                throw error;
            });
    }

    /**
     * 선택적 헤더를 사용하여 지정된 URL에 GET 요청을 보냅니다.
     *
     * @param {string} url - GET 요청을 보낼 URL입니다.
     * @param {Object} [headers={}] - 요청에 포함할 선택적 헤더입니다.
     * @returns {Promise<any|undefined>} - 요청이 성공하면 JSON 응답으로 확인되고, 요청이 실패하면 오류와 함께 거부하는 Promise입니다.
     */
    get(url, headers = {}) {
        return fetch(url, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
                ...headers
            }
        })
            .then(async response => {
                const responseJson = await response.json();
                if (response.ok) {
                    return responseJson;
                } else {
                    throw new FetchError(responseJson);
                }
            })
            .catch(error => {
                throw error;
            });
    }

    /**
     * 제공된 데이터 및 헤더와 함께 지정된 URL에 POST 요청을 보냅니다.
     *
     * @param {string} url - POST 요청이 전송될 URL입니다.
     * @param {any} data - 요청 본문에 전송될 데이터입니다.
     * @param {Object} headers - 요청에 포함될 추가 헤더입니다. 기본값은 빈 개체입니다.
     * @return {Promise<any | undefined>} - 요청이 성공하면 구문 분석된 JSON 응답을 확인하는 Promise,
     * or throws a FetchError with the parsed error response if the request fails.
     */
    post(url, data, headers = {}) {
        const init = {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
                ...headers
            },
        };

        if (init.headers["Content-Type"] === 'application/json')
            init.body = JSON.stringify(data);
        else
            init.body = data;

        return fetch(url, init)
            .then(async response => {
                const responseJson = await response.json();
                if (response.ok) {
                    return responseJson;
                } else {
                    throw new FetchError(responseJson)
                }
            })
            .catch(error => {
                throw error;
            });
    }

    /**
     * HTTP PATCH 요청을 수행합니다.
     *
     * @param {string} url - 요청을 보낼 URL입니다.
     * @param {Object} data - 요청 본문에 전송될 데이터입니다.
     * @param {Object} headers - 요청에 포함될 선택적 헤더입니다.
     * @return {Promise<any | undefined>} - 요청이 성공하면 JSON 응답으로 확인되고, 요청이 실패하면 오류와 함께 거부되는 Promise입니다.
     */
    patch(url, data, headers = {}) {
        const init = {
            method: 'PATCH',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
                ...headers
            },
        };

        if (init.headers["Content-Type"] === 'application/json')
            init.body = JSON.stringify(data);
        else
            init.body = data;

        return fetch(url, init)
            .then(async response => {
                const responseJson = await response.json();
                if (response.ok) {
                    return responseJson;
                } else {
                    throw new FetchError(responseJson)
                }
            })
            .catch(error => {
                throw error;
            });
    }

    /**
     * DELETE HTTP 메서드를 사용하여 지정된 URL에서 데이터를 삭제합니다.
     *
     * @param {string} url - HTTP DELETE 요청을 보낼 URL입니다.
     * @param {any} data - 요청 본문과 함께 보낼 데이터입니다. Content-Type 헤더에 따라 모든 유형이 될 수 있습니다.
     * @param {Object} headers - 요청에 포함할 선택적 HTTP 헤더입니다.
     * @returns {Promise<any>} - 요청이 성공하면 응답 데이터를 확인하고, 요청이 실패하면 FetchError로 거부하는 Promise입니다.
     */
    delete(url, data, headers = {}) {
        const init = {
            method: 'DELETE',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
                ...headers
            },
        };

        if (init.headers["Content-Type"] === 'application/json')
            init.body = JSON.stringify(data);
        else
            init.body = data;

        return fetch(url, init)
            .then(async response => {
                const responseJson = await response.json();
                if (response.ok) {
                    return responseJson;
                } else {
                    throw new FetchError(responseJson)
                }
            })
            .catch(error => {
                throw error;
            });
    }
}
/**
 * 제공된 대상 요소 내에서 지정된 선택자와 일치하는 첫 번째 요소를 찾아 반환합니다.
 *
 * @param {Document | Element} target - 일치하는 요소를 검색할 요소입니다.
 * @param {string} selector - 일치하는 요소를 찾는 데 사용되는 CSS 선택기입니다.
 * @return {HTMLElement} - 제공된 대상 요소 내에서 지정된 선택기와 일치하는 첫 번째 요소이거나, 일치하는 항목이 없으면 null입니다.
 */
const qs = (target, selector) => target.querySelector(selector);
/**
 * 주어진 선택자와 일치하는 모든 요소를 포함하는 배열을 반환합니다.
 *
* @param {Element} target - 쿼리할 대상 요소
 * @param {string} selector - 요소를 일치시킬 CSS 선택기
 * @returns {Array} - 일치하는 요소를 포함하는 배열
 */
const qsAll = (target, selector) => Array.from(target.querySelectorAll(selector));
/**
 * 개체를 쿼리 문자열로 변환합니다.
 *
 * @param {Object} obj - 쿼리 문자열로 변환할 개체입니다.
 * @returns {string} The query string representation of the provided object.
 */
const toQueryString = (obj) => {
    return Object.entries(obj).map(([key, value]) => {
        // return `${encodeURIComponent(key)}=${encodeURIComponent(value === undefined ? '' : value)}`;
        return `${key}=${value === undefined ? '' : value}`;
    }).join('&');
}
/**
 * 지정된 태그와 옵션을 사용하여 새 HTML 요소를 만듭니다.
 *
* @param {String} tag - 생성할 요소의 HTML 태그 이름입니다.
 * @param {Object} options - 선택 사항입니다. 요소에 할당할 속성이 포함된 개체입니다.
 * @returns {HTMLElement} - 새로 생성된 HTML 요소입니다.
 */
const createEl = (tag, options = {}) => Object.assign(document.createElement(tag), options);
/**
 * @description 문자열을 HTML 요소로 변환합니다.
 * @param {string} string
 * @return {HTMLElement|null}
 */
const stringToHTMLElement = (string) => {
    const template = document.createElement('template');
    template.innerHTML = string.trim();
    return template.content.firstChild instanceof HTMLElement ? template.content.firstChild : null;
}
const FETCH = new Fetch();
const lpad = (str, len, char) => {
    str = String(str); // ensures that str is a string
    return str.length >= len ? str : char.repeat(len - str.length) + str;
};
const isEmptyObject = (object) => {
    return Object.keys(object).length === 0;
}
export {
    qs,
    qsAll,
    createEl,
    toQueryString,
    FETCH,
    HTTP_STATUS,
    lpad,
    isEmptyObject,
    stringToHTMLElement,
};
