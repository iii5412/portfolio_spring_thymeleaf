/**
 * 결과 목록과 총 개수가 포함된 페이지 결과를 나타냅니다.
 *
 * @class
 */
export default class PageResult {
    result = [];
    totalCount = 0;

    /**
     * 메소드 실행 결과를 검색합니다.
     *
     * @return {Array} 메소드 실행 결과입니다.
     */
    getResult() {
        return this.result;
    }

   /**
     * 총 개수를 반환합니다.
     *
     * @return {number} 총 개수 값입니다.
     */
    getTotalCount() {
        return this.totalCount;
    }
    constructor({result, totalCount}) {
        this.result = result;
        this.totalCount = totalCount;
    }
}
