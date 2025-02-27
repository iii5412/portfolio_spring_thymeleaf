import Menu from '/js/menu/Menu.js';
import { MENU_TYPE } from '/js/menu/menuConstants.js';

/**
 * 폴더 메뉴 클래스입니다.
 */
export default class FolderMenu extends Menu {
    /**
     * @type {number}
     */
    id;
    /**
     * @type {number|null}
     */
    upperMenuId = null;
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
     * @type {FolderMenu[]}
     */
    subMenus = [];

    /**
     * 폴더 메뉴를 생성하는 생성자 함수입니다.
     * @param {Object} param
     * @param {number} param.id
     * @param {number|null} [param.upperId]
     * @param {string} param.menuName
     * @param {number} param.orderNum
     * @param {FolderMenu[]} [param.subMenus]
     */
    constructor({ id, upperId, menuName, orderNum, subMenus = [] }) {
        if (!subMenus)
            subMenus = [];

        super({
            id,
            upperMenuId: upperId,
            menuName,
            programUrl: null,
            menuType: MENU_TYPE.FOLDER,
            orderNum,
            subMenus,
        });
    }

    /**
     * 하위 메뉴를 반환합니다.
     * @returns {FolderMenu[]}
     */
    getChildren() {
        return this.subMenus;
    }

}
