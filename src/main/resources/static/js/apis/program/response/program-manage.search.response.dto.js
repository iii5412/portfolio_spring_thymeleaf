import PageResultDto from "/js/apis/program/PageResultDto.js";
import ProgramResponseDto from "/js/apis/program/response/program.response.dto.js";

export default class ProgramSearchResponseDto extends PageResultDto {
    /**
     * @param paramObj
     * @param {object[]} paramObj.result
     * @param {number} paramObj.totalCount
     * @param {number} paramObj.page
     * @param {number} paramObj.size
     */
    constructor({result, totalCount, page, size}) {
        super({result, totalCount, page, size});
        this.result = result.map(program => new ProgramResponseDto(program));
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
    }

    /**
     *
     * @return {ProgramResponseDto[]}
     */
    getResult() {
        return this.result;
    }

    /**
     *
     * @override
     * @return {{result: {createdAt: string, programName: string, lastUpdatedByUserLoginId: string, id: number, url: string, updatedAt: string}[], size: number, page: number, totalCount: number}}
     */
    toObject() {
        return {
            result: this.result.map(programDto => programDto.toObject()),
            totalCount: this.totalCount,
            page: this.page,
            size: this.size,
        }
    }
}
