import {MENU_TYPE} from "/js/menu/menuConstants.js";

const tag = `[Menu]`;
/**
 * @class
 * @classdesc Menu 클래스는 웹사이트 내의 메뉴를 표현하는 클래스입니다.
 */
export default class Menu {
    /**
     * 메뉴를 생성하는 생성자 함수입니다.
     * @param {Object} param0 - 생성자에 전달될 객체를 분해합니다.
     * @param {number} param0.id - 고유한 메뉴 ID입니다.
     * @param {number|null} param0.upperMenuId - 상위 메뉴의 ID입니다. 상위 메뉴가 없을 경우 null입니다.
     * @param {string} param0.menuName - 메뉴의 이름입니다.
     * @param {string} param0.programUrl - 메뉴의 프로그램 URL입니다.
     * @param {string} param0.menuType - 메뉴의 타입입니다. 'FOLDER' 또는 'PROGRAM'일 수 있습니다.
     * @param {number} param0.orderNum - 메뉴의 정렬 순서를 나타내는 번호입니다.
     * @param {Menu[]} param0.subMenus - 하위 메뉴들의 배열입니다.
     */
    constructor({id, upperMenuId, menuName, programUrl, menuType, orderNum, subMenus = []}) {

        Object.assign(this, {id, upperMenuId, menuName, programUrl, menuType, orderNum, subMenus});
    }

    /**
     * 메뉴의 ID를 반환합니다.
     * @return {number} id
     */
    getId() {
        return this.id
    }

    /**
     * 상위 메뉴의 ID를 반환합니다.
     * @return {number|null} upperMenuId
     */
    getUpperId() {
        return this.upperMenuId;
    }

    /**
     * 상위 메뉴의 존재 여부를 반환합니다.
     * @return {boolean} 상위 메뉴 존재 여부
     */
    hasUpper() {
        return !!this.getUpperId();
    }

    /**
     * 하위 메뉴의 존재 여부를 반환합니다.
     * @return {boolean} 하위 메뉴 존재 여부
     */
    hasChildren() {
        return this.subMenus.length > 0;
    }

    /**
     * 하위 메뉴 객체들을 반환합니다.
     * @return {Menu[]} 하위 메뉴 객체의 배열
     */
    getChildren() {
        return this.subMenus.map(sm => new Menu(sm));
    }

    /**
     *
     * @param {Menu[]} subMenus
     */
    setSubMenus(subMenus) {
        if(subMenus.length > 0)
            this.subMenus = subMenus;
        else
            console.warn("SubMenus length is " + this.subMenus.length);
    }

    isProgramMenu() {
        return this.menuType === MENU_TYPE.PROGRAM;
    }

    isFolderMenu() {
        return this.menuType === MENU_TYPE.FOLDER
    }

    getMenuName() {
        return this.menuName;
    }

    getProgramUrl() {
        return this.programUrl;
    }


}




