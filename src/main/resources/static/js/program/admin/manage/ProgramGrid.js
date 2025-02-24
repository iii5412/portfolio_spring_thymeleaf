import {isEmptyObject, qs} from "/js/common/util.js";
import {fetchManageProgram} from '/js/apis/program/index.js';
import ProgramSearchRequestDto from "/js/apis/program/request/program-manage-search.request.dto.js";
import Program from "/js/program/Program.js";

const tag = '[ProgramGrid]';

// export default class ProgramGrid {
//     container;
//     gridId;
//     searchForm;
//     pagination;
//
//     searchParam = {};
//     size = 20;
//     gridView;
//     gridProvider;
//
//     /**
//      *
//      * @param {HTMLElement} container
//      * @param {string} gridId
//      * @param {HTMLElement} searchForm - 검색 양식 요소입니다.
//      * @param {Pagination} pagination - Pagination 개체입니다.
//      */
//     constructor(container, gridId, searchForm, pagination) {
//         this.container = container;
//         this.gridId = gridId;
//         this.searchForm = searchForm;
//         this.pagination = pagination;
//     }
//
//     async init() {
//         const {programList, programCount} = await this.#getPrograms({id: '', programName: '', url: ''}, 1, this.size);
//         this.#drawGrid(programList, programCount);
//         this.#configPagination();
//     }
//
//     async refreshGrid() {
//         await this.search();
//     }
//
//     /**
//      * @param {Program[]} programList
//      * @param {Number} programCount
//      * @return {Promise<void>}
//      */
//     #drawGrid(programList, programCount) {
//         const gridContainer = qs(this.container, `#${this.gridId}`);
//         const gridProvider = new RealGrid.LocalDataProvider(false);
//         const gridView = new RealGrid.GridView(gridContainer);
//
//         gridView.setDataSource(gridProvider);
//
//         //provider에서는 삭제하지말고
//         gridProvider.softDeleting = true;
//         //저장되지않는 추가건은 삭제시 provider에도 삭제한다.
//         gridProvider.deleteCreated = true;
//         //화면에서만 숨기도록
//         gridView.hideDeletedRows = true;
//
//         gridProvider.setFields([
//             createField('id').number().end(),
//             createField('programName').text().end(),
//             createField('url').text().end(),
//             createField('createdAt').text().end(),
//             createField('updatedAt').text().end(),
//             createField('lastUpdatedByUserLoginId').text().end()
//         ]);
//
//         gridProvider.setRows(programList);
//         gridView.setColumns([
//             createColumn('id', COLUMN_TYPE.DATA, 70).visible(false).editable(false).header('id').end(),
//             createColumn('programName', COLUMN_TYPE.DATA, 120).editable(false).header('프로그램명').end(),
//             createColumn('url', COLUMN_TYPE.DATA, 200).alignLeft().editable(false).header('URL').end(),
//             createColumn('createdAt', COLUMN_TYPE.DATA, 100).editable(false).header('생성일').end(),
//             createColumn('updatedAt', COLUMN_TYPE.DATA, 100).editable(false).header('수정일').end(),
//             createColumn('lastUpdatedByUserLoginId', COLUMN_TYPE.DATA, 120).editable(false).header('수정자').end(),
//         ]);
//
//         //컬럼의 사이즈를 그리드 여백에 맞춰 설정해준다.
//         gridView.displayOptions.fitStyle = "evenFill";
//         gridView.displayOptions.emptyMessage = '표시할 데이타가 없습니다.';
//
//         this.gridView = gridView;
//         this.gridProvider = gridProvider;
//         this.gridView.setPaging(true, this.size);
//         this.#setPagination(1, this.size, programCount);
//     }
//
//     #setPagination(page, size, totalCount) {
//         this.pagination.create({page, size, totalCount})
//     }
//
//     #configPagination() {
//         this.pagination.onClickPageButton = async (currentPage) => {
//             const {programList, programCount} = await this.#getPrograms(this.searchParam, currentPage, this.size);
//             this.gridProvider.fillJsonData(programList, {fillMode: "set"});
//             this.gridView.refresh();
//             this.gridView.setPage(currentPage);
//             this.gridView.rowIndicator.indexOffset = ((currentPage - 1) * this.size);
//         };
//     }
//
//     async search() {
//         const searchType = qs(this.searchForm, '#searchType').value;
//         const searchInput = qs(this.searchForm, '#searchInput').value;
//         this.searchParam = {
//             [searchType]: searchInput
//         }
//         const {programList, programCount} = await this.#getPrograms(this.searchParam, 1, this.size);
//         // this.gridProvider.setRows(programList);
//         this.gridProvider.fillJsonData(programList, {fillMode: "set"});
//         this.gridView.refresh();
//         this.#setPagination(1, this.size, programCount);
//     }
//
//
//     /**
//      * 주어진 매개변수를 기반으로 프로그램을 검색합니다.
//      *
//      * @param {Object} 옵션 - 옵션 개체입니다.
//      * @param {string} options.id - 프로그램 ID입니다.
//      * @param {string} options.programName - 프로그램 이름입니다.
//      * @param {string} options.url - 프로그램 URL입니다.
//      * @param {number} page - 검색할 페이지 번호(기본값: 1).
//      * @param {number} size - 페이지당 검색할 프로그램 수(기본값: 10).
//      * @return {Promise<{programList: Program[], programCount: number}>} - 프로그램 목록과 개수를 포함하는 객체로 해석되는 Promise입니다.
//      */
//     async #getPrograms({id, programName, url} = {
//         id: '',
//         programName: '',
//         url: '',
//         page: '',
//         size: ''
//     }, page = 1, size = 10) {
//         try {
//             const programPageResult = await fetchManageProgram({
//                 id,
//                 programName,
//                 url
//             }, page, size, ['updatedAt'], ["DESC"]);
//             const programList = programMapping(programPageResult.getResult());
//             const programCount = programPageResult.getTotalCount();
//
//             return {programList, programCount}
//         } catch (e) {
//             console.error(tag, e);
//             errorAlert("프로그램을 가져오는데 실패하였습니다.");
//         }
//     }
// }

export default class ProgramGrid {
    /**
     * @type {TGrid}
     */
    grid;
    /**
     * @type {HTMLElement}
     */
    container
    /**
     * @type {HTMLElement}
     */
    searchForm;
    /**
     * @type {Pagination}
     */
    pagination;
    /**
     * @type {{}}
     */
    searchParam = {};
    /**
     *
     * @type {number}
     */
    size = 20;

    /**
     * @param {object} paramObj
     * @param {TGrid} paramObj.tGrid
     * @param {HTMLElement} paramObj.container
     * @param {HTMLElement} paramObj.searchForm - 검색 양식 요소입니다.
     * @param {Pagination} paramObj.pagination - Pagination 개체입니다.
     */
    constructor({
                    tGrid,
                    container,
                    searchForm,
                    pagination,
                }) {
        this.grid = tGrid;
        this.container = container;
        this.searchForm = searchForm;
        this.pagination = pagination;
    }

    /**
     * @description 그리드 클릭 이벤트를 설정한다.
     * @param {function} func
     * @param {number} func.param.rowNum - 셀 클릭 이벤트의 파라미터 객체
     * @param {string} func.param.columnName - 클릭된 셀의 열 이름
     */
    setClickEvent(func) {
        this.grid.setCellClick(func);
    }

    /**
     * @description 행 번호에 해당하는 데이터를 Program으로 반환한다.
     * @param rowNum
     * @return {Program|null}
     */
    getProgram(rowNum) {
        const rowData = this.grid.getRowData(rowNum);

        if(isEmptyObject(rowData))
            return null;

        return new Program(rowData)
    }

    /**
     * @description 그리드를 초기화한다.
     * @return {Promise<void>}
     */
    async search() {
        const searchType = qs(this.searchForm, '#searchType').value;
        const searchInput = qs(this.searchForm, '#searchInput').value;
        const searchParam = {
            [searchType]: searchInput,
            page: 1,
            size: 10,
        }

        const programSearchResponseDto = await this.#fetchProgram(searchParam);
        const programResponseDtos = programSearchResponseDto.getResult();

        this.grid.setData(programResponseDtos.map(programResponseDto => programResponseDto.toObject()));
    }

    /**
     *
     * @param {object} paramObj
     * @param {number} paramObj.id
     * @param {string} paramObj.programName
     * @param {string} paramObj.url
     * @return {Promise<ProgramSearchResponseDto>}
     */
    async #fetchProgram({id, programName, url, size, page}) {
        const programSearchRequestDto = new ProgramSearchRequestDto(
        {
            id,
            programName,
            url,
            page: page ? page : 1,
            size: size ? size : 10,
        });
        return await fetchManageProgram(programSearchRequestDto);
    }
}
