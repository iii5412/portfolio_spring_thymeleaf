import Dto from "/js/apis/Dto";

export default class ProgramManageCreateRequestDto extends Dto {
    /**
     * @type {string}
     */
    programName;
    /**
     * @type {string}
     */
    url;


    constructor(programName, url) {
        super();
        this.programName = programName;
        this.url = url;
    }

    /**
     *
     * @returns {{programName: string, url: string}}
     */
    toObject() {
        return {
            ...super.toObject(),
            programName: this.programName,
            url: this.url,
        }
    }
}
