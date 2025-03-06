import ResponseDto from '/js/apis/response/ResponseDto.js';

export default class RoleResponseDto extends ResponseDto {
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
    createAt;
    /**
     * @type {string}
     */
    updatedAt;

    /**
     * ROLE 응답 DTO
     * @param {object} param
     * @param {number} param.id
     * @param {number:null} [param.upperRoleId]
     * @param {ROLE_CODE} param.roleCode
     * @param {string} param.roleName
     * @param {string} param.createAt
     * @param {string} param.updatedAt
     */
    constructor({ id, upperRoleId, roleCode, roleName, createAt, updatedAt }) {
        super();
        this.id = id;
        this.upperRoleId = upperRoleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    /**
     * DTO를 오브젝트로 변환합니다.
     * @returns {{id: number, upperRoleId: (number|null), roleCode: ROLE_CODE, roleName: string, createAt: string, updatedAt: string}}
     */
    toObject() {
        return {
            ...super.toObject(),
            id: this.id,
            upperRoleId: this.upperRoleId,
            roleCode: this.roleCode,
            roleName: this.roleName,
            createAt: this.createAt,
            updatedAt: this.updatedAt,
        };
    }
}
