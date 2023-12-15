import {FETCH, toQueryString} from "/js/common/util.js";
import PageResult from "/js/common/PageResult.js";
import RequiredValueError from "/js/error/RequiredValueError.js";

const tag = "[api/program]";
const requstMapping = "/program";
/**
 * 모든 프로그램을 가져옵니다.
 *
 * @param {object} searchParam - 프로그램에 대한 검색 기준입니다.
 * @param {number} page - 페이지 매김을 위한 페이지 번호입니다.
 * @param {number} size - 페이지당 가져올 프로그램 수입니다.
 * @param {string[]} sortFields - 프로그램을 정렬할 필드입니다.
 * @param {string[]} sorts - 각 필드의 정렬 방향입니다.
 * @return {Promise<PageResult>} - 가져온 프로그램을 포함하는 PageResult 개체로 확인되는 약속입니다.
 * @throws {Error} - 프로그램을 가져오는 중 오류가 발생한 경우.
 */
async function fetchAllProgram(searchParam = {}, page = 1, size = 10, sortFields = [], sorts = []) {
    try {
        const responseJson = await FETCH.get(`${requstMapping}?${toQueryString(searchParam)}&page=${page}&size=${size}&sortFields=${sortFields.join(',')}&sorts=${sorts.join(',')}`);
        return new PageResult(responseJson);
    } catch (e) {
        throw e;
    }
}

/**
 * 관리자 프로그램을 제외한 프로그램을 가져옵니다.
 * @param {Object} searchParam - 검색 매개변수입니다.
 * @param {number} page - 페이지 번호입니다.
 * @param {number} size - 페이지당 항목 수입니다.
 * @param {Array} sortFields - 정렬 기준이 되는 필드입니다.
 * @param {Array} sorts - 정렬 방향입니다.
 * @return {PageResult} - 페이지 결과 개체입니다.
 * @throws {Error} - 프로그램을 가져오는 중 오류가 발생한 경우.
 */
async function fetchManageProgram(searchParam = {}, page = 1, size = 10, sortFields = [], sorts = []) {
    try {
        const responseJson = await FETCH.get(`${requstMapping}/manage?${toQueryString(searchParam)}&page=${page}&size=${size}&sortFields=${sortFields.join(',')}&sorts=${sorts.join(',')}`);
        return new PageResult(responseJson);
    } catch (e) {
        throw e;
    }
}

/**
 * 지정된 URL에 POST 요청을 하여 프로그램을 가져와서 저장합니다.
 *
 * @param {string} programName - 저장할 프로그램의 이름입니다.
 * @param {string} url - POST 요청이 전송되는 URL입니다.
 *
 * @return {Promise} POST 요청의 응답 데이터를 해결하는 Promise입니다.
 * 요청이 실패하면 Promise는 오류와 함께 거부됩니다.
 */
function fetchSaveProgram({programName, url}) {
    return FETCH.post(`${requstMapping}`, {programName, url});
}

/**
 * 프로그램을 업데이트합니다.
 *
 * @param {string} id - 업데이트할 프로그램의 ID입니다.
 * @param {string} programName - 새 프로그램 이름입니다.
 * @param {string} url - 프로그램의 새 URL입니다.
 * @return {Promise<undefined>} 프로그램이 성공적으로 업데이트되면 해결되는 Promise입니다. 오류가 발생하면 Promise는 오류 객체로 거부합니다.
 * @throws {RequiredValueError} - 'id' 매개변수가 누락인 경우.
 */
function fetchEditProgram({id, programName, url}) {
    if(!id)
        throw RequiredValueError('id');
    return FETCH.patch(`${requstMapping}`, {id, programName, url})
}

function fetchDeleteProgram(id) {
    if(!id)
        throw RequiredValueError('id');
    return FETCH.delete(`${requstMapping}`, {id});
}
export {fetchAllProgram, fetchManageProgram, fetchSaveProgram, fetchEditProgram, fetchDeleteProgram};
