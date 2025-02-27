import Dto from '/js/apis/Dto.js';

export default class MenuManageSearchRequestDto extends Dto {
    /**
     * @type {number}
     */
    id;
    /**
     * @type {string}
     */
    menuName;
    /**
     * @type {ROLE_CODE}
     */
    roleCode;

    /**
     * 메뉴 관리 검색 요청 DTO
     * @param {number} id
     * @param {string} menuName
     * @param {ROLE_CODE} roleCode
     */
    constructor({ id, menuName, roleCode }) {
        super();
        this.id = id;
        this.menuName = menuName;
        this.roleCode = roleCode;
    }

    /**
     * DTO를 오브젝트로 변환합니다.
     * @returns {{id: number, menuName: string, roleCode: ROLE_CODE}}
     */
    toObject() {
        return {
            ...super.toObject(),
            id: this.id,
            menuName: this.menuName,
            roleCode: this.roleCode,
        };
    }
}
