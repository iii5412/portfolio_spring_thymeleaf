class Role {
    childRoles = [];

    constructor({id, upperRoleId, roleCode, roleName, createdAt, updatedAt}) {
        Object.assign(this, {id, upperRoleId, roleCode, roleName, createdAt, updatedAt});
    }

    getId() {
        return this.id;
    }

    getUpperId() {
        return this.upperRoleId;
    }

    getRoleCode() {
        return this.roleCode;
    }

    getRoleName() {
        return this.roleName;
    }

    getChildRoles() {
        return this.childRoles;
    }

    hasChildRoles() {
        return this.childRoles.length > 0;
    }

    hasUpper() {
        return !!this.upperRoleId;
    }

    isLastLevel() {
        return this.hasUpper() && !this.hasChildRoles();
    }

    setChildRoles(childRoles = []) {
        this.childRoles = childRoles;
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

export {Role, roleMapping}
