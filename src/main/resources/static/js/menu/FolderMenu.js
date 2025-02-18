import Menu from "/js/menu/Menu.js";
import {MENU_TYPE} from "/js/menu/menuConstants.js"

export default class FolderMenu extends Menu {
    constructor({id, upperId, menuName, orderNum, subMenus = []}) {
        if(!subMenus)
            subMenus = [];

        super({
            id,
            upperMenuId: upperId,
            menuName,
            programUrl: null,
            menuType: MENU_TYPE.FOLDER,
            orderNum,
            subMenus
        });
    }

    /**
     * @return {FolderMenu[]}
     */
    getChildren() {
        return this.subMenus.map(sm => new FolderMenu(sm));
    }

}
