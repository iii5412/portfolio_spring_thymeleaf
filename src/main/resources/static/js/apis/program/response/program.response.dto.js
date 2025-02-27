import ResponseDto from '/js/apis/response/ResponseDto.js';

export default class ProgramResponseDto extends ResponseDto {
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
     * @type {string}
     */
    createdAt;
    /**
     * @type {string}
     */
    updatedAt;
    /**
     * @type {string}
     */
    lastUpdatedByUserLoginId;

    /**
     * Program Response Dto
     * @param {number} id
     * @param {string} programName
     * @param {string} url
     * @param {string} createdAt
     * @param {string} updatedAt
     * @param {string} lastUpdatedByUserLoginId
     */
    constructor({ id, programName, url, createdAt, updatedAt, lastUpdatedByUserLoginId }) {
        super();
        Object.assign(this, { id, programName, url, createdAt, updatedAt, lastUpdatedByUserLoginId });
    }

    /**
     *
     * @return {{createdAt: string, programName: string, lastUpdatedByUserLoginId: string, id: number, url: string, updatedAt: string}}
     */
    toObject() {
        return {
            id: this.id,
            programName: this.programName,
            url: this.url,
            createdAt: this.createdAt,
            updatedAt: this.updatedAt,
            lastUpdatedByUserLoginId: this.lastUpdatedByUserLoginId,
        };
    }
}
