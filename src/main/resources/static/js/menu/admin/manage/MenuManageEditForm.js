import { fetchAllProgram } from '/js/apis/program/program.js';
import Swal from 'https://cdn.jsdelivr.net/npm/sweetalert2@11.10.1/+esm';
import { createMenu, deleteMenu, editMenu, fetchFolderMenus, fetchMenuById } from '/js/apis/menu/menu.js';
import FetchMenuByIdRequestDto from '/js/apis/menu/request/menu-fetch-menu-by-id.request.dto.js';
import MenuManageCreateRequestDto from '/js/apis/menu/request/menu-manage-create.request.dto.js';
import MenuManageEditRequestDto from '/js/apis/menu/request/menu-manage.edit.request.dto.js';
import { errorAlert, infoAlert } from '/js/common/alert.js';
import { qs, qsAll, stringToHTMLElement } from '/js/common/util.js';
import { FetchError, FieldFetchError } from '/js/error/fetchError.js';
import { MENU_TYPE } from '/js/menu/menuConstants.js';
import MenuManageDeleteRequestDto from '/js/apis/menu/request/menu-manage-delete.request.dto.js';

export default class MenuManageEditForm {
    /** @type {HTMLElement} */
    container;
    /** @type {HTMLElement} */
    btnArea;
    /** @type {ManageMenu} */
    nowMenu;
    /** @type {() => void} */
    #newBtnClickCallback = () => {
        new Error('newBtnClickCallback is not defined');
    };
    /** @type {() => void} */
    #editBtnClickCallback = () => {
        new Error('editBtnClickCallback is not defined');
    };
    /** @type {() => void} */
    #delBtnClickCallback = () => {
        new Error('delBtnClickCallback is not defined');
    };
    /**
     * @description 상위 메뉴 선택 다이얼로그를 표시합니다.
     * @returns {Promise<void>}
     */
    #showUpperMenuDialog = () => {
        const swal = Swal;
        swal.fire({
            title: '상위메뉴',
            html: `
                <div class="table-responsive" id="dialog">
                    <table class="table table-striped table-hover table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>메뉴명</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            
                        </tbody>  
                    </table>
                </div>
            `,
            didOpen: async () => {
                const folderMenus = await fetchFolderMenus();
                const dialogContainer = qs(document, '#dialog');
                const table = qs(dialogContainer, 'table');
                /**
                 * @param {HTMLTableElement} table
                 * @param {FolderMenu} folderMenu
                 * @return {string}
                 */
                const addTr = (table, folderMenu) => {
                    const row = stringToHTMLElement(`
                        <tr>
                            <td>${folderMenu.getId()}</td>
                            <td>${folderMenu.getMenuName()}</td>
                            <td>
                                <button type="button" class="btn btn-success btn-sm border border-secondary">
                                    <i class="bi bi-check-circle"></i>
                                </button>
                            </td>
                        </tr>
                    `);
                    const button = qs(row, 'button');
                    button.addEventListener('click', () => {
                        qs(this.container, '#upperId').value = folderMenu.getId();
                        qs(this.container, '#upperMenuName').value = folderMenu.getMenuName();
                        swal.close();
                    });

                    table.append(row);
                };
                folderMenus.forEach(fm => {
                    addTr(table, fm);
                });
            },
        });
    };
    /**
     * @description 프로그램 목록 다이얼로그를 표시합니다.
     * @returns {Promise<void>}
     */
    #showProgramListDialog = () => {
        const swal = Swal;
        swal.fire({
            title: '프로그램목록',
            showCloseButton: true,
            showConfirmButton: false,
            html: `
                 <div class="table-responsive" id="dialog">
                    <table class="table table-striped table-hover table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>프로그램명</th>
                                <th>url</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                        
                        </tbody>
                    </table>
                </div>
            `,
            didOpen: async () => {
                const programs = await fetchAllProgram();
                const dialogContainer = qs(document, '#dialog');
                const table = qs(dialogContainer, 'table');
                /**
                 * @param {HTMLTableElement} table
                 * @param {Program} program
                 * @return {string}
                 */
                const addTr = (table, program) => {
                    const row = stringToHTMLElement(`
                    <tr>
                        <td>${program.getId()}</td>
                        <td>${program.getProgramName()}</td>
                        <td>${program.getUrl()}</td>
                        <td>
                            <button type="button" class="btn btn-success btn-sm border border-secondary">
                                <i class="bi bi-check-circle"></i>
                            </button>
                        </td>
                    </tr>
                    `);

                    const btn = qs(row, 'button');

                    btn.addEventListener('click', () => {
                        qs(this.container, '#programId').value = program.getId();
                        qs(this.container, '#programName').value = program.getProgramName();
                        swal.close();
                    });
                    table.append(row);
                };
                programs.forEach(fm => {
                    addTr(table, fm);
                });
            },
        });
    };
    /**
     * @description 메뉴 관리 Edit Form
     * @param {object} param
     * @param {HTMLElement} param.container - Form을 포함하는 컨테이너 엘리먼트
     * @param {HTMLElement} param.btnArea - 버튼들을 포함하는 컨테이너 엘리먼트
     */
    constructor({
        container,
        btnArea,
    }) {
        this.container = container;
        this.btnArea = btnArea;
        this.#setNewMode();
        this.#bindEvent();
    }

    /**
     * @description 메뉴를 편집 모드로 설정합니다.
     * @param {number} menuId - 편집할 메뉴의 ID
     * @returns {Promise<void>}
     */
    async setEditMode(menuId) {
        this.#clear();
        this.#createNewModeBtn();
        this.#createDelBtn();
        this.#createEditBtn();

        const menu = await fetchMenuById(new FetchMenuByIdRequestDto(menuId));

        this.nowMenu = menu;

        if (menu.isFolderMenu())
            this.#hideProgramArea();
        else
            this.#showProgramArea();

        qs(this.container, '#id').value = menu.getId();
        qs(this.container, '#menuName').value = menu.getMenuName();
        qs(this.container, '#upperId').value = menu.getUpperId();
        qs(this.container, '#upperMenuName').value = menu.getUpperMenuName();
        qs(this.container, '#menuType').value = menu.getMenuType();
        qs(this.container, '#roleCode').value = menu.getRoleCode();
        qs(this.container, '#programId').value = menu.getProgramId();
        qs(this.container, '#programName').value = menu.getProgramName();
        qs(this.container, '#orderNum').value = menu.getOrderNum();
        qs(this.container, '#lastModifiedByLoginId').value = menu.getLastModifiedByLoginId();
        qs(this.container, '#updatedAt').value = menu.getUpdatedAt();
    }

    /**
     * @description '신규' 버튼 클릭 시 호출될 콜백 함수를 설정합니다.
     * @param {() => void} callback - 콜백 함수
     */
    setNewBtnClickCallback(callback) {
        this.#newBtnClickCallback = callback;
    }

    /**
     * @description '저장' 버튼 클릭 시 호출될 콜백 함수를 설정합니다.
     * @param {() => void} callback - 콜백 함수
     */
    setEditBtnClickCallback(callback) {
        this.#editBtnClickCallback = callback;
    }

    /**
     * @description '삭제' 버튼 클릭 시 호출될 콜백 함수를 설정합니다.
     * @param {() => void} callback - 콜백 함수
     */
    setDelBtnClickCallback(callback) {
        this.#delBtnClickCallback = callback;
    }

    /**
     * @description 새로운 메뉴를 생성합니다.
     * @returns {Promise<void>}
     */
    async create() {
        try {
            const {
                upperId,
                menuName,
                menuType,
                orderNum,
                roleCode,
            } = this.#getFormData();
            const menuManageCreateRequestDto = new MenuManageCreateRequestDto({
                upperId: upperId,
                menuName: menuName,
                menuType: menuType,
                orderNum: orderNum,
                roleCode: roleCode,
            });
            await createMenu(menuManageCreateRequestDto);
            infoAlert('저장되었습니다.');
            this.#setNewMode();
        } catch (e) {
            if (e instanceof FetchError) {
                const fieldFetchError = new FieldFetchError(this.container, e);
                fieldFetchError.clearMessage(...qsAll(this.container, 'input[type="text"], select'));
                fieldFetchError.bindingMessage();
            } else {
                errorAlert('저장에 실패하였습니다. 잠시 후 다시 시도해주세요.');
                throw e;
            }
        }
    }

    /**
     * @description 메뉴를 수정합니다.
     * @returns {Promise<void>}
     */
    async edit() {
        try {
            const {
                upperId,
                menuName,
                menuType,
                programId,
                orderNum,
                roleCode,
            } = this.#getFormData();
            const menuManageEditRequestDto = new MenuManageEditRequestDto({
                id: this.nowMenu.getId(),
                upperId, menuName, menuType, orderNum, programId, roleCode,
            });
            await editMenu(menuManageEditRequestDto);
            infoAlert('저장되었습니다.');
            await this.setEditMode(menuManageEditRequestDto.id);
        } catch (e) {
            if (e instanceof FetchError) {
                if(e.isFieldError) {
                    const fieldFetchError = new FieldFetchError(this.container, e);
                    fieldFetchError.clearMessage(...qsAll(this.container, 'input[type="text"], select'));
                    fieldFetchError.bindingMessage();
                } else {
                    errorAlert(e.serverMessage);
                }
            } else {
                errorAlert('저장에 실패하였습니다. 잠시 후 다시 시도해주세요.');
                throw e;
            }
        }
    }

    /**
     * @description 메뉴를 삭제합니다.
     * @returns {Promise<void>}
     */
    async delete() {
        try {
            const id = this.nowMenu.getId();
            const menuManageDeleteRequestDto = new MenuManageDeleteRequestDto({ id });
            await deleteMenu(menuManageDeleteRequestDto);
            infoAlert('삭제되었습니다.');
            this.#setNewMode();
        } catch (e) {
            if (e instanceof FetchError) {
                errorAlert(e.serverMessage);
            } else {
                errorAlert('삭제에 실패하였습니다. 잠시 후 다시 시도해주세요.');
                throw e;
            }
        }
    }
    /**
     * @description 폼 요소에 이벤트를 바인딩합니다.
     * @returns {void}
     */
    #bindEvent() {
        const container = this.container;
        /** @type {HTMLSelectElement} */
        const htmlSelectElement = qs(container, '#menuType');
        htmlSelectElement.addEventListener('change', ({ target }) => {
            if (target.value === MENU_TYPE.FOLDER) {
                this.#hideProgramArea();
            } else {
                if (this.nowMenu && this.nowMenu.hasChildren()) {
                    errorAlert('하위 메뉴가 존자해여 변경 할 수 없습니다.');
                    target.value = this.nowMenu.getMenuType();
                }
                this.#showProgramArea();
            }
        });


        qs(container, '#findUpperMenuBtn').addEventListener('click', () => {
            this.#showUpperMenuDialog();
        });

        qs(container, '#findProgramBtn').addEventListener('click', () => {
            this.#showProgramListDialog();
        });
    }
    /**
     * @description 폼을 '신규' 모드로 설정합니다.
     * @returns {void}
     */
    #setNewMode() {
        this.#clear();
        this.#createNewBtn();
    }
    /**
     * @description 폼과 버튼을 초기화합니다.
     * @returns {void}
     */
    #clear() {
        this.nowMenu = null;
        this.#clearForm();
        this.#clearBtn();
    }
    /**
     * @description 폼 필드를 초기화합니다.
     * @returns {void}
     */
    #clearForm() {
        const container = this.container;
        qsAll(container, 'input[type="text"]').forEach(input => input.value = '');
        qsAll(container, 'select').forEach(select => select.value = '');
        const fieldFetchError = new FieldFetchError(container);
        fieldFetchError.clearMessage(...qsAll(container, 'input[type="text"], select'));
    }
    /**
     * @description 버튼 영역을 초기화합니다.
     * @returns {void}
     */
    #clearBtn() {
        qsAll(this.btnArea, '.btnWrapper').forEach(button => button.remove());
    }
    /**
     * @description 프로그램 영역을 숨깁니다.
     * @returns {void}
     */
    #hideProgramArea() {
        qs(this.container, '#programRow').classList.add('hidden');
    }
    /**
     * @description 프로그램 영역을 표시합니다.
     * @returns {void}
     */
    #showProgramArea() {
        qs(this.container, '#programRow').classList.remove('hidden');
    }

    /**
     * form 데이터를 반환한다.
     * @returns {{menuName: string, upperId: string, upperName: string, menuType: string, roleCode: string, programId: string, programName: string, orderNum: string}}
     */
    #getFormData() {
        /** @type {HTMLInputElement} */
        const menuName = qs(this.container, '#menuName');
        /** @type {HTMLInputElement} */
        const upperId = qs(this.container, '#upperId');
        /** @type {HTMLInputElement} */
        const upperName = qs(this.container, '#upperMenuName');
        /** @type {HTMLSelectElement} */
        const menuType = qs(this.container, '#menuType');
        /** @type {HTMLSelectElement} */
        const roleCode = qs(this.container, '#roleCode');
        /** @type {HTMLInputElement} */
        const programId = qs(this.container, '#programId');
        /** @type {HTMLInputElement} */
        const programName = qs(this.container, '#programName');
        /** @type {HTMLSelectElement} */
        const orderNum = qs(this.container, '#orderNum');

        return {
            menuName: menuName.value,
            upperId: upperId.value,
            upperName: upperName.value,
            menuType: menuType.value,
            roleCode: roleCode.value,
            programId: programId.value,
            programName: programName.value,
            orderNum: orderNum.value,
        };
    }

    #createNewBtn() {
        const btn = stringToHTMLElement('<div class="btnWrapper"><button type="button" class="btn btn-primary me-2" id="createBtn">추가</button></div>');
        btn.addEventListener('click', () => {
            this.#newBtnClickCallback();
        });
        this.btnArea.appendChild(btn);
    }

    #createEditBtn() {
        const btn = stringToHTMLElement('<div class="btnWrapper"><button type="button" class="btn btn-primary me-2" id="editBtn">저장</button></div>');
        btn.addEventListener('click', () => {
            this.#editBtnClickCallback();
        });
        this.btnArea.appendChild(btn);
    }

    #createDelBtn() {
        const btn = stringToHTMLElement('<div class="btnWrapper"><button type="button" class="btn btn-danger me-2" id="delBtn">삭제</button></div>');
        btn.addEventListener('click', () => {
            this.#delBtnClickCallback();
        });
        this.btnArea.appendChild(btn);
    }

    #createNewModeBtn() {
        const btn = stringToHTMLElement('<div class="btnWrapper addBtnWrapper"><button type="button" class="btn btn-success me-2" id="addBtn">신규</button></div>');
        btn.addEventListener('click', () => {
            this.#setNewMode();
        });
        this.btnArea.appendChild(btn);
    }
}


