import ResponseDto from '/js/apis/response/ResponseDto.js';

export default class FolderMenuResponseDto extends ResponseDto {
    /**
     * @type {number}
     */
    id;
    /**
     * @type {string}
     */
    menuName;
    /**
     * @type {number}
     */
    upperId;
    /**
     * @type {number}
     */
    orderNum = 0;
    /**
     *
     * @type {FolderMenuResponseDto[]}
     */
    subMenus = [];

    /**
     * 폴더 메뉴 응답 DTO
     * @param {object} param
     * @param {number} param.id
     * @param {string} param.menuName
     * @param {number|null} [param.upperId]
     * @param {number} param.orderNum
     * @param {FolderMenuResponseDto[]} [param.subMenus]
     */
    constructor({ id, menuName, upperId, orderNum, subMenus }) {
        super();
        this.id = id;
        this.menuName = menuName;
        this.upperId = upperId;
        this.orderNum = orderNum;
        this.subMenus = subMenus;
    }

    /**
     * DTO를 오브젝트로 변환합니다.
     * @returns {{id: number, menuName: string, upperId: number, orderNum: number, subMenus: FolderMenuResponseDto[]}}
     */
    toObject() {
        return {
            ...super.toObject(),
            id: this.id,
            menuName: this.menuName,
            upperId: this.upperId,
            orderNum: this.orderNum,
            subMenus: this.subMenus,
        };
    }
}
