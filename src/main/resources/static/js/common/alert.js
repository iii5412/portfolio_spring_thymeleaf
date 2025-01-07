import Swal from 'https://cdn.jsdelivr.net/npm/sweetalert2@11.10.1/+esm';
import NotImplementedError from "/js/error/NotImplementedError.js";

const tag = "[Alert]";

/**
 * @class Alert
 */
class Alert {
    /**
     *
     * @param message
     */
    constructor(message) {
        this.message = message;
        // this.showAlert();
    }

    showAlert() {
        throw new NotImplementedError('showAlert');
    }
}

/**
 * @class InfoAlert
 */
class InfoAlert extends Alert {
    /**
     *
     * @param message
     */
    constructor(message) {
        super(message);
    }

    /**
     *
     * @return {Swal}
     */
    showAlert() {
        return Swal.fire({
            text: this.message,
            icon: 'info',
        });
    }
}

/**
 * @class WarnAlert
 */
class WarnAlert extends Alert {
    /**
     *
     * @param message
     */
    constructor(message) {
        super(message);
    }
    /**
     *
     * @return {Swal}
     */
    showAlert() {
        return Swal.fire({
            text: this.message,
            icon: 'warning',
        });
    }
}

/**
 * @class ErrorAlert
 */
class ErrorAlert extends Alert {
    /**
     *
     * @param message
     */
    constructor(message) {
        super(message);
    }
    /**
     *
     * @return {Swal}
     */
    showAlert() {
        return Swal.fire({
            text: this.message,
            icon: 'error',
        });
    }
}

/**
 * @class SuccAlert
 */
class SuccAlert extends Alert {
    /**
     *
     * @param message
     */
    constructor(message) {
        super(message);
    }
    /**
     *
     * @return {Swal}
     */
    showAlert() {
        return Swal.fire({
            text: this.message,
            icon: 'success',
        });
    }
}

/**
 * @class CustomAlert
 */
class CustomAlert extends Alert {
    options = {};

    /**
     *
     * @param options
     */
    constructor(options) {
        super('');
        this.options = options;
    }
    /**
     *
     * @return {Swal}
     */
    showAlert() {
        return Swal.fire(this.options);
    }
}

const infoAlert = message => {
    const alert = new InfoAlert(message);
    return alert.showAlert();
}

const warnAlert = message => {
    const alert = new WarnAlert(message);
    return alert.showAlert();
}

const errorAlert = message => {
    const alert = new ErrorAlert(message);
    return alert.showAlert();

}

const succAlert = message => {
    const alert = new SuccAlert(message);
    return alert.showAlert();
}

const customAlert = (options = {}) => {
    const alert = new CustomAlert(options);
    return alert.showAlert();
}

export {
    infoAlert,
    warnAlert,
    errorAlert,
    succAlert,
    customAlert,
}
