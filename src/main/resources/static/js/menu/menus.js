import {createEl} from "/js/common/util.js";
import {loadContent} from "/js/common/common.js";
import {fetchMenusByUserRole} from "/js/apis/menu/menu.js";
import {FetchError} from "/js/error/fetchError.js";
import {errorAlert} from "/js/common/alert.js";
export default class Menus {
    /**
     * @type {MainMenu[]}
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
            this.menus = await fetchMenusByUserRole();
            this.#createTopMenu();
        } catch (e) {
            if(e instanceof FetchError) {
               errorAlert(e.serverMessage);
            } else {
                alert("메뉴를 가져오는데 실패했습니다.");
                throw e;
            }
        }
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
     * @param {MainMenu} mainMenu
     */
    addTopLi(parent, mainMenu) {
        let menuName = mainMenu.getMenuName();
        return this.addLi(parent, {href: '#', innerText: menuName});
    }

    /**
     * @param {HTMLUListElement | HTMLLIElement} parentElement
     * @param {MainMenu[]} subMenus
     */
    #createSubMenu(parentElement, subMenus) {
        const subMenuDiv = createEl('div');
        subMenuDiv.classList.add('subMenu');

        subMenus.forEach(subMenu => {
            const a = createEl('a', {innerText: subMenu.getMenuName()});
            a.addEventListener('click', () => {
                loadContent(subMenu);
            });
            subMenuDiv.appendChild(a);
        });

        parentElement.appendChild(subMenuDiv);
    }

    /**
     * @param {MainMenu} menu
     * @return {MainMenu|null}
     */
    #findFirstProgramSubMenu(menu) {
        if(!menu.hasChildren())
            return null;

        let firstProgramMenu;

        let programMenu = menu.getChildren().find(subMenu => subMenu.isProgramMenu());

        if(programMenu)
            return programMenu;

        menu.getChildren().forEach(subMenu => {
            if(!programMenu)
                programMenu = this.#findFirstProgramSubMenu(subMenu);
        })

        return programMenu;
    }

    #createTopMenu() {
        const ul = this.addUl(this.topMenuArea);
        this.menus.forEach(menu => {
            const li = this.addTopLi(ul, menu);
            if(menu.hasChildren()) {
                this.#createSubMenu(li, menu.getChildren());
            }
        });
    }

}
