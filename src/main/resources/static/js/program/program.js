class Program {

    constructor({id, programName, url, createdAt, updatedAt, lastUpdatedByUserLoginId}) {
        this.id = id;
        this.programName = programName;
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastUpdatedByUserLoginId = lastUpdatedByUserLoginId;
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
}

/**
 * @param {Object[]} data
 * @return {Program[]}
 */
function programMapping(data = []) {
    return data.map(d => new Program(d));
}

export {Program, programMapping}
