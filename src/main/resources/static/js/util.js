class Fetch {
    get(url, headers = {}) {
        return fetch(url, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
                ...headers
            }
        })
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

        if(init.headers["Content-Type"] === 'application/json')
            init.body = JSON.stringify(data);
        else
            init.body = data;

        return fetch(url, init);
    }
}

const qs = (target, selector) => target.querySelector(selector);
const qsAll = (target, selector) => Array.from(target.querySelectorAll(selector));
const FETCH = new Fetch();

export {qs, qsAll, FETCH};
