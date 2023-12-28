import Swal from 'https://cdn.jsdelivr.net/npm/sweetalert2@11.10.1/+esm';
import NotImplementedError from "/js/error/NotImplementedError.js";

const tag = "[Alert]";
class Alert {
    constructor(message) {
        this.message = message;
        // this.showAlert();
    }

    showAlert() {
        throw new NotImplementedError('showAlert');
    }
}

class InfoAlert extends Alert {
    constructor(message) {
        super(message);
    }

    showAlert() {
        Swal.fire({
            text: this.message,
            icon: 'info',
        });
    }
}

class WarnAlert extends Alert {
    constructor(message) {
        super(message);
    }

    showAlert() {
        Swal.fire({
            text: this.message,
            icon: 'warning',
        });
    }
}

class ErrorAlert extends Alert {
    constructor(message) {
        super(message);
    }

    showAlert() {
        Swal.fire({
            text: this.message,
            icon: 'error',
        });
    }
}

class SuccAlert extends Alert {

    constructor(message) {
        super(message);
    }

    showAlert() {
        return Swal.fire({
            text: this.message,
            icon: 'success',
        });
    }
}

class CustomAlert extends Alert {
    options = {};
    constructor(options) {
        super('');
        this.options = options;
    }

    showAlert() {
        return Swal.fire(this.options);
    }
}

const infoAlert = message => {
    const alert = new InfoAlert(message);
    return alert.showAlert();
}

const warnAlert = message => {
    new WarnAlert(message);
}

const errorAlert = message => {
    new ErrorAlert(message);
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
