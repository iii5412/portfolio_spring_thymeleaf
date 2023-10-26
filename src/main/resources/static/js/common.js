import {FETCH, qs} from '/js/util.js';

const loadContent = url => {
    FETCH.get(url)
        .then(response => response.text())
        .then(data => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(data, 'text/html');
            const content = qs(doc, '.content')
            qs(document, '.content').innerHTML = content.innerHTML;
        })
}


export {loadContent};
