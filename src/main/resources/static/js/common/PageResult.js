export default class PageResult {
    result = [];
    totalCount = 0;

    getResult() {
        return this.result;
    }

    getTotalCount() {
        return this.totalCount;
    }
    constructor({result, totalCount}) {
        this.result = result;
        this.totalCount = totalCount;
    }
}
