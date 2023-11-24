export default class PageResult {
    result = [];
    totalCount = 0;


    constructor({result, totalCount}) {
        this.result = result;
        this.totalCount = totalCount;
    }
}
