import ResponseDto from '/js/apis/response/ResponseDto.js';

export default class MenuManageSearchResponseDto extends ResponseDto {
    /**
     * @type {number}
     */
    id;
    /**
     * @type {number|null}
     */
    upperMenuId;
    /**
     * @type {number|null}
     */
    programId;
    /**
     * @type {string|null}
     */
    programName;
    /**
     * @type {string}
     */
    menuName;
    /**
     * @type {MENU_TYPE}
     */
    menuType;
    /**
     * @type {ROLE_CODE}
     */
    roleCode;
    /**
     * @type {number}
     */
    orderNum;
    /**
     * @type {string}
     */
    lastModifiedByLoginId;
    /**
     *
     * @type {MenuManageSearchResponseDto[]}
     */
    subMenus = [];
    /**
     * @type {string}
     */
    createdAt;
    /**
     * @type {string}
     */
    updatedAt;

    /**
     * 메뉴 관리 검색 응답 DTO
     * @param {object} param
     * @param {number} param.id
     * @param {number|null} param.upperMenuId
     * @param {number|null} param.programId
     * @param {string|null} param.programName
     * @param {string} param.menuName
     * @param {MENU_TYPE} param.menuType
     * @param {ROLE_CODE} param.roleCode
     * @param {number} param.orderNum
     * @param {string} param.lastModifiedByLoginId
     * @param {MenuManageSearchResponseDto[]} param.subMenus
     * @param {string} param.createdAt
     * @param {string} param.updatedAt
     */
    constructor({ id, upperMenuId, programId, programName, menuName, menuType, roleCode, orderNum, lastModifiedByLoginId, subMenus, createdAt, updatedAt }) {
        super();
        this.id = id;
        this.upperMenuId = upperMenuId;
        this.programId = programId;
        this.programName = programName;
        this.menuName = menuName;
        this.menuType = menuType;
        this.roleCode = roleCode;
        this.orderNum = orderNum;
        this.lastModifiedByLoginId = lastModifiedByLoginId;
        this.subMenus = subMenus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * DTO를 오브젝트로 변환합니다.
      * @returns {{id: number, upperMenuId: number, programId: number, programName: string, menuName: string, menuType: MENU_TYPE, roleCode: ROLE_CODE, orderNum: number, lastModifiedByLoginId: string, subMenus: MenuManageSearchResponseDto[], createdAt: string, updatedAt: string}}
     */
    toObject() {
        return {
            ...super.toObject(),
            id: this.id,
            upperMenuId: this.upperMenuId,
            programId: this.programId,
            programName: this.programName,
            menuName: this.menuName,
            menuType: this.menuType,
            roleCode: this.roleCode,
            orderNum: this.orderNum,
            lastModifiedByLoginId: this.lastModifiedByLoginId,
            subMenus: this.subMenus,
            createdAt: this.createdAt,
            updatedAt: this.updatedAt,
        };
    }
}
