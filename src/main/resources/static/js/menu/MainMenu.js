import AbstractTreeNode from "/js/common/AbstractTreeNode.js";

const tag = `[MainMenu]`;
export default class MainMenu extends AbstractTreeNode {
    constructor({
            id, upperMenuId, menuName, programUrl, menuType, orderNum, subMenus
                }) {

        super();

        if(!subMenus)
            subMenus = [];

        Object.assign(this, {id, upperMenuId, menuName, programUrl, menuType, orderNum, subMenus})
    }

    getId() {
        return this.id;
    }

    hasUpper() {
        return !!this.getUpperId();
    }

    hasChildren() {
        return this.subMenus.length > 0
    }

    /**
     * 하위 Menu data를 Menu객체로 변환 후 반환한다.
     * @return {MainMenu[]}
     */
    getChildren() {
        return this.subMenus.map(sm => new MainMenu(sm));
    }

    getUpperId() {
        return this.upperMenuId;
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

    getMenuName() {
        return this.menuName;
    }

    getProgramUrl() {
        return this.programUrl;
    }
}
