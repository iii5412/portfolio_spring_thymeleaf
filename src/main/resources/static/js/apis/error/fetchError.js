export default class FetchError extends Error {

    status;
    serverMessage = "";

    constructor(status, serverMessaage) {
        super();
        this.status = status;
        this.serverMessage = serverMessaage;
    }
}
