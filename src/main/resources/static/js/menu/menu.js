import {Program} from "/js/program/program.js";

const tag = '[menu]';

class Menu {

    static MENU_TYPE = {
        FOLDER: 'FOLDER',
        PROGRAM: 'PROGRAM'
    }

    constructor({
                    id, menuName, menuType, orderNum, subMenus = [], upperMenuId = null
                    , programId = null, programUrl = null
                    , lastModifiedByLoginId, createdAt, updatedAt
                }) {
        if (!id)
            throw tag + "id가 없는 Menu가 있습니다.";

        Object.assign(this, {
            id, menuName, menuType, orderNum, subMenus, upperMenuId
            , programId, programUrl
            , lastModifiedByLoginId, createdAt, updatedAt
        })
    }

    hasSubMenus() {
        return this.subMenus.length > 0
    }

    hasUpper() {
        return !!this.getUpperId();
    }

    getMenuId() {
        return this.id;
    }

    getUpperId() {
        return this.upperMenuId;
    }

    /**
     * @return {Menu[]}
     */
    getSubMenus() {
        return this.subMenus;
    }

    setSubMenus(subMenus) {
        this.subMenus = subMenus;
    }

    /**
     * @return {boolean}
     */
    isProgramMenu() {
        return this.menuType === Menu.MENU_TYPE.PROGRAM;
    }

    isFolderMenu() {
        return this.menuType === Menu.MENU_TYPE.FOLDER;
    }

    getMenuName() {
        return this.menuName;
    }

    getProgramUrl() {
        return this.programUrl;
    }

}

function menuMapping(data = []) {
    return data.map(d => new Menu(d));
}


export {
    Menu, menuMapping
}

