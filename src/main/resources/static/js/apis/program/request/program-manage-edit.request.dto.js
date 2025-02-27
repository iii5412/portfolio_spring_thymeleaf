import Dto from '/js/apis/Dto.js';
/**
 * 프로그램 생성 요청 DTO
 * @class ProgramManageEditRequestDto
 */
export default class ProgramManageEditRequestDto extends Dto {
    /**
     * @type {number}
     */
    id;
    /**
     * @type {string}
     */
    programName;
    /**
     * @type {string}
     */
    url;

    /**
     * @description 프로그램 생성 요청 DTO
     * @param {number} id
     * @param {string} programName
     * @param {string} url
     */
    constructor({ id, programName, url }) {
        super();
        this.id = id;
        this.programName = programName;
        this.url = url;
    }

    /**
     * @description DTO를 객체로 변환합니다.
     * @returns {{id: number, programName: string, url: string}}
     */
    toObject() {
        return {
            ...super.toObject(),
            id: this.id,
            programName: this.programName,
            url: this.url,
        };
    }
}
