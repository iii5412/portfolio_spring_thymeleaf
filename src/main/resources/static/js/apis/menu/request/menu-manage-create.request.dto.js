import Dto from '/js/apis/Dto.js';

export default class MenuManageCreateRequestDto extends Dto {
    /**
     * @type {number}
     */
    upperId;
    /**
     * @type {string}
     */
    menuName;
    /**
     * @type {MENU_TYPE}
     */
    menuType;
    /**
     * @type {number}
     */
    orderNum;
    /**
     * @type {ROLE_CODE}
     */
    roleCode;

    constructor(upperId, menuName, menuType, orderNum, roleCode) {
        super();
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.roleCode = roleCode;
    }

    /**
     * DTO를 오브젝트로 변환합니다.
     * @returns {{upperId, menuName, menuType, orderNum, roleCode}}
     */
    toObject() {
        return {
            ...super.toObject(),
            upperId: this.upperId,
            menuName: this.menuName,
            menuType: this.menuType,
            orderNum: this.orderNum,
            roleCode: this.roleCode,
        };
    }
}
