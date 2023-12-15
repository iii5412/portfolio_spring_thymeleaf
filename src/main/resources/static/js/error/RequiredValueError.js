/**
 * 필수 값이 누락되었을 때 발생하는 오류를 나타냅니다.
 * @extends Error
 */
export default class RequiredValueError extends Error {
    constructor(paramName) {
        super(`${paramName} is required`);
        this.name = 'RequiredValueError';
    }
}

