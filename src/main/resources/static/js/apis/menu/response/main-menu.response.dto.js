import Dto from '/js/apis/Dto.js';

/**
 * @description 메인 메뉴 응답 DTO
 */
export default class MainMenuResponseDto extends Dto {
    /**
     * @type {number}
     */
    id;
    /**
     * @type {number}
     */
    upperMenuId;
    /**
     * @type {string}
     */
    menuName;
    /**
     * @type {string}
     */
    programUrl;
    /**
     * @type {MENU_TYPE}
     */
    menuType;
    /**
     * @type {number}
     */
    orderNum;
    /**
     * @type {MainMenuResponseDto[]}
     */
    subMenus = [];

    /**
     * 메인 메뉴 응답 DTO
     * @param {object} param
     * @param {number} param.id
     * @param {number} param.upperMenuId
     * @param {string} param.menuName
     * @param {string} param.programUrl
     * @param {MENU_TYPE} param.menuType
     * @param {number} param.orderNum
     * @param {MainMenuResponseDto[]} param.subMenus
     */
    constructor({ id, upperMenuId, menuName, programUrl, menuType, orderNum, subMenus }) {
        super();
        this.id = id;
        this.upperMenuId = upperMenuId;
        this.menuName = menuName;
        this.programUrl = programUrl;
        this.menuType = menuType;
        this.orderNum = orderNum;

        if (subMenus)
            this.subMenus = subMenus.map(sm => new MainMenuResponseDto(sm));
    }

    /**
     * DTO를 오브젝트로 변환합니다.
     * @returns {{id: number, upperMenuId: number, menuName: string, programUrl: string, menuType: MENU_TYPE, orderNum: number, subMenus: MainMenuResponseDto[]}}
     */
    toObject() {
        return {
            ...super.toObject(),
            id: this.id,
            upperMenuId: this.upperMenuId,
            menuName: this.menuName,
            programUrl: this.programUrl,
            menuType: this.menuType,
            orderNum: this.orderNum,
            subMenus: this.subMenus,
        };
    }
}
