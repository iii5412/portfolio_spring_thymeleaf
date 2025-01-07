import FieldError from "/js/validate/fieldError.js";
import {HTTP_STATUS} from "/js/common/util.js";
import {errorAlert} from "/js/common/alert.js";
import {goLoginPage} from "/js/common/route.js";

/**
 * 가져오기 작업 중에 발생하는 오류를 나타냅니다.
 * @extends Error
 */
class FetchError extends Error {
    status;
    serverMessage = "";
    validation = {};
    constructor({code, message, validation = {}}) {
        super(message);
        this.status = Number(code);
        this.serverMessage = message;
        this.validation = validation;

        switch(this.status) {
            case HTTP_STATUS.UNAUTHORIZED :
                errorAlert("인증을 확인해주세요.");
                goLoginPage();
                break;
            case HTTP_STATUS.FORBIDDEN :
                errorAlert("권한이 없습니다.");
                break;
        }

    }
}

/**
 * 필드에 대한 데이터를 가져올 때 발생하는 오류를 나타냅니다.
 */
class FieldFetchError {
    status;
    serverMessage;
    validation;

    /**
     * @param {Element} target
     * @param {FetchError} fetchError
     */
    constructor(target, fetchError) {
        this.target = target;
        this.fieldError = new FieldError(target);
        if(fetchError) {
            this.status = fetchError.status;
            this.serverMessage = fetchError.serverMessage;
            this.validation = fetchError.validation;
        }
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
