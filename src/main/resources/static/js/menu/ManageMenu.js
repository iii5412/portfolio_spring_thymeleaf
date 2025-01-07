import {MENU_TYPE} from "/js/menu/menuConstants.js";
import AbstractTreeNode from "/js/common/AbstractTreeNode.js";

const tag = `[ManageMenu]`;

export default class ManageMenu extends AbstractTreeNode {

    constructor({
                    id, menuName, menuType, roleCode, orderNum, subMenus, upperMenuId, upperMenuName
                    , programId, programName
                    , lastModifiedByLoginId, createdAt, updatedAt
                }) {

        super();

        if (!id)
            throw tag + "id가 없는 Menu가 있습니다.";

        if(!subMenus)
            subMenus = [];

        Object.assign(this, {
            id, menuName, menuType, roleCode, orderNum, subMenus, upperMenuId, upperMenuName
            , programId, programName
            , lastModifiedByLoginId, createdAt, updatedAt
        })
    }

    hasUpper() {
        return !!this.getUpperId();
    }

    hasChildren() {
        return this.subMenus.length > 0
    }

    /**
     * 하위 Menu data를 Menu객체로 변환 후 반환한다.
     * @return {ManageMenu[]}
     */
    getChildren() {
        return this.subMenus.map(sm => new ManageMenu(sm));
    }

    getUpperId() {
        return this.upperMenuId;
    }

    getId() {
        return this.id;
    }

    getMenuName() {
        return this.menuName;
    }

    getMenuType() {
        return this.menuType;
    }

    getRoleCode() {
        return this.roleCode;
    }

    getUpperName() {
        return this.upperMenuName;
    }

    getProgramId() {
        return this.programId;
    }

    getProgramName() {
        return this.programName;
    }

    getOrderNum() {
        return this.orderNum;
    }

    getLastModifiedByLoginId() {
        return this.lastModifiedByLoginId;
    }

    getUpdatedAt() {
        return this.updatedAt;
    }

    setSubMenus(subMenus) {
        this.subMenus = subMenus;
    }

    /**
     * @return {boolean}
     */
    isProgramMenu() {
        return this.menuType === MENU_TYPE.PROGRAM;
    }

    isFolderMenu() {
        return this.menuType === MENU_TYPE.FOLDER;
    }

    getProgramUrl() {
        return this.programUrl;
    }

    getUpperMenuName() {
        return this.upperMenuName;
    }
}


