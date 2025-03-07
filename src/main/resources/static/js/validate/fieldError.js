import {createEl, qs, qsAll} from "/js/common/util.js";
const fieldErrorMessageClass = "invalid-feedback";

export default class FieldError {
    constructor(target) {
        this.target = target;
    }

    #createMessageArea(elId) {
        const div = createEl('div');
        div.classList.add(`${fieldErrorMessageClass}`);
        div.dataset.target = elId;
        return div;
    }

    getMessageArea(elId) {
        let findArea = qsAll(this.target, `.${fieldErrorMessageClass}`).find(el => el.dataset.target === elId);
        if(!findArea) {
            const el = qs(this.target, '#' + elId);
            findArea = this.#createMessageArea(elId);
            el.parentElement.appendChild(findArea);
        }
        return findArea;
    }

    showMessage(elId, message) {
        const messageArea = this.getMessageArea(elId);
        /** @type {HTMLInputElement} */
        const targetEl = qs(this.target, `#${elId}`);
        if (message) {
            messageArea.innerHTML = message;
            messageArea.classList.add('show');
            targetEl.classList.add('is-invalid');
        }
    }

    clearMessage(...elements) {

        elements.forEach(el => {
            /** @type {HTMLInputElement} */
            const targetEl = qs(this.target, `#${el.id}`);
            const messageArea = this.getMessageArea(el.id);
            messageArea.innerHTML = '';
            messageArea.classList.remove('show');
            targetEl.classList.remove('is-invalid');
        });
    }
}

