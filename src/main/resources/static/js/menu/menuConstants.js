/**
 * 메뉴 타입(FOLDER, PROGRAM)
 * @type {{FOLDER: string, PROGRAM: string}}
 */
const MENU_TYPE = {
    FOLDER: 'FOLDER',
    PROGRAM: 'PROGRAM',
};
/**
 * @description 메뉴 타입을 한글로 변환합니다.
 * @param menuType
 * @returns {string}
 */
const getMenuTypeName = (menuType) => {
    switch(menuType) {
    case MENU_TYPE.FOLDER:
        return '폴더';
    case MENU_TYPE.PROGRAM:
        return '프로그램';
    default :
        throw new Error('잘못된 메뉴 타입입니다.');

    }
}

export { MENU_TYPE, getMenuTypeName};
