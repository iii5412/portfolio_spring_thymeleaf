import {FETCH} from "/js/common/util.js";
import {Menu} from "/js/menu/menu.js";

const tag = "[api/menu]";
const requestMapping = "/menu";
/**
 * 서버에서 모든 메뉴 데이터를 가져오고 Menu 객체의 배열로 해결되는 약속을 반환합니다.
 * @return {Promise<Menu[]>} Menu 객체의 배열로 해결되는 Promise입니다.
 * @throws {Error} 메뉴 데이터를 가져오는 중 오류가 발생한 경우.
 */
async function fetchAllMenu() {
    try {
        const response = await FETCH.get(`${requestMapping}`);
        return createMenuFromMenuData(response)
    } catch (e) {
        throw e;
    }
}

/**
 * 모든 메뉴 항목을 평탄화 구조로 가져옵니다.
 * @returns {Promise<Menu[]>} 메뉴 항목 배열로 해결되는 Promise입니다.
 */
async function fetchAllMenuFlat() {
    try {
        const response = await FETCH.get(`${requestMapping}/flat`);
        return menuMapping(response);
    } catch (e) {
        throw e;
    }
}

/**
 * 주어진 메뉴 데이터로부터 메뉴 객체의 배열을 생성합니다.
 *
 * @param {Object[]} menuData - 메뉴 객체를 생성하는 데 사용되는 메뉴 데이터입니다.
 * @return {Menu[]} - 메뉴 객체의 배열입니다.
 */
function createMenuFromMenuData(menuData = []) {
    const result = [];
    menuData.forEach(data => {
        const menu = new Menu(data);
        result.push(menu);
        if(data.subMenus){
            const subMenus = createMenuFromMenuData(data.subMenus);
            menu.setSubMenus(subMenus);
        }
    })
    return result;
}

/**
 * 데이터 개체 배열을 메뉴 개체 배열로 매핑합니다.
 *
 * @param {Object[]} data - 매핑할 데이터 개체의 배열입니다.
 * @return {Menu[]} - 데이터에서 매핑되는 메뉴 개체의 배열입니다.
 */
function menuMapping(data = []) {
    return data.map(d => new Menu(d));
}

export {fetchAllMenu, fetchAllMenuFlat};
