import {Program} from "/js/program/program.js";

const tag = '[menu]';

class Menu {

    static MENU_TYPE = {
        FOLDER : 'FOLDER',
        PROGRAM: 'PROGRAM'
    }

    hasSubMenus(){
        return this.subMenus.length > 0
    }

    getMenuId() {
        return this.id;
    }

    getUpperMenuId(){
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
    isProgramMenu(){
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

    constructor({id, menuName, menuType, orderNum, subMenus = [], upperMenuId = null
                    , programId = null, programUrl = null
                    , lastModifiedByLoginId, createdAt, updatedAt
                }) {
        if(!id)
            throw tag + "id가 없는 Menu가 있습니다.";


        this.id = id;
        this.menuName = menuName;
        this.menuType = menuType;
        this.orderNum = orderNum;
        this.subMenus = subMenus;
        this.upperMenuId = upperMenuId;
        this.programId = programId;
        this.programUrl = programUrl;
        this.lastModifiedByLoginId = lastModifiedByLoginId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

function menuMapping(data = []) {
    return data.map(d => new Menu(d));
}


export {
    Menu, menuMapping
}

