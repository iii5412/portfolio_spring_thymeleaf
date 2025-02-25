import ResponseDto from "/js/apis/response/ResponseDto.js";

export default class PageResultResponseDto extends ResponseDto {
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
    getPage() {
        return this.page;
    }

    /**
     *
     * @return {number}
     */
    getSize() {
        return this.size;
    }

    /**
     *
     * @return {number}
     */
    getTotalCount() {
        return this.totalCount;
    }

    /**
     * @override
     * @return {{result: *[], size: number, page: number, totalCount: number}}
     */
    toObject() {
        return Object.assign(super.toObject(), {
            result: this.result,
            totalCount: this.totalCount,
            page: this.page,
            size: this.size,
        });
    }
}
