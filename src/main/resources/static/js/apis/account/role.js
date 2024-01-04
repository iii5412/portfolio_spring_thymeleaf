import {FETCH} from "/js/common/util.js";
import Role from "/js/role/role.js";

const tag = "[api/role]";
const requestMapping = '/role';
/**
 * 서버에서 모든 역할을 검색합니다.
 *
 * @return {Promise<Role[]>} 역할 객체의 배열로 확인되는 Promise입니다.
 * @throws {Error} 역할 검색 중 오류가 발생한 경우.
 */
async function getAllRoles() {
    try {
        const response = await FETCH.get(`${requestMapping}`);
        return roleMapping(response);
    } catch (e) {
        throw e;
    }
}

async function getAllRolesFlat() {
    try {
        const response = await FETCH.get(`${requestMapping}/flat`);
        return roleMapping(response);
    } catch (e) {
        throw e;
    }
}

/**
* 서버에서 역할 코드 이름을 가져옵니다.
 *
 * @returns {Promise<Role[]>} 역할 코드 이름의 배열로 확인되는 Promise입니다.
 * @throws {Error} 요청이 실패하거나 응답 처리 중 오류가 발생한 경우.
 */
async function fetchRoleCodeName() {
    try {
        const response = await FETCH.get(`${requestMapping}/roleCodeNames`);
        return roleMapping(response);
    } catch (e) {
        throw e;
    }
}

function roleMapping(data = []) {
    const roles = [];
    data.forEach(r => {
        const role = new Role(r);
        if(r.childRoles && r.childRoles.length > 0){
            role.childRoles = roleMapping(r.childRoles);
        }
        roles.push(role);
    })
    return roles;
}

export {getAllRoles, getAllRolesFlat, fetchRoleCodeName}
