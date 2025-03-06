import { FETCH } from '/js/common/util.js';
import Role from '/js/role/role.js';
import RoleCodeNameResponseDto from '/js/apis/account/response/role-code-name.response.dto.js';
import RoleResponseDto from '/js/apis/account/response/role.response.dto.js';

const tag = '[api/role]';
const requestMapping = '/role';
/**
 * 서버에서 모든 역할을 검색합니다.
 *
 * @return {Promise<Role[]>} 역할 객체의 배열로 확인되는 Promise입니다.
 * @throws {Error} 역할 검색 중 오류가 발생한 경우.
 */
async function getAllRoles() {
    const response = await FETCH.get(`${requestMapping}`);
    const roleResponseDtos = response.map(r => new RoleResponseDto(r));
    return roleMapping(roleResponseDtos);
}

/**
 *
 * @returns {Promise<Role[]>}
 */
async function getAllRolesFlat() {
    const response = await FETCH.get(`${requestMapping}/flat`);
    const roleResponseDtos = response.map(r => new RoleResponseDto(r));
    return roleMapping(roleResponseDtos);
}

/**
 *
 * @returns {Promise<Role[]>}
 */
async function fetchRoleCodeName() {
    const response = await FETCH.get(`${requestMapping}/roleCodeNames`);
    const roleCodeNameResponseDtos = response.map(r => new RoleCodeNameResponseDto(r));
    return roleCodeNameResponseDtoToRole(roleCodeNameResponseDtos);
}

/**
 *
 * @param {RoleCodeNameResponseDto[]} roleCodeNameResponseDtos
 * @returns {Role[]}
 */
function roleCodeNameResponseDtoToRole(roleCodeNameResponseDtos) {
    const roles = [];
    roleCodeNameResponseDtos.forEach(r => {
        const role = new Role(r);
        roles.push(role);
    });
    return roles;
}

/**
 *
 * @param {RoleResponseDto[]} data
 * @returns {Role[]}
 */
function roleMapping(data = []) {
    const roles = [];
    data.forEach(r => {
        const role = new Role(r);
        roles.push(role);
    });
    return roles;
}

export { getAllRoles, getAllRolesFlat, fetchRoleCodeName };
