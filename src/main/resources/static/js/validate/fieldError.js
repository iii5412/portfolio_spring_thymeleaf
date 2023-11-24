import {qs} from "/js/common/util.js";

export default class FieldError {
    constructor(target) {
        this.target = target;
    }

    getMessageArea(elId) {
        return qs(this.target, '#' + elId + 'Error');
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

