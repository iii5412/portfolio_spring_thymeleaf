import {createEl, qs, qsAll} from "/js/common/util.js";

export default class FieldError {
    constructor(target) {
        this.target = target;
    }

    #createMessageArea(elId) {
        const div = createEl('div');
        div.classList.add('field-error-message');
        div.dataset.target = elId;
        return div;
    }

    getMessageArea(elId) {
        let findArea = qsAll(this.target, '.field-error-message').find(el => el.dataset.target === elId);
        if(!findArea) {
            const el = qs(this.target, '#' + elId);
            findArea = this.#createMessageArea(elId);
            el.parentElement.appendChild(findArea);
        }
        return findArea;
    }

    showMessage(elId, message) {
        const messageArea = this.getMessageArea(elId);
        if (message) {
            messageArea.innerHTML = message;
            messageArea.classList.add('show');
        }
    }

    clearMessage(...elements) {
        elements.forEach(el => {
            const messageArea = this.getMessageArea(el.id);
            messageArea.innerHTML = '';
            messageArea.classList.remove('show');
        });
    }
}

