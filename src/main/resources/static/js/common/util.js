import {FetchError} from "/js/error/fetchError.js";


const HTTP_STATUS = {
    OK: 200,
    UNAUTHORIZED: 401,
    FORBIDDEN: 403,
    NOT_FOUND: 404,
    CONFLICT: 409,
}

class Fetch {
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
 * @param {HTMLElement | Document} target
 * @param {string} selector
 * @return {HTMLElement | null}
 */
const qs = (target, selector) => target.querySelector(selector);
/**
 * @param {HTMLElement | Document} target
 * @param {string} selector
 * @return {HTMLElement[]}
 */
const qsAll = (target, selector) => Array.from(target.querySelectorAll(selector));
/**
 * @param {string} tag
 * @param {Object} options
 * @return {HTMLElement}
 */
const createEl = (tag, options = {}) => Object.assign(document.createElement(tag), options);
const FETCH = new Fetch();

export {
    qs,
    qsAll,
    createEl,
    FETCH,
    HTTP_STATUS,
};
