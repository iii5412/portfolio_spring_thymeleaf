import { FETCH, toQueryString } from '/js/common/util.js';
import RequiredValueError from '/js/error/RequiredValueError.js';
import Program from '/js/program/Program.js';
import ProgramSearchResponseDto from '/js/apis/program/response/program-manage.search.response.dto';

const tag = '[api/program]';
const DOMAIN = '/program';

/**
 * 모든 프로그램을 가져옵니다.
 * @returns {Promise<Program[]>}
 */
async function fetchAllProgram() {
    const response = await FETCH.get(`${DOMAIN}`);
    return programMapping(response);
}

/**
 *
 * @param {ProgramSearchRequestDto} programSearchRequestDto
 * @return {Promise<ProgramSearchResponseDto>}
 */
async function fetchManageProgram(programSearchRequestDto) {
    const paramObj = programSearchRequestDto.toObject();
    const response = await FETCH.get(`${DOMAIN}/manage?${toQueryString(paramObj)}`);
    return new ProgramSearchResponseDto(response);
}

/**
 * 지정된 URL에 POST 요청을 하여 프로그램을 가져와서 저장합니다.
 * @param {ProgramManageCreateRequestDto} programManageCreateRequestDto - 프로그램 생성 매개변수입니다.
 * @returns {Promise} POST 요청의 응답 데이터를 해결하는 Promise입니다.
 * 요청이 실패하면 Promise는 오류와 함께 거부됩니다.
 */
function fetchCreateProgram(programManageCreateRequestDto) {
    return FETCH.post(`${DOMAIN}`, programManageCreateRequestDto.toObject());
}

/**
 * @param {ProgramManageEditRequestDto} programManageEditRequestDto
 * @param programManageEditRequestDto
 * @returns {Promise<*|undefined>}
 */
function fetchEditProgram(programManageEditRequestDto) {
    const { id } = programManageEditRequestDto.toObject();

    if (!id)
        throw RequiredValueError('id');

    return FETCH.patch(`${DOMAIN}`, programManageEditRequestDto.toObject());
}

/**
 * 프로그램을 삭제합니다.
 * @param {ProgramManageDeleteRequestDto} programManageDeleteRequestDto
 * @returns {Promise<*>}
 */
function fetchDeleteProgram(programManageDeleteRequestDto) {
    const { id } = programManageDeleteRequestDto.toObject();
    if (!id)
        throw RequiredValueError('id');
    return FETCH.delete(`${DOMAIN}`, { id });
}

/**
 * @param {object[]} data
 * @returns {Program[]}
 */
function programMapping(data = []) {
    return data.map(d => new Program(d));
}

export {
    fetchAllProgram,
    fetchManageProgram,
    fetchCreateProgram,
    fetchEditProgram,
    fetchDeleteProgram,
};
