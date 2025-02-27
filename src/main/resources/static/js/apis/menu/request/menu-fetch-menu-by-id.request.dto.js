import Dto from '/js/apis/Dto.js';

export default class FetchMenuByIdRequestDto extends Dto {
    /**
     * @type {number}
     */
    id;

    constructor(id) {
        super();
        this.id = id;
    }

    /**
     * DTO를 오브젝트로 변환합니다.
     * @returns {{id: number}}
     */
    toObject() {
        return {
            ...super.toObject(),
            id: this.id,
        };
    }
}
