import {FETCH} from "/js/common/util.js";

const tag = "[api/menu]";

function fetchMenu() {
    try {
        return FETCH.get('/menu');
    } catch (e) {
        throw e;
    }
}

export {fetchMenu};
