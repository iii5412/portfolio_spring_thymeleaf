import ResponseDto from '/js/apis/response/ResponseDto.js';

export default class RoleCodeNameResponseDto extends ResponseDto {
    /**
     * @type {number}
     */
    id;
    /**
     * @type {ROLE_CODE}
     */
    roleCode;
    /**
     * @type {string}
     */
    roleName;

    /**
     * ROLE 코드 이름 응답 DTO
     * @param {object} param
     * @param {number} param.id
     * @param {ROLE_CODE} param.roleCode
     * @param {string} param.roleName
     */
    constructor({ id, roleCode, roleName }) {
        super();
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    /**
     * DTO를 오브젝트로 변환합니다.
     * @returns {{id: number, roleCode: ROLE_CODE, roleName: string}}
     */
    toObject() {
        return {
            ...super.toObject(),
            id: this.id,
            roleCode: this.roleCode,
            roleName: this.roleName,
        };
    }
}
