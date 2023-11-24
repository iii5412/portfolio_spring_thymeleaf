import {createEl} from "/js/common/util.js";
import {loadContent} from "/js/common/common.js";
import {fetchMenu} from "/js/apis/menu/menu.js";

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
                    , lastModifiedByLoginId, createAt, updatedAt
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
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }
}


export default class Menus {
    /**
     * @type {Menu[]}
     */
    menus = [];
    topMenuArea;
    leftMenuArea;

    constructor(topMenuArea, leftMenuArea) {
        this.topMenuArea = topMenuArea;
        this.leftMenuArea = leftMenuArea;
    }

    getMenus() {
        // TODO: deep copy
        return this.menus;
    }

    async init() {
        try {
            const response = await fetchMenu();
            if(response.ok) {
                const menuData = await response.json();
                this.menus = this.createMenuFromMenuData(menuData);
                this.#createTopMenu();
            } else {
                throw new Error("메뉴를 가져오는데 실패했습니다.");
            }
        } catch (e) {
            throw e;
        }
    }

    /**
     * @param {Object[]} menuData
     * @return {Menu[]}
     */
    createMenuFromMenuData(menuData = []) {
        const result = [];
        menuData.forEach(data => {
            const menu = new Menu(data);
            result.push(menu);
            if(data.subMenus){
                const subMenus = this.createMenuFromMenuData(data.subMenus);
                menu.setSubMenus(subMenus);
            }
        })

        return result;
    }

    addUl(parent, options = {}) {
        const ul = createEl('ul', options);
        parent.appendChild(ul);
        return ul;
    }

    addLi(parent, options = {}) {
        const li = createEl('li', options);
        parent.appendChild(li);
        return li;
    }

    /**
     *
     * @param {HTMLUListElement} parent
     * @param {Menu} menu
     */
    addTopLi(parent, menu) {
        let menuName = menu.getMenuName();
        return this.addLi(parent, {href: '#', innerText: menuName});
    }

    /**
     * @param {HTMLUListElement | HTMLLIElement} parentElement
     * @param {Menu[]} subMenus
     */
    #createSubMenu(parentElement, subMenus) {
        const subMenuDiv = createEl('div');
        subMenuDiv.classList.add('subMenu');

        subMenus.forEach(subMenu => {
            const a = createEl('a', {innerText: subMenu.getMenuName()});
            a.addEventListener('click', () => {
                loadContent(subMenu.getProgramUrl());
            });
            subMenuDiv.appendChild(a);
        });

        parentElement.appendChild(subMenuDiv);
    }

    /**
     * @param {Menu} menu
     * @return {Menu|null}
     */
    #findFirstProgramSubMenu(menu) {
        if(!menu.hasSubMenus())
            return null;

        let firstProgramMenu;

        let programMenu = menu.getSubMenus().find(subMenu => subMenu.isProgramMenu());

        if(programMenu)
            return programMenu;

        menu.getSubMenus().forEach(subMenu => {
            if(!programMenu)
                programMenu = this.#findFirstProgramSubMenu(subMenu);
        })

        return programMenu;
    }

    #createTopMenu() {
        const ul = this.addUl(this.topMenuArea);
        this.menus.forEach(menu => {
            const li = this.addTopLi(ul, menu);
            if(menu.hasSubMenus()) {
                this.#createSubMenu(li, menu.getSubMenus());
            }
        });
    }

}
