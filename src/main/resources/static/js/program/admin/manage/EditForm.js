import {createEl, qs, qsAll} from "/js/common/util.js";
import { FieldFetchError} from "/js/error/fetchError.js";

export default class EditForm {
    static FORM_STATE = {
        'CLEAR': 'clear',
        'EDIT': 'edit',
    }
    /**
     * @type {HTMLElement}
     */
    form;
    /**
     * @type {HTMLElement}
     */
    btnArea;
    /**
     * @type {EditForm.FORM_STATE}
     */
    status;
    #addBtnCallback = () => {
        console.error('addBtnCallback is not defined');
    }
    #editBtnCallback = () => {
        console.error('editBtnCallback is not defined');
    }
    #delBtnCallback = () => {
        console.error('delBtnCallback is not defined');
    }
    #newBtnCallback = () => {
        console.error('newBtnCallback is not defined');
    }
    /**
     * @description EditForm 생성자
     * @param {Object} param
     * @param {HTMLElement} param.form
     * @param {function} param.addBtnCallback
     * @param {function} param.editBtnCallback
     * @param {function} param.delBtnCallback
     * @param {function} param.newBtnCallback
     */
    constructor({
                    form
                    , addBtnCallback
                    , editBtnCallback
                    , delBtnCallback
                    , newBtnCallback
                }) {
        this.form = form;
        this.btnArea = qs(form, '.rightBtnArea');
        this.#addBtnCallback = addBtnCallback;
        this.#editBtnCallback = editBtnCallback;
        this.#delBtnCallback = delBtnCallback;
        this.#newBtnCallback = newBtnCallback;

        this.clear();
        this.setNewMode();
    }

    /**
     * @description form 영역을 반환합니다.
     * @return {HTMLElement}
     */
    getForm() {
        return this.form;
    }

    /**
     * @description 버튼 영역을 반환합니다.
     * @return {HTMLElement}
     */
    getBtnArea() {
        return this.btnArea;
    }

    clearFormData() {
        this.status = EditForm.FORM_STATE.CLEAR;
        qsAll(this.form, 'input[type="text"]').forEach(input => input.value = '');
    }

    clearBtnArea() {
        qsAll(this.form, 'input[type="button"]').forEach(button => button.remove());
    }

    clear() {
        this.status = EditForm.FORM_STATE.CLEAR;
        this.clearFormData();
        this.clearBtnArea();
        this.errorClear();
    }

    errorClear() {
        const fieldFetchError = new FieldFetchError(this.form);
        fieldFetchError.clearMessage(...qsAll(this.form, 'input[type="text"]'));
    }

    setNewMode() {
        this.clear();
        const addBtn = this.#createAddBtn();
        this.btnArea.appendChild(addBtn);
    }

    setEditMode(program) {
        const btnArea = this.btnArea;

        this.status = EditForm.FORM_STATE.EDIT;
        this.clear();
        const editBtn = this.#createEditBtn();
        const delBtn = this.#createDelBtn();
        const newModeBtn = this.#createNewModeBtn();

        btnArea.appendChild(editBtn);
        btnArea.appendChild(delBtn);
        btnArea.appendChild(newModeBtn);
        this.#setProgramValue(program);
    }

    /**
     * @description 프로그램명을 반환합니다.
     * @return {string}
     */
    getProgramName() {
        return qs(this.form, '#programName').value;
    }

    /**
     * @description url 값을 반환합니다.
     * @return {string}
     */
    getUrl() {
        return qs(this.form, '#url').value;
    }

    /**
     * @description id 값을 반환합니다.
     * @return {string}
     */
    getId() {
        return qs(this.form, '#id').value;
    }

    /**
     * @description 저장 버튼을 만듭니다.
     * @return {HTMLElement}
     */
    #createEditBtn() {
        const btn = createEl('input', {id: 'editBtn', type: 'button', value: '저장'});

        btn.addEventListener('click', this.#editBtnCallback);

        return btn;
    }

    /**
     * @description 삭제버튼을 만듭니다.
     * @return {HTMLElement}
     */
    #createDelBtn() {
        const btn = createEl('input', {id: 'editBtn', type: 'button', value: '삭제'});
        btn.addEventListener('click', this.#delBtnCallback);
        return btn;
    }

    /**
     * @description 신규 등록 버튼을 만듭니다.
     * @return {HTMLElement}
     */
    #createNewModeBtn() {
        const btn = createEl('input', {id: 'newBtn', type: 'button', value: '신규등록'});
        btn.addEventListener('click', this.#newBtnCallback);
        return btn;
    }

    /**
     * @description 추가 버튼을 만듭니다.
     * @return {HTMLElement}
     */
    #createAddBtn() {
        const btn = createEl('input', {id: 'addBtn', type: 'button', value: '추가'});
        btn.addEventListener('click', this.#addBtnCallback);
        return btn;
    }

    /**
     * @param {Program} program
     */
    #setProgramValue(program) {
        const form = this.form;
        const id = qs(form, '#id');
        const programName = qs(form, '#programName');
        const url = qs(form, '#url');
        const createdAt = qs(form, '#createdAt');
        const updatedAt = qs(form, '#updatedAt');
        const lastUpdatedByUserLoginId = qs(form, '#lastUpdatedByUserLoginId');

        id.value = program.getId();
        programName.value = program.getProgramName();
        url.value = program.getUrl();
        createdAt.value = program.getCreatedAt();
        updatedAt.value = program.getUpdatedAt();
        lastUpdatedByUserLoginId.value = program.getLastUpdatedByUserLoginId();
    }
}
