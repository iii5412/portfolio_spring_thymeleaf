import {FETCH} from "/js/common/util.js";
import MainMenu from "/js/menu/MainMenu.js";
import ManageMenu from "/js/menu/ManageMenu.js";
import FolderMenu from "/js/menu/FolderMenu.js";

const tag = "[api/menu]";
const requestMapping = "/menu";

/**
 * 서버에서 사용자 권한에 맞는 모든 메뉴 데이터를 가져오고 Menu 객체의 배열로 해결되는 약속을 반환합니다.
 * @return {Promise<MainMenu[]>} Menu 객체의 배열로 해결되는 Promise입니다.
 * @throws {Error} 메뉴 데이터를 가져오는 중 오류가 발생한 경우.
 */
async function fetchMenusByUserRole() {
    try {
        const response = await FETCH.get(`${requestMapping}`);
        return createMenuFromMenuData(response)
    } catch (e) {
        throw e;
    }
}

/**
 * 모든 메뉴를 가져옵니다.
 * @returns {Promise<ManageMenu[]>} 메뉴 항목 배열로 해결되는 Promise입니다.
 */
async function fetchAllMenus() {
    try {
        const response = await FETCH.get(`${requestMapping}/all`);
        return manageMenuMapping(response);
    } catch (e) {
        throw e;
    }
}

/**
 * 폴더 타입의 메뉴를 가져옵니다.
 * @return {Promise<FolderMenu[]>}
 */
async function fetchFolderMenus() {
    try {
        const response = await FETCH.get(`${requestMapping}/forderMenus`);
        return folderMenuMapping(response);
    } catch (e) {
        throw e;
    }
}

/**
 *
 * @param id
 * @return {Promise<ManageMenu>}
 */
async function fetchMenuById(id) {
    try {
        const response = await FETCH.get(`${requestMapping}/${id}`);
        return manageMenuMapping(response);
    } catch (e) {
        throw e;
    }
}

/**
 *
 * @param {number | null} upperId
 * @param {string} menuName
 * @param {MENU_TYPE} menuType
 * @param {number} orderNum
 * @param {ROLE_CODE} roleCode
 * @return {Promise<*|undefined>}
 */
async function fetchCreateMenu({upperId, menuName, menuType, orderNum, roleCode}) {
    try {
        return await FETCH.post(`${requestMapping}`, {upperId, menuName, menuType, orderNum, roleCode});
    } catch (e) {
        throw e;
    }
}

/**
 * 주어진 메뉴 데이터로부터 메뉴 객체의 배열을 생성합니다.
 *
 * @param {Object[]} menuData - 메뉴 객체를 생성하는 데 사용되는 메뉴 데이터입니다.
 * @return {MainMenu[]} - 메뉴 객체의 배열입니다.
 */
function createMenuFromMenuData(menuData = []) {
    const result = [];
    menuData.forEach(data => {
        const menu = new MainMenu(data);
        result.push(menu);
        if (data.subMenus) {
            const subMenus = createMenuFromMenuData(data.subMenus);
            menu.setSubMenus(subMenus);
        }
    })
    return result;
}

/**
 * 데이터 개체 배열을 MainMenu 개체 배열로 매핑합니다.
 *
 * @param {Object[] | Object} data - 매핑할 데이터 개체의 배열입니다.
 * @return {MainMenu[] | MainMenu} - 데이터에서 매핑되는 메뉴 개체의 배열입니다.
 */
function mainMenuMapping(data = []) {
    if (data instanceof Array)
        return data.map(d => new MainMenu(d));
    else
        return new MainMenu(data);
}

/**
 * 데이터 개체 배열을 ManageMenu 개체 배열로 매핑합니다.
 *
 * @param {Object[] | Object} data - 매핑할 데이터 개체의 배열입니다.
 * @return {ManageMenu[] | ManageMenu} - 데이터에서 매핑되는 메뉴 개체의 배열입니다.
 */
function manageMenuMapping(data = []) {
    if (data instanceof Array)
        return data.map(d => new ManageMenu(d));
    else
        return new ManageMenu(data);
}

/**
 * 데이터 개체 배열을 FolderMenu 개체 배열로 매핑합니다.
 * @param data
 * @return {FolderMenu[]}
 */
function folderMenuMapping(data = []) {
    return data.map(d => new FolderMenu(d));
}

export {fetchMenusByUserRole, fetchAllMenus, fetchMenuById, fetchFolderMenus, fetchCreateMenu};
