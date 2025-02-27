import Dto from '/js/apis/Dto.js';

export default class ProgramManageDeleteRequestDto extends Dto {
    /**
     * @type {number}
     */
    id;

    constructor(id) {
        super();
        this.id = id;
    }

    /**
     * @description DTO를 객체로 변환합니다.
     * @returns {{id: number}}
     */
    toObject() {
        return {
            ...super.toObject(),
            id: this.id,
        };
    }
}
