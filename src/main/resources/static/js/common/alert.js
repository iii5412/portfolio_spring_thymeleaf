const tag = "[Alert]";
class Alert {
    constructor(message) {
        this.message = message;
        this.showAlert();
    }

    showAlert() {
        throw new Error(tag + "showAlert을 구현하세요.");
    }
}

class InfoAlert extends Alert {
    constructor(message) {
        super(message);
    }

    showAlert() {
        alert(this.message);
    }
}

class WarnAlert extends Alert {
    constructor(message) {
        super(message);
    }

    showAlert() {
        alert(this.message);
    }
}

class ErrorAlert extends Alert {
    constructor(message) {
        super(message);
    }

    showAlert() {
        alert(this.message);
    }
}

const infoAlert = message => {
    new InfoAlert(message);
}

const warnAlert = message => {
    new WarnAlert(message);
}

const errorAlert = message => {
    new ErrorAlert(message);
}

export {
    infoAlert,
    warnAlert,
    errorAlert,
}
