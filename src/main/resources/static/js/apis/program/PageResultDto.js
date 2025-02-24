import Dto from "/js/apis/Dto.js";

export default class PageResultDto extends Dto {
    /**
     * @type {any[]}
     */
    result;
    /**
     * @type {number}
     */
    totalCount;
    /**
     * @type {number}
     */
    page;
    /**
     * @type {number}
     */
    size;

    /**
     * @param paramObj
     * @param {any[]} paramObj.result
     * @param {number} paramObj.totalCount
     * @param {number} paramObj.page
     * @param {number} paramObj.size
     */
    constructor({result, totalCount, page, size}) {
        super();
        this.result = result;
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
    }

    /**
     *
     * @return {*[]}
     */
    getResult() {
       return this.result;
    }

    /**
     *
     * @return {number}
     */
    getTotalCount() {
        return this.totalCount;
    }
}
