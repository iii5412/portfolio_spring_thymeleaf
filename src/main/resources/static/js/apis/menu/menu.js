import FolderMenuResponseDto from '/js/apis/menu/response/folder-menu.response.dto.js';
import MainMenuResponseDto from '/js/apis/menu/response/main-menu.response.dto.js';
import MenuManageSearchResponseDto from '/js/apis/menu/response/menu-manage-search.response.dto.js';
import { FETCH, toQueryString } from '/js/common/util.js';
import FolderMenu from '/js/menu/FolderMenu.js';
import MainMenu from '/js/menu/MainMenu.js';
import ManageMenu from '/js/menu/ManageMenu.js';

const tag = '[api/menu]';
const requestMapping = '/menu';

/**
 * 현재 사용자의 역할에 따라 메뉴를 가져옵니다.
 * @returns {Promise<MainMenu[]>}
 */
async function fetchMenusByUserRole() {
    const response = await FETCH.get(`${requestMapping}`);
    const mainMenuResponseDtos = response.map(r => {
        return new MainMenuResponseDto(r);
    });
    return createMenuFromMenuData(mainMenuResponseDtos);
}

/**
 * @param {MenuManageSearchRequestDto} menuManageSearchRequestDto
 * @returns {Promise<ManageMenu[]|ManageMenu>}
 */
async function fetchAllMenus(menuManageSearchRequestDto) {
    const param = menuManageSearchRequestDto.toObject();
    const response = await FETCH.get(`${requestMapping}/all?${toQueryString(param)}`);
    const menuManageSearchResponseDtos = response.map(r => new MenuManageSearchResponseDto(r));
    return manageMenuMapping(menuManageSearchResponseDtos);
}

/**
 * 폴더 메뉴를 가져옵니다.
 * @returns {Promise<FolderMenu[]>}
 */
async function fetchFolderMenus() {
    const response = await FETCH.get(`${requestMapping}/forderMenus`);
    const folderMenuResponseDtos = response.map(r => new FolderMenuResponseDto(r));
    return folderMenuMapping(folderMenuResponseDtos);
}

/**
 * 메뉴 ID에 해당하는 메뉴를 가져옵니다.
 * @param {FetchMenuByIdRequestDto} fetchMenuByIdRequestDto
 * @return {Promise<ManageMenu>}
 */
async function fetchMenuById(fetchMenuByIdRequestDto) {
    const { id } = fetchMenuByIdRequestDto.toObject();
    const response = await FETCH.get(`${requestMapping}/${id}`);
    return manageMenuMapping(response);
}

/**
 * @description 메뉴를 생성합니다.
 * @param {MenuManageCreateRequestDto} menuManageCreateRequestDto
 * @returns {Promise<*|undefined>}
 */
async function fetchCreateMenu(menuManageCreateRequestDto) {
    const { upperId, menuName, menuType, orderNum, roleCode } = menuManageCreateRequestDto.toObject();
    return await FETCH.post(`${requestMapping}`, { upperId, menuName, menuType, orderNum, roleCode });
}

/**
 * 메뉴 데이터를 사용하여 메뉴 개체를 생성합니다.
 * @param {MainMenuResponseDto[]} mainMenuResponseDtos
 * @returns {MainMenu[]}
 */
function createMenuFromMenuData(mainMenuResponseDtos = []) {
    const topMenus = [];
    mainMenuResponseDtos.forEach(mainMenuResponseDto => {
        const mainMenuObject = mainMenuResponseDto.toObject();
        const { id, upperMenuId, menuName, menuType, programUrl, orderNum } = mainMenuObject;
        const menu = new MainMenu({ id, upperMenuId, menuName, menuType, programUrl, orderNum });
        if (mainMenuObject.subMenus) {
            menu.setSubMenus(createMenuFromMenuData(mainMenuObject.subMenus));
        }
        topMenus.push(menu);
    });
    return topMenus;
}

/**
 * 데이터 개체 배열을 ManageMenu 개체 배열로 매핑합니다.
 *
 * @param {MenuManageSearchResponseDto[] | MenuManageSearchResponseDto} menuManageSearchResponseDtos - 매핑할 데이터 개체의 배열입니다.
 * @return {ManageMenu[] | ManageMenu}
 */
function manageMenuMapping(menuManageSearchResponseDtos = []) {
    if (Array.isArray(menuManageSearchResponseDtos)) {
        return menuManageSearchResponseDtos.map(menuManageSearchResponseDto => {
            const responseDtoObject = menuManageSearchResponseDto.toObject();
            const { subMenus, ...rest } = responseDtoObject;
            const manageMenu = new ManageMenu({ ...rest });
            if (subMenus && subMenus.length > 0) {
                manageMenu.setSubMenus(manageMenuMapping(subMenus));
            }
            return manageMenu;
        });
    } else {
        const responseDtoObject = menuManageSearchResponseDtos.toObject();
        const { subMenus, ...rest } = responseDtoObject;
        return new ManageMenu({ ...rest });
    }
}

/**
 * 데이터 개체 배열을 FolderMenu 개체 배열로 매핑합니다.
 * @param {FolderMenuResponseDto[] | FolderMenuResponseDto} folderMenuResponseDtos
 * @return {FolderMenu[] | FolderMenu}
 */
function folderMenuMapping(folderMenuResponseDtos = []) {
    if (Array.isArray(folderMenuResponseDtos)) {
        return folderMenuResponseDtos.map(folderMenuResponseDto => {
            const responseDtoObject = folderMenuResponseDto.toObject();
            const { subMenus, ...rest } = responseDtoObject;
            const folderMenu = new FolderMenu({ ...rest });
            if (subMenus && subMenus.length > 0) {
                folderMenu.setSubMenus(folderMenuMapping(subMenus));
            }
            return folderMenu;
        });
    } else {
        const responseDtoObject = folderMenuResponseDtos.toObject();
        const { subMenus, ...rest } = responseDtoObject;
        const folderMenu = new FolderMenu({ ...rest });
        if (subMenus && subMenus.length > 0) {
            folderMenu.setSubMenus(folderMenuMapping(subMenus));
        }
        return folderMenu;
    }
}

export { fetchMenusByUserRole, fetchAllMenus, fetchMenuById, fetchFolderMenus, fetchCreateMenu };
