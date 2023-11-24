import {FETCH} from "/js/common/util.js";
import PageResult from "/js/common/PageResult.js";

const tag = "[api/program]";

/**
 * @return {PageResult}
 */
async function fetchProgram() {
    try {
        const response = await FETCH.get('/program');
        if (response.ok) {
            return new PageResult(await response.json());
        } else {
            throw new Error(response.statusText);
        }
    } catch (e) {
        throw e;
    }
}

export {fetchProgram};
