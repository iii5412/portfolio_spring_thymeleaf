import Menu from '/js/menu/Menu.js';

const tag = '[MainMenu]';
export default class MainMenu extends Menu {
    /**
     *
     * @param {number} id
     * @param {number} [upperMenuId]
     * @param {string} menuName
     * @param {string} programUrl
     * @param {MENU_TYPE} menuType
     * @param {number} orderNum
     * @param {Menu[]} [subMenus]
     */
    constructor({ id, upperMenuId, menuName, programUrl, menuType, orderNum, subMenus = [] }) {
        super({ id, upperMenuId, menuName, programUrl, menuType, orderNum, subMenus });
    }

    /**
     * MainMenu 개체의 배열로 하위 메뉴를 반환합니다.
     *
     * @return {MainMenu[]} 하위 메뉴를 나타내는 MainMenu 개체의 배열.
     */
    getChildren() {
        return this.subMenus.map(sm => new MainMenu(sm));
    }

}
