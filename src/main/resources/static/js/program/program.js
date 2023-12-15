class Program {

    constructor({id, programName, url, createdAt, updatedAt, lastUpdatedByUserLoginId, roleCode}) {
        Object.assign(this, {id, programName, url, createdAt, updatedAt, lastUpdatedByUserLoginId, roleCode});
    }

    getId() {
        return this.id;
    }

    getProgramName() {
        return this.programName;
    }

    getUrl() {
        return this.url;
    }

    getCreatedAt() {
        return this.createdAt;
    }

    getUpdatedAt() {
        return this.updatedAt;
    }

    getLastUpdatedByUserLoginId() {
        return this.lastUpdatedByUserLoginId;
    }

    getRoleCode() {
        return this.roleCode;
    }
}

/**
 * @param {Object[]} data
 * @return {Program[]}
 */
function programMapping(data = []) {
    return data.map(d => new Program(d));
}

export {Program, programMapping}
