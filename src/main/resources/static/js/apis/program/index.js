import {FETCH, toQueryString} from "/js/common/util.js";
import ProgramSearchResponseDto from "/js/apis/program/response/program-manage.search.response.dto.js";


const DOMAIN = '/program';


/**
 *
 * @param {ProgramSearchRequestDto} programSearchRequestDto
 * @return {Promise<ProgramSearchResponseDto>}
 */
async function fetchManageProgram(programSearchRequestDto) {
    try {
        const paramObj = programSearchRequestDto.toObject();
        const response = await FETCH.get(`${DOMAIN}/manage?${toQueryString(paramObj)}`);
        return new ProgramSearchResponseDto(response);
    } catch (e) {
        throw e;
    }
}

export {fetchManageProgram};
