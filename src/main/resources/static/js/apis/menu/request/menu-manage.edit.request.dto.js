import Dto from '/js/apis/Dto.js';

export default class MenuManageEditRequestDto extends Dto {
    /**
     * @type {number}
     */
    id;
    /**
     * @type {number|null}
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
     * @type {number}
     */
    programId;
    /**
     * @type {ROLE_CODE}
     */
    roleCode;

    /**
     * @param {object} param
     * @param {number} param.id
     * @param {number|null} [param.upperId]
     * @param {string} param.menuName
     * @param {MENU_TYPE} param.menuType
     * @param {number} param.orderNum
     * @param {number} param.programId
     * @param {ROLE_CODE} param.roleCode
     */
    constructor({ id, upperId, menuName, menuType, orderNum, programId, roleCode }) {
        super();
        this.id = id;
        this.upperId = upperId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.programId = programId;
        this.roleCode = roleCode;
    }

    toObject() {
        return {
            ...super.toObject(),
            id: this.id,
            upperId: this.upperId,
            menuName: this.menuName,
            menuType: this.menuType,
            orderNum: this.orderNum,
            programId: this.programId,
            roleCode: this.roleCode,
        };
    }
}
