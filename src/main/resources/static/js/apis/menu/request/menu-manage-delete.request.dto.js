import Dto from '/js/apis/Dto.js';

export default class MenuManageDeleteRequestDto extends Dto {
    /**
     * @type {number}
     */
    id;

    /**
     * @param {Object} param
     * @param param.id
     */
    constructor({ id }) {
        super();
        this.id = id;
    }
}
