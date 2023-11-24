import FieldError from "/js/validate/fieldError.js";

class FetchError {
    status;
    serverMessage = "";

    constructor(code, message) {
        this.status = code;
        this.serverMessage = message;
    }
}

class FieldFetchError extends FetchError {
    validation;
    constructor(target, {code, message, validation = {}}) {
        super(code, message);
        this.target = target;
        this.validation = validation;
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
