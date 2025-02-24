import Dto from "/js/apis/Dto.js";

export default class ProgramSearchRequestDto extends Dto {
    /**
     * @type {number}
     */
    page;
    /**
     * @type {number}
     */
    size;
    /**
     * @type {string[]}
     */
    sortFields = [];
    /**
     * @type {string[]}
     */
    sorts = [];
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
     *
     * @param {object} paramObj
     * @param {number} paramObj.page
     * @param {number} paramObj.size
     * @param {string[]} paramObj.sortFields
     * @param {string[]} paramObj.sorts
     * @param {number} paramObj.id
     * @param {string} paramObj.programName
     * @param {string} paramObj.url
     */
    constructor({page = 1, size = 10, sortFields = [], sorts = [], id, programName, url}) {
        super();
    }

    /**
     *
     * @return {{size: number, programName: string, sortFields: string[], page: number, id: number, sorts: string[], url: string}}
     */
    toObject() {
        return {
            page: this.page,
            size: this.size,
            sortFields: this.sortFields.join(','),
            sorts: this.sorts.join(','),
            id: this.id,
            programName: this.programName,
            url: this.url,
        }
    }
}
