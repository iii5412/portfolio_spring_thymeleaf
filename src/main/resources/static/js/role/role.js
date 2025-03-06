export default class Role {
    /**
     * @type {number}
     */
    id;
    /**
     * @type {number|null}
     */
    upperRoleId;
    /**
     * @type {ROLE_CODE}
     */
    roleCode;
    /**
     * @type {string}
     */
    roleName;
    /**
     * @type {string}
     */
    createdAt;
    /**
     * @type {string}
     */
    updatedAt;
    /**
     * @type {Role[]}
     */
    childRoles = [];
    /**
     * Role 객체
     * @param {object} param
     * @param {number} param.id
     * @param {number|null} [param.upperRoleId]
     * @param {ROLE_CODE} param.roleCode
     * @param {string} param.roleName
     * @param {string} [param.createdAt]
     * @param {string} [param.updatedAt]
     */
    constructor({ id, upperRoleId, roleCode, roleName, createdAt, updatedAt }) {
        Object.assign(this, { id, upperRoleId, roleCode, roleName, createdAt, updatedAt });
    }

    /**
     * ID를 반환합니다.
     * @returns {number}
     */
    getId() {
        return this.id;
    }

    /**
     * 상위 역할 ID를 반환합니다.
     * @returns {number|null}
     */
    getUpperId() {
        return this.upperRoleId;
    }

    /**
     * 역할 코드를 반환합니다.
     * @returns {ROLE_CODE}
     */
    getRoleCode() {
        return this.roleCode;
    }

    /**
     * 역할 이름을 반환합니다.
     * @returns {string}
     */
    getRoleName() {
        return this.roleName;
    }

    /**
     * 자식 역할을 반환합니다.
     * @returns {Role[]}
     */
    getChildRoles() {
        return this.childRoles;
    }

    /**
     * 자식 존재 여부를 반환합니다.
     * @returns {boolean}
     */
    hasChildRoles() {
        return this.childRoles.length > 0;
    }

    /**
     * 부모 존재 여부를 반환합니다.
     * @returns {boolean}
     */
    hasUpper() {
        return !!this.upperRoleId;
    }

    /**
     * 최하위 레벨인지 확인합니다.
     * @returns {*|boolean}
     */
    isLastLevel() {
        return this.hasUpper() && !this.hasChildRoles();
    }

    /**
     * 하위 역할을 설정합니다.
     * @param {Role[]} childRoles
     */
    setChildRoles(childRoles = []) {
        this.childRoles = childRoles;
    }
}

