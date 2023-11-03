import FieldError from "/js/validate/fieldError.js";

class FetchError {
    status;
    serverMessage = "";
    validation;

    constructor({code, message, validation = {}}) {
        this.status = code;
        this.serverMessage = message;
        this.validation = validation;
    }
}

class FieldFetchError extends FetchError {
    constructor(target, {code, message, validation}) {
        super({code, message, validation});
        this.target = target;
        this.fieldError = new FieldError(target);
    }

    bindingMessage() {
        Object.keys(this.validation).forEach(key => {
            this.#showMessage(key, this.validation[key]);
        })
    }

    #showMessage(elId, message) {
        this.fieldError.showMessage(elId, message);
    }

    clearMessage(...elements) {
        this.fieldError.clearMessage(...elements);
    }
}


export {FetchError, FieldFetchError}
