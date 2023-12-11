import {FETCH} from "/js/common/util.js";
import PageResult from "/js/common/PageResult.js";

const tag = "[api/program]";

/**
 * @return {PageResult}
 */
async function fetchAllProgram(sortFields = [], sorts = []) {
    try {
        const responseJson = await FETCH.get(`/program?sortFields=${sortFields.join(',')}&sorts=${sorts.join(',')}`);
        return new PageResult(responseJson);
    } catch (e) {
        throw e;
    }
}

/**
 * @return {PageResult}
 */
async function fetchManageProgram(sortFields = [], sorts = []) {
    try {
        const responseJson = await FETCH.get(`/program?sortFields=${sortFields.join(',')}&sorts=${sorts.join(',')}`);
        return new PageResult(responseJson);
    } catch (e) {
        throw e;
    }adm
}

export {fetchAllProgram, fetchManageProgram};
