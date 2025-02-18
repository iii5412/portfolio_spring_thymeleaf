const tag = `[UserResponse]`;

export default class UserResponse {
    #id;
    #loginId;
    #userName;
    #roleCodes;

    constructor({id, loginId, userName, roleCodes}) {
        this.#id = id;
        this.#loginId = loginId;
        this.#userName = userName;
        this.#roleCodes = roleCodes;

        console.log(`${tag} :: `)
        console.dir(this);
    }

    getId() {
        return this.#id;
    }

    getLoginId() {
        return this.#loginId;
    }

    getUserName() {
        return this.#userName;
    }

    getRoleCodes() {
        return this.#roleCodes;
    }
}
