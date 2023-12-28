export default class NotImplementedError extends Error {
    constructor(methodName = "", message = "메소드가 구현되지 않았습니다.") {
        super(`${methodName} _ ${message}`);
        this.name = "NotImplementedError";
    }
}
