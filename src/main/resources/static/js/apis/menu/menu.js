import {FETCH} from "/js/common/util.js";

const tag = "[api/menu]";

async function fetchMenu() {
    try {
        return await FETCH.get('/menu');
    } catch (e) {
        throw e;
    }
}

export {fetchMenu};
