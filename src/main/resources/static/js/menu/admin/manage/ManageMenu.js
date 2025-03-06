import { MENU_TYPE } from '/js/menu/menuConstants.js';
import AbstractTreeNode from '/js/common/AbstractTreeNode.js';

const tag = '[ManageMenu]';

export default class ManageMenu {
    /**
     * @type {number}
     */
    id;
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
     *
     * @type {ManageMenu[]}
     */
    subMenus = [];
    /**
     * @type {number|null}
     */
    upperMenuId;
    /**
     * @type {string|null}
     */
    upperMenuName;
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
    lastModifiedByLoginId;
    /**
     * @type {string}
     */
    createdAt;
    /**
     * @type {string}
     */
    updatedAt;
    /**
     * @param {object} param
     * @param {number} param.id
     * @param {string} param.menuName
     * @param {MENU_TYPE} param.menuType
     * @param {ROLE_CODE} param.roleCode
     * @param {number} param.orderNum
     * @param {ManageMenu[]} [param.subMenus]
     * @param {number|null} [param.upperMenuId]
     * @param {string|null} [param.upperMenuName]
     * @param {number|null} param.programId
     * @param {string|null} param.programName
     * @param {string} param.lastModifiedByLoginId
     * @param {string} param.createdAt
     * @param {string} param.updatedAt
     */
    constructor({ id, menuName, menuType, roleCode, orderNum, subMenus, upperMenuId, upperMenuName
        , programId, programName
        , lastModifiedByLoginId, createdAt, updatedAt  }) {

        if (!id)
            throw tag + 'id가 없는 Menu가 있습니다.';

        if (!subMenus)
            subMenus = [];

        Object.assign(this, {
            id, menuName, menuType, roleCode, orderNum, subMenus, upperMenuId, upperMenuName
            , programId, programName
            , lastModifiedByLoginId, createdAt, updatedAt,
        });
    }

    hasUpper() {
        return !!this.getUpperId();
    }

    hasChildren() {
        return this.subMenus.length > 0;
    }

    /**
     * 하위 Menu data를 Menu객체로 변환 후 반환한다.
     * @return {ManageMenu[]}
     */
    getSubMenus() {
        return this.subMenus.map(sm => new ManageMenu(sm));
    }

    /**
     *
     * @returns {number|null}
     */
    getUpperId() {
        return this.upperMenuId;
    }

    /**
     *
     * @returns {number}
     */
    getId() {
        return this.id;
    }

    /**
     *
     * @returns {string}
     */
    getMenuName() {
        return this.menuName;
    }

    /**
     *
     * @returns {MENU_TYPE}
     */
    getMenuType() {
        return this.menuType;
    }

    /**
     *
     * @returns {ROLE_CODE}
     */
    getRoleCode() {
        return this.roleCode;
    }

    /**
     *
     * @returns {string|null}
     */
    getUpperName() {
        return this.upperMenuName;
    }

    /**
     *
     * @returns {number|null}
     */
    getProgramId() {
        return this.programId;
    }

    /**
     *
     * @returns {string|null}
     */
    getProgramName() {
        return this.programName;
    }

    /**
     *
     * @returns {number}
     */
    getOrderNum() {
        return this.orderNum;
    }

    /**
     *
     * @returns {string}
     */
    getLastModifiedByLoginId() {
        return this.lastModifiedByLoginId;
    }

    /**
     *
     * @returns {string}
     */
    getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     *
     * @param {ManageMenu[]} subMenus
     */
    setSubMenus(subMenus) {
        this.subMenus = subMenus;
    }

    /**
     * @return {boolean}
     */
    isProgramMenu() {
        return this.menuType === MENU_TYPE.PROGRAM;
    }

    /**
     *
     * @returns {boolean}
     */
    isFolderMenu() {
        return this.menuType === MENU_TYPE.FOLDER;
    }

    getUpperMenuName() {
        return this.upperMenuName;
    }
}

